package org.gitclub;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.gitclub.data.AccessTokenStore;
import org.gitclub.di.components.ApplicationComponent;
import org.gitclub.di.components.DaggerApplicationComponent;
import org.gitclub.di.modules.ApplicationModule;
import org.gitclub.model.AccessToken;

import javax.inject.Inject;

/**
 * Created by le on 5/8/17.
 */

public class GitApplication extends Application {

    ApplicationComponent mAppComponent;
    @Inject
    AccessTokenStore mAccessTokenStore;

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
