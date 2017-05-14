package org.gitclub.di.components;

import org.gitclub.di.ActivityScope;
import org.gitclub.ui.activities.BaseActivity;

import dagger.Component;

/**
 * Created by wangsongtao on 2017/5/14.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(BaseActivity activity);
}
