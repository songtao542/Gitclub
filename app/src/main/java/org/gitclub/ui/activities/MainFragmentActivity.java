package org.gitclub.ui.activities;

import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.gitclub.R;
import org.gitclub.ui.fragments.LoginFragment;
import org.gitclub.ui.fragments.MainFragment;

import javax.inject.Inject;


public class MainFragmentActivity extends BaseActivity
        implements LoginFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener {

    LoginFragment mLoginFragment;
    MainFragment mMainFragment;

    @Inject
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String login = mSharedPreferences.getString("login", null);
        if (login == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mLoginFragment = LoginFragment.newInstance();
            transaction.add(android.R.id.content, mLoginFragment);
            transaction.commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mMainFragment = MainFragment.newInstance(login);
            transaction.add(android.R.id.content, mMainFragment);
            transaction.commit();
        }

    }

    private void decideToLogin() {

//        getContentResolver().query();


    }

    @Override
    public void onBackPressed() {
        if (mMainFragment == null || !mMainFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        // All access token
        sURIMatcher.addURI("org.gitclub.fragment", "login/*", 1);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case 1:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
//                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.remove(mLoginFragment);
                mMainFragment = MainFragment.newInstance(uri.getLastPathSegment());
                transaction.add(android.R.id.content, mMainFragment);
                transaction.commit();
                break;
        }
    }
}