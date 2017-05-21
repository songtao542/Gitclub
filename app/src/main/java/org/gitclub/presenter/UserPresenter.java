package org.gitclub.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import org.gitclub.data.UserAccessor;
import org.gitclub.data.UserTokenStore;
import org.gitclub.model.User;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApiV3;
import org.gitclub.ui.view.UserView;
import org.gitclub.utils.SLog;

import javax.inject.Inject;

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
    private UserTokenStore mUserTokenStore;

    private SharedPreferences mSharedPreferences;

    private String mEmailAddress;
    private User mUser;

    @Inject
    public UserPresenter(Context context, Api api, UserAccessor userAccessor, SharedPreferences sharedPreferences, UserTokenStore userTokenStore) {
        mContext = context;
        mApi = api;
        mUserAccessor = userAccessor;
        mSharedPreferences = sharedPreferences;
        mUserTokenStore = userTokenStore;
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
        mUser = mUserTokenStore.getUser();
        if (mUser == null) {
            mEmailAddress = mSharedPreferences.getString("login", null);
        }
        SLog.d(UserPresenter.this, "hasLoginUser email address=" + mEmailAddress);
        if (mEmailAddress != null) {
            boolean restored = mUserTokenStore.restoreUserToken(mEmailAddress);
            SLog.d(UserPresenter.this, "restore user accessToken " + restored);
            if (restored) {
                return true;
            }
        }
        return false;
    }

    public String getEmailAddress() {
        return mEmailAddress;
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
                        if (mUserView != null) {
                            mUserView.user(user);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(UserPresenter.this, "getUser from github server failure");
                        if (mUserView != null) {
                            mUserView.showError(throwable.getMessage());
                        }
                    }
                });
    }

    private void saveUser(User user) {
        SLog.d(UserPresenter.this, "saveUser to database user=" + user);
        if (user.email == null) {
            user.email = mEmailAddress;
        }
        mUserAccessor.insertOrUpdateByEmail(user);
        long id = mUserAccessor.queryKeyByEmail(user.email);
        mUserTokenStore.updateTokenUserKeyByEmail(user.email, id);
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
