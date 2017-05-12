package org.gitclub.ui.module;

import org.gitclub.ApplicationComponent;
import org.gitclub.ui.MainFragment;
import org.gitclub.ui.MainFragmentActivity;
import org.gitclub.ui.MainActivity;

import dagger.Component;

/**
 * Created by le on 5/8/17.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    void inject(MainFragmentActivity activity);

    void inject(MainFragment fragment);
}


