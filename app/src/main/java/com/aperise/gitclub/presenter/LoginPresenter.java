package com.aperise.gitclub.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aperise.gitclub.data.UserTokenStore;
import com.aperise.gitclub.model.AccessToken;
import com.aperise.gitclub.model.Scopes;
import com.aperise.gitclub.model.User;
import com.aperise.gitclub.net.Api;
import com.aperise.gitclub.net.GithubApi;
import com.aperise.gitclub.net.GithubApiV3;
import com.aperise.gitclub.ui.view.LoginView;
import com.aperise.gitclub.utils.SLog;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class LoginPresenter implements Presenter {
    static final String LOGIN_MATCH_URL = "https://github.com/login?client_id=" + GithubApi.CLIENT_ID + "&return_to=%2Flogin%2Foauth%2Fauthorize%3Fclient_id%3D8c148bb9efb6382368f4%26";

    static final String AUTHORIZE_URL = GithubApi.AUTHORIZE_URL + Scopes.user + " " + Scopes.repo;

    private WeakReference<LoginView> mLoginView;

    private Context mContext;

    private Api mApi;
    private GithubApi mGithubApi;
    private GithubApiV3 mGithubApiV3;
    private UserTokenStore mUserTokenStore;

    private WebView mWebView;

    private boolean mIsReady;
    private boolean mWaitLogin;
    private int mLoadCounter = 0;

    private String mCode;

    private SharedPreferences mSharedPreferences;

    private String mEmailAddress;
    private String mPassword;

    /**
     * @param context
     * @param api
     * @param sharedPreferences
     * @param userTokenStore
     */
    @Inject
    public LoginPresenter(Context context, Api api, SharedPreferences sharedPreferences, UserTokenStore userTokenStore) {
        mContext = context;
        mApi = api;
        mUserTokenStore = userTokenStore;
        mSharedPreferences = sharedPreferences;
        mWebView = new WebView(context);
        configWebView();
    }

    public void setLoginView(LoginView loginView) {
        mLoginView = new WeakReference<>(loginView);
    }


    private void configWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setDomStorageEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        mWebView.addJavascriptInterface(new JavaScriptInterface(), "local");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoadCounter++;
                SLog.d(LoginPresenter.this, "onPageStarted url:" + url);
                String prefix = "http://localhost:4567/callback?code=";
                if (url.startsWith(prefix)) {
                    mCode = url.substring(prefix.length());
                    SLog.d(LoginPresenter.this, "onPageStarted mCode:" + mCode);
                    getAccessToken(mCode);
                } else if (view.getTitle().contains("Authorize GitClub")) {
                    needAuthorize();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                SLog.d("onPageFinished url:" + url);
                //view.loadUrl("javascript:window.local.showSource('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                if (url.startsWith(LOGIN_MATCH_URL)) {
                    SLog.d(LoginPresenter.this, "login page is ready");
                    mIsReady = true;
                    hiddenHeader();

                    if (mWaitLogin) {
                        mWaitLogin = false;
                        login(mEmailAddress, mPassword);
                    }
                } else {
                    mIsReady = false;
                    if (view.getTitle().contains("Authorize GitClub")) {
                        needAuthorize();
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (mLoadCounter == 1) {
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                SLog.d(LoginPresenter.this, "onReceivedError error:" + error);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (mLoadCounter > 1 && !url.startsWith(prefix)) {
                    if (mLoginView != null && mLoginView.get() != null) {
                        mLoginView.get().showError("error");
                    }
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                SLog.d(LoginPresenter.this, "onReceivedHttpError errorResponse:" + errorResponse);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (mLoadCounter > 1 && !url.startsWith(prefix)) {
                    if (mLoginView != null && mLoginView.get() != null) {
                        mLoginView.get().showError("http error");
                    }
                }
            }
        });

        mWebView.loadUrl(AUTHORIZE_URL);
    }

    final static class JavaScriptInterface {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println(html);
        }
    }

    private void hiddenHeader() {
        String js = "javascript:(function(){" +
                "document.getElementsByClassName(\"nav-bar\")[0].style.visibility=\"hidden\";" +
                "console.log(\"隐藏标题栏\");" +
                "})()";
        mWebView.evaluateJavascript(js, null);
    }

    private void needAuthorize() {
        String js = "javascript:(function(){" +
                "var authorize = document.getElementsByName(\"authorize\"); " +
                "authorize[0].click();" +
                "console.log(\"点击授权\");" +
                "})()";
        mWebView.evaluateJavascript(js, null);
    }

    public void login(String email, String password) {
        mEmailAddress = email;
        mPassword = password;
        if (mIsReady) {
            String js = "javascript:(function(){" +
                    "var username = document.getElementsByName(\"login\");" +
                    "var password = document.getElementsByName(\"password\"); " +
                    "var commit = document.getElementsByClassName(\"btn btn-block\"); " +
                    "username[0].value = \"" + email + "\";" +
                    "password[0].value= \"" + password + "\";" +
                    "commit[0].click();" +
                    "console.log(\"点击登录\");" +
                    "})()";
            mWebView.evaluateJavascript(js, null);
        } else {
            mWaitLogin = true;
            mWebView.loadUrl(AUTHORIZE_URL);
        }
    }

    private GithubApi ensureGithubApi() {
        if (mGithubApi == null) {
            mGithubApi = mApi.getGithubApi();
        }
        return mGithubApi;
    }

    private void getAccessToken(String code) {
        ensureGithubApi();
        mGithubApi.rxAccessToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccessToken>() {
                    @Override
                    public void accept(@NonNull final AccessToken accessToken) throws Exception {
                        if (accessToken != null) {
                            SLog.d(LoginPresenter.this, "response getAccessToken:" + accessToken.accessToken);
                            SLog.d(LoginPresenter.this, "response tokenType:" + accessToken.tokenType);
                            SLog.d(LoginPresenter.this, "response scope:" + accessToken.scope);
                            saveAccessToken(accessToken);
                            getUser();
                        } else {
                            SLog.d(LoginPresenter.this, "getAccessToken from github server failure");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (mLoginView != null) {
                            LoginView loginView = mLoginView.get();
                            if (loginView != null) {
                                mLoginView.get().showError(throwable.getMessage());
                            }
                        }
                    }
                });
    }

    private GithubApiV3 ensureGithubApiV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
        return mGithubApiV3;
    }

    public void getUser() {
        ensureGithubApiV3();
        mGithubApiV3.rxuser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        saveUser(user);
                        if (mLoginView != null) {
                            LoginView loginView = mLoginView.get();
                            if (loginView != null) {
                                loginView.hideLoading();
                                loginView.success(user);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(LoginPresenter.this, "getUser from github server failure");
                        if (mLoginView != null) {
                            LoginView loginView = mLoginView.get();
                            if (loginView != null) {
                                loginView.showError(throwable.getMessage());
                            }
                        }
                    }
                });
    }

    private void saveUser(User user) {
        SLog.d(LoginPresenter.this, "saveUser to database user=" + user);
        if (user.email == null) {
            user.email = mEmailAddress;
        }
        mUserTokenStore.insertOrUpdateUserByEmail(user);
        long id = mUserTokenStore.queryUserKeyByEmail(user.email);
        mUserTokenStore.updateTokenUserKeyByEmail(user.email, id);
        mUserTokenStore.storeUser(user);
    }

    private void saveAccessToken(AccessToken accessToken) {
        accessToken.email = mEmailAddress;

        SLog.d(this, "saveUserAccessToken getAccessToken:" + accessToken);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("login", mEmailAddress);
        editor.commit();

        mUserTokenStore.insertOrUpdateTokenByEmail(accessToken);
        mUserTokenStore.storeAccessToken(accessToken);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
    }
}
