package org.gitclub.di.modules;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wangsongtao on 2017/5/14.
 */
@Module
public class ActivityModule {
    Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Context context() {
        return mActivity;
    }
}
