package com.aperise.gitclub.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aperise.gitclub.GitApplication;
import com.aperise.gitclub.di.components.ApplicationComponent;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((GitApplication) getApplication()).getApplicationComponent();
    }


}
