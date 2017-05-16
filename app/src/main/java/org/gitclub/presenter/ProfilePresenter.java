package org.gitclub.presenter;

import android.content.Context;

import com.google.gson.JsonArray;

import org.gitclub.model.Repository;
import org.gitclub.net.GithubApiV3;
import org.gitclub.net.Api;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by le on 5/16/17.
 */

public class ProfilePresenter implements Presenter {
    private Api mApi;
    private GithubApiV3 mGithubApiV3;

    private Context mContext;

    @Inject
    public ProfilePresenter(Context context, Api api) {
        this.mContext = context;
        this.mApi = api;
    }

    private void ensureGithubV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
    }

    public void getRepos() {
        ensureGithubV3();
        mGithubApiV3.rxrepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Repository>>() {
                    @Override
                    public void accept(@NonNull ArrayList<Repository> repos) throws Exception {
                        SLog.d(ProfilePresenter.this, "repos size:" + repos.size() + "\n" + repos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(ProfilePresenter.this, "getRepos error");
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
