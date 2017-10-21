package com.aperise.gitclub.presenter;

import android.content.Context;

import com.aperise.gitclub.data.UserTokenStore;
import com.aperise.gitclub.model.Gist;
import com.aperise.gitclub.net.Api;
import com.aperise.gitclub.net.GistApi;
import com.aperise.gitclub.net.GithubApiV3;
import com.aperise.gitclub.ui.view.GistsView;
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

public class GistsPresenter implements Presenter {
    private Api mApi;
    private GithubApiV3 mGithubApiV3;
    private GistApi mGistApi;


    private Context mContext;
    private static final int PAGE_SIZE = 30;
    private int mPageIndex = 1;

    private GistsView mGistsView;

    private UserTokenStore mUserTokenStore;

    @Inject
    public GistsPresenter(Context context, Api api, UserTokenStore userTokenStore) {
        this.mContext = context;
        this.mApi = api;
        this.mUserTokenStore = userTokenStore;
    }

    public void setGistsView(GistsView gistsView) {
        this.mGistsView = gistsView;
    }

    private void ensureGithubV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
    }

    private void ensureGistApi() {
        if (mGistApi == null) {
            mGistApi = mApi.getGistApi();
        }
    }

    public void getGists() {
        ensureGithubV3();
        mGithubApiV3.rxgists(mPageIndex, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Gist>>() {
                    @Override
                    public void accept(@NonNull ArrayList<Gist> gists) throws Exception {
                        SLog.d(GistsPresenter.this, "gists size:" + gists.size() + "\n" + gists);
                        if (mGistsView != null) {
                            mGistsView.gists(gists);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(GistsPresenter.this, "getRepos from github server failure");
                        if (mGistsView != null) {
                            mGistsView.showError("getRepos from github server failure");
                        }
                    }
                });

//        ensureGistApi();
//        mGistApi.rxgists(mUserTokenStore.getUser().login  )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String gists) throws Exception {
//                        SLog.d(GistsPresenter.this, "gists" +  gists);
//                        if (mGistsView != null) {
//                            //mGistsView.gists(gists);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        SLog.d(GistsPresenter.this, "getRepos from github server failure");
//                        if (mGistsView != null) {
//                            mGistsView.showError("getRepos from github server failure");
//                        }
//                    }
//                });
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
