package com.aperise.gitclub.presenter;

import android.content.Context;

import com.aperise.gitclub.model.Star;
import com.aperise.gitclub.net.Api;
import com.aperise.gitclub.net.GithubApiV3;
import com.aperise.gitclub.ui.view.StarsView;
import com.aperise.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by le on 5/27/17.
 */

public class StarsPresenter implements Presenter {
    private Api mApi;
    private GithubApiV3 mGithubApiV3;

    private Context mContext;
    private static final int PAGE_SIZE = 30;
    private int mPageIndex = 1;

    private StarsView mStarsView;

    @Inject
    public StarsPresenter(Context context, Api api) {
        this.mContext = context;
        this.mApi = api;
    }

    public void setStarsView(StarsView starsView) {
        this.mStarsView = starsView;
    }

    private void ensureGithubV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
    }

    public void getStars() {
        ensureGithubV3();
        mGithubApiV3.rxstars(mPageIndex, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Star>>() {
                    @Override
                    public void accept(@NonNull ArrayList<Star> stars) throws Exception {
                        SLog.d(StarsPresenter.this, "stars size:" + stars.size() + "\n" + stars);
                        if (mStarsView != null) {
                            mStarsView.stars(stars);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(StarsPresenter.this, "getRepos from github server failure");
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
