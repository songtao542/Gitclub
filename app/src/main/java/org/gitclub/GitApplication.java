package org.gitclub;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by le on 5/8/17.
 */

public class GitApplication extends Application {

    ApplicationComponent mComponent;

    String mApiToken;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

    public ApplicationComponent getApplicationComponent() {
        ensureAppComponent();
        return mComponent;
    }

    private void ensureAppComponent() {
        if (mComponent == null) {
            mComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this, mApiToken)).build();
        }
    }

    public ApplicationComponent initApplicationComponentWithApiToken(String apiToken) {
        mApiToken = apiToken;
        mComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this, mApiToken)).build();
        return mComponent;
    }
}
