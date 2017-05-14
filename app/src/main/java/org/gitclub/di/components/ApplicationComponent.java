package org.gitclub.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import org.gitclub.GitApplication;
import org.gitclub.data.AccessTokenStore;
import org.gitclub.di.modules.ApplicationModule;
import org.gitclub.net.GithubApi;
import org.gitclub.net.GithubApiV3;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by le on 5/8/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(GitApplication application);

    /**
     * @return GitApplication
     */
    GitApplication application();

    /**
     * @return ApplicationContext
     */
    Context context();

    /**
     * @return SharedPreferences
     */
    SharedPreferences sharedPreferences();

    /**
     * @return
     */
    AccessTokenStore accessTokenStore();

}
