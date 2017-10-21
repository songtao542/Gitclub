package com.aperise.gitclub;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import com.aperise.gitclub.data.UserTokenStore;
import com.aperise.gitclub.di.components.ApplicationComponent;
import com.aperise.gitclub.di.components.DaggerApplicationComponent;
import com.aperise.gitclub.di.modules.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by le on 5/8/17.
 */

public class GitApplication extends Application {

    ApplicationComponent mAppComponent;
    @Inject
    UserTokenStore mUserTokenStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ensureApplicationComponent();
    }

    private void ensureApplicationComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
            mAppComponent.inject(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

}
