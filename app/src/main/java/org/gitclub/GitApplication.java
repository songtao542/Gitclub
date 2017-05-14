package org.gitclub;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.gitclub.di.components.ApplicationComponent;
import org.gitclub.di.components.DaggerApplicationComponent;
import org.gitclub.di.modules.ApiModule;
import org.gitclub.di.modules.ApplicationModule;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by le on 5/8/17.
 */

public class GitApplication extends Application {

    ApplicationComponent mAppComponent;
    ApiModule mApiModule;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ensureApplicationComponent();
    }

    private void ensureApplicationComponent() {
        if (mAppComponent == null) {
            mApiModule = new ApiModule();
            mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).apiModule(mApiModule).build();
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    public ApplicationComponent initApiAccessToken(String apiToken) {
        mApiModule.initApiAccessToken(apiToken);
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).apiModule(mApiModule).build();
        return mAppComponent;
    }


}
