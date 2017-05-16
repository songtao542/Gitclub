package org.gitclub.presenter;

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

import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.data.AccessTokenStore;
import org.gitclub.model.AccessToken;
import org.gitclub.model.Scopes;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApi;
import org.gitclub.provider.GitclubContent;
import org.gitclub.ui.view.LoginView;
import org.gitclub.utils.SLog;

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

    private LoginView mLoginView;

    private Context mContext;

    private GithubApi mGithubApi;
    private Api mApi;
    private AccessTokenStore mAccessTokenStore;

    private WebView mWebView;

    private boolean mIsReady;
    private boolean mWaitLogin;
    private int mLoadCounter = 0;

    private String mCode;

    private SharedPreferences mSharedPreferences;

    private String mUsername;
    private String mPassword;

    @Inject
    public LoginPresenter(Context context, Api api, SharedPreferences sharedPreferences, AccessTokenStore accessTokenStore) {
        mContext = context;
        mApi = api;
        mAccessTokenStore = accessTokenStore;
        mSharedPreferences = sharedPreferences;
        mWebView = new WebView(context);
        configWebView();
    }

    public void setLoginView(LoginView loginView) {
        mLoginView = loginView;
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
                SLog.d("onPageStarted url:" + url);
                String prefix = "http://localhost:4567/callback?code=";
                if (url.startsWith(prefix)) {
                    mCode = url.substring(prefix.length());
                    SLog.d("onPageStarted mCode:" + mCode);
                    accessToken(mCode);
                } else if (view.getTitle().contains("Authorize GitClub")) {
                    needAuthorize();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                SLog.d("onPageFinished url:" + url);
                //view.loadUrl("javascript:window.local.showSource('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

                if (url.startsWith(LOGIN_MATCH_URL)) {
                    SLog.d("login page is ready");
                    mIsReady = true;
                    hiddenHeader();

                    if (mWaitLogin) {
                        mWaitLogin = false;
                        login(mUsername, mPassword);
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
                SLog.d("onReceivedError error:" + error);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (mLoadCounter > 1 && !url.startsWith(prefix)) {
                    if (mLoginView != null) {
                        mLoginView.showError("error");
                    }
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                SLog.d("onReceivedHttpError errorResponse:" + errorResponse);
                Uri uri = request != null ? request.getUrl() : null;
                String url = uri != null ? uri.toString() : "";
                String prefix = "http://localhost:4567/callback?code=";
                if (mLoadCounter > 1 && !url.startsWith(prefix)) {
                    if (mLoginView != null) {
                        mLoginView.showError("http error");
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

    public void login(String username, String password) {
        mUsername = username;
        mPassword = password;
        if (mIsReady) {
            String js = "javascript:(function(){" +
                    "var username = document.getElementsByName(\"login\");" +
                    "var password = document.getElementsByName(\"password\"); " +
                    "var commit = document.getElementsByClassName(\"btn btn-block\"); " +
                    "username[0].value = \"" + username + "\";" +
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

    private void accessToken(String code) {
        ensureGithubApi();
        mGithubApi.rxAccessToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccessToken>() {
                    @Override
                    public void accept(@NonNull final AccessToken accessToken) throws Exception {
                        if (accessToken != null) {
                            SLog.d("response accessToken:" + accessToken.accessToken);
                            SLog.d("response tokenType:" + accessToken.tokenType);
                            SLog.d("response scope:" + accessToken.scope);
                            saveAccessToken(mUsername, accessToken);
                            if (mLoginView != null) {
                                mLoginView.hideLoading();
                            }
                        } else {
                            SLog.d("response accessToken is null");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (mLoginView != null) {
                            mLoginView.showError(throwable.getMessage());
                        }
                    }
                });
    }

    private void saveAccessToken(String username, AccessToken accessToken) {
        accessToken.email = username;

        SLog.d("saveUserAccessToken accessToken:" + accessToken + " username:" + username);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("login", username);
        editor.commit();

        //mContext.getContentResolver().insert(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues());
        mAccessTokenStore.insertOrUpdateByEmail(accessToken);
        mAccessTokenStore.storeAccessToken(accessToken.email, accessToken);
        if (mLoginView != null) {
            mLoginView.accessToken(accessToken);
        }
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
