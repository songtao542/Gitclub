package org.gitclub.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import org.gitclub.GitApplication;
import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.data.AccessTokenStore;
import org.gitclub.data.UserAccessor;
import org.gitclub.model.AccessToken;
import org.gitclub.model.User;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApi;
import org.gitclub.net.GithubApiV3;
import org.gitclub.ui.view.UserView;
import org.gitclub.utils.SLog;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class UserPresenter implements Presenter {

    private Context mContext;
    public GithubApiV3 mGithubApiV3;
    public Api mApi;
    private UserView mUserView;

    private UserAccessor mUserAccessor;
    private AccessTokenStore mAccessTokenStore;

    private SharedPreferences mSharedPreferences;

    private String mEmailAddress;

    @Inject
    public UserPresenter(Context context, Api api, UserAccessor userAccessor, SharedPreferences sharedPreferences, AccessTokenStore accessTokenStore) {
        mContext = context;
        mApi = api;
        mUserAccessor = userAccessor;
        mSharedPreferences = sharedPreferences;
        mAccessTokenStore = accessTokenStore;
    }

    public void setUserView(UserView userView) {
        mUserView = userView;
    }

    private GithubApiV3 ensureGithubApiV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
        return mGithubApiV3;
    }

    public boolean hasLoginUser() {
        mEmailAddress = mAccessTokenStore.getEmailAddress();
        if (mEmailAddress == null) {
            mEmailAddress = mSharedPreferences.getString("login", null);
        }
        SLog.d("UserPresenter mEmailAddress=" + mEmailAddress);
        if (mEmailAddress != null) {
            AccessToken accessToken = mAccessTokenStore.getAccessToken(mEmailAddress);
            SLog.d("UserPresenter accessToken=" + accessToken);
            if (accessToken != null && accessToken.accessToken != null) {
                mAccessTokenStore.storeAccessToken(mEmailAddress, accessToken);
                return true;
            }
        }
        return false;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String email) {
        mEmailAddress = email;
    }

    public void getUser() {
        ensureGithubApiV3();
        mGithubApiV3.rxGetUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        saveUser(user);
                        if (mUserView != null) {
                            mUserView.user(user);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (mUserView != null) {
                            mUserView.showError(throwable.getMessage());
                        }
                    }
                });
    }

    private void saveUser(User user) {
        if (user.email == null) {
            user.email = mEmailAddress;
        }
        mUserAccessor.insertOrUpdateByEmail(user);
        long id = mUserAccessor.queryIdByEmail(user.email);
        mAccessTokenStore.updateUserKeyByEmail(user.email, id);
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
