package org.gitclub.ui.module;

import org.gitclub.ApplicationComponent;
import org.gitclub.ui.LoginActivity;
import org.gitclub.ui.LoginFragment;

import dagger.Component;

/**
 * Created by le on 5/8/17.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    void inject(LoginFragment fragment);
}


