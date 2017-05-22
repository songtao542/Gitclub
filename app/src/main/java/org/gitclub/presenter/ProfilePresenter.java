package org.gitclub.presenter;

import android.content.Context;
import android.util.Pair;

import org.gitclub.data.UserTokenStore;
import org.gitclub.model.Event;
import org.gitclub.model.Repository;
import org.gitclub.model.User;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApiV3;
import org.gitclub.ui.view.ProfileView;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by le on 5/16/17.
 */

public class ProfilePresenter implements Presenter {
    private Api mApi;
    private GithubApiV3 mGithubApiV3;

    private Context mContext;
    private ProfileView mProfileView;

    private UserTokenStore mUserTokenStore;

    private User mUser;

    private static final int PAGE_SIZE = 30;
    private int mPageIndex = 1;

    @Inject
    public ProfilePresenter(Context context, Api api, UserTokenStore userTokenStore) {
        this.mContext = context;
        this.mApi = api;
        this.mUserTokenStore = userTokenStore;
    }

    private void ensureGithubV3() {
        if (mGithubApiV3 == null) {
            mGithubApiV3 = mApi.getGithubApiV3();
        }
    }

    public void setView(ProfileView view) {
        mProfileView = view;
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
                        if (mProfileView != null) {
                            mProfileView.profile(repos);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(ProfilePresenter.this, "getRepos from github server failure");
                        if (mProfileView != null) {
                            mProfileView.showError("getRepos from github server failure");
                        }
                    }
                });
    }

    private void getUser() {

    }


    public void getReposWithEvents() {
        ensureGithubV3();
        User user = mUserTokenStore.getUser();
        if (user == null) {
            return;
        }
        Observable<ArrayList<Repository>> repos = mGithubApiV3.rxrepos();
        Observable<ArrayList<Event>> events = mGithubApiV3.rxevents(user.login, mPageIndex, PAGE_SIZE);
        Observable.zip(repos, events, new BiFunction<ArrayList<Repository>, ArrayList<Event>, Pair<ArrayList<Repository>, ArrayList<Event>>>() {
            @Override
            public Pair<ArrayList<Repository>, ArrayList<Event>> apply(@NonNull ArrayList<Repository> repositories, @NonNull ArrayList<Event> events) throws Exception {
                return new Pair(repositories, events);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pair<ArrayList<Repository>, ArrayList<Event>>>() {
                               @Override
                               public void accept(@NonNull Pair<ArrayList<Repository>, ArrayList<Event>> pair) throws Exception {
                                   SLog.d(ProfilePresenter.this, "repos size:" + pair.first.size() + "\n" + pair.first);
                                   SLog.d(ProfilePresenter.this, "events size:" + pair.second.size() + "\n" + pair.second);
                                   if (mProfileView != null) {
                                       mProfileView.overview(pair.first, pair.second);
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                SLog.d(ProfilePresenter.this, "getRepos from github server failure");
                                if (mProfileView != null) {
                                    mProfileView.showError("getRepos from github server failure");
                                }
                            }
                        });
    }

    public void getEvents() {
        ensureGithubV3();
        User user = mUserTokenStore.getUser();
        if (user == null) {
            return;
        }
        mGithubApiV3.rxevents(user.login, mPageIndex, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Event>>() {
                    @Override
                    public void accept(@NonNull ArrayList<Event> events) throws Exception {
                        SLog.d(ProfilePresenter.this, "events size:" + events.size() + "\n" + events);
                        if (mProfileView != null) {
                            mProfileView.events(events);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        SLog.d(ProfilePresenter.this, "getRepos from github server failure");
                        if (mProfileView != null) {
                            mProfileView.showError("getRepos from github server failure");
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
