package org.gitclub.presenter;

import android.content.Context;

import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.data.UserAccessor;
import org.gitclub.model.User;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApi;
import org.gitclub.net.GithubApiV3;
import org.gitclub.ui.view.UserView;

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
    private AccessTokenAccessor mAccessTokenAccessor;

    private String mEmailAddress;

    @Inject
    public UserPresenter(Context context, Api api, UserAccessor userAccessor, AccessTokenAccessor accessTokenAccessor) {
        mContext = context;
        mApi = api;
        mUserAccessor = userAccessor;
        mAccessTokenAccessor = accessTokenAccessor;
    }

    public void setUserView(UserView userView) {
        mUserView = userView;
    }

    private GithubApiV3 ensureGithubApiV3(String email) {
        if (mGithubApiV3 == null) {
            mApi.setUserEmail(email);
            mGithubApiV3 = mApi.getGithubApiV3();
        }
        return mGithubApiV3;
    }

    public void getUser(String email) {
        mEmailAddress = email;
        ensureGithubApiV3(mEmailAddress);
        mGithubApiV3.rxGetUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (mUserView != null) {
                            mUserView.showError(throwable.getMessage());
                        }
                    }
                })
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        saveUser(user);
                        if (mUserView != null) {
                            mUserView.user(user);
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
        mAccessTokenAccessor.updateUserKeyByEmail(user.email, id);
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
