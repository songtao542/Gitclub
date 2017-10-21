package com.aperise.gitclub.di.modules;

import com.aperise.gitclub.ui.activities.LoginActivity;
import com.aperise.gitclub.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();
}
