package org.gitclub.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import org.gitclub.GitApplication;
import org.gitclub.di.ApplicationScope;
import org.gitclub.di.modules.AndroidBindingModule;
import org.gitclub.di.modules.ApiModule;
import org.gitclub.di.modules.ApplicationModule;
import org.gitclub.net.GithubApi;
import org.gitclub.net.GithubApiV3;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by le on 5/8/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent extends AndroidInjector<GitApplication> {
    /**
     * @return GihubApiV3
     */
    GithubApiV3 githubApiV3();

    /**
     * @return GihubApi
     */
    GithubApi githubApi();

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


}
