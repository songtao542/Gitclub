package org.gitclub.presenter;

import android.content.Context;

import org.gitclub.data.UserTokenStore;
import org.gitclub.model.PullRequest;
import org.gitclub.model.Star;
import org.gitclub.model.User;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApiV3;
import org.gitclub.ui.view.StarsView;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by le on 5/27/17.
 */

public class PullsPresenter implements Presenter {
    private Api mApi;
    private GithubApiV3 mGithubApiV3;

    private Context mContext;
    private static final int PAGE_SIZE = 30;
    private int mPageIndex = 1;

    private StarsView mStarsView;
    private UserTokenStore mUserTokenStore;

    @Inject
    public PullsPresenter(Context context, Api api, UserTokenStore userTokenStore) {
        this.mContext = context;
        this.mApi = api;
        this.mUserTokenStore = userTokenStore;
    }

    public void setStarsView(StarsView starsView) {
        this.mStarsView = starsView;
    }

    private void ensureGithubV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
    }

    public void getPulls() {
        ensureGithubV3();
        User user = mUserTokenStore.getUser();
        mGithubApiV3.rxpulls(user.login,"",mPageIndex, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<PullRequest>>() {
                    @Override
                    public void accept(@NonNull ArrayList<PullRequest> pulls) throws Exception {
                        SLog.d(PullsPresenter.this, "pulls size:" + pulls.size() + "\n" + pulls);
                        if (mStarsView != null) {
                            //mStarsView.stars(stars);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(PullsPresenter.this, "getRepos from github server failure");
                        if (mStarsView != null) {
                            mStarsView.showError("getRepos from github server failure");
                        }
                    }
                });
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
