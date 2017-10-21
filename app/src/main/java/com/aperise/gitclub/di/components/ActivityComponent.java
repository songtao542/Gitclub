package com.aperise.gitclub.di.components;

import com.aperise.gitclub.di.ActivityScope;
import com.aperise.gitclub.ui.activities.BaseActivity;

import dagger.Component;

/**
 * Created by wangsongtao on 2017/5/14.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(BaseActivity activity);
}
