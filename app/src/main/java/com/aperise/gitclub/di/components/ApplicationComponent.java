package com.aperise.gitclub.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.aperise.gitclub.GitApplication;
import com.aperise.gitclub.data.UserTokenStore;
import com.aperise.gitclub.di.modules.ApplicationModule;
import com.aperise.gitclub.net.Api;

import javax.inject.Singleton;

import dagger.Component;

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
     * @return AccessTokenStore
     */
    UserTokenStore userTokenStore();

    /**
     * @return Api
     */
    Api api();

}
