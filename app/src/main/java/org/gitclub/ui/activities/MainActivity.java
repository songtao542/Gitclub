package org.gitclub.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.gitclub.GitApplication;
import org.gitclub.R;
import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.di.ActivityScope;
import org.gitclub.di.components.ApplicationComponent;
import org.gitclub.model.AccessToken;
import org.gitclub.model.User;
import org.gitclub.presenter.UserPresenter;
import org.gitclub.provider.GitclubContent;
import org.gitclub.ui.fragments.ProfileFragment;
import org.gitclub.ui.view.UserView;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<User>, ProfileFragment.OnFragmentInteractionListener, UserView {

    @ActivityScope
    @dagger.Component(dependencies = {ApplicationComponent.class})
    public interface Component {
        void inject(MainActivity activity);
    }

    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    AccessTokenAccessor mAccessTokenAccessor;

    @Inject
    UserPresenter mUserPresenter;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    //    @BindView(R.id.userhead)
    protected SimpleDraweeView mUserHead;
    //    @BindView(R.id.username)
    protected TextView mUserName;
    //    @BindView(R.id.useremail)
    protected TextView mUserEmail;

    private String mEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerMainActivity_Component.builder().applicationComponent(getApplicationComponent()).build().inject(this);

        mEmailAddress = getIntent().getStringExtra("EXTRA_EMAIL");
        SLog.d("mEmailAddress extra=" + mEmailAddress);
        if (TextUtils.isEmpty(mEmailAddress)) {
            mEmailAddress = mSharedPreferences.getString("login", null);
            SLog.d("mEmailAddress=" + mEmailAddress);
            if (mEmailAddress == null) {
                intentToLogin();
                return;
            } else {
                AccessToken accessToken = mAccessTokenAccessor.queryByEmail(mEmailAddress);
                SLog.d("MainActivity accessToken=" + accessToken);
                if (accessToken != null && accessToken.accessToken != null) {
                    ((GitApplication) getApplication()).initApiAccessToken(accessToken.accessToken);
                    DaggerMainActivity_Component.builder().applicationComponent(getApplicationComponent()).build().inject(this);
                } else {
                    intentToLogin();
                    return;
                }
            }
        }

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mUserHead = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.userhead);
        mUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        mUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.useremail);
        SLog.d("initLoader");
        getSupportLoaderManager().initLoader(0, null, this);

        mUserPresenter.setUserView(this);
    }


    private void intentToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.add(R.id.contentRoot, ProfileFragment.newInstance());
            ft.commit();
        } else if (id == R.id.nav_stars) {

        } else if (id == R.id.nav_gist) {

        } else if (id == R.id.nav_pull_requests) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<User>(this) {

            @Override
            public User loadInBackground() {
                String emailAddress = mEmailAddress;
                if (emailAddress == null) {
                    emailAddress = getIntent().getStringExtra("EXTRA_EMAIL");
                }
                Cursor cursor = getContext().getContentResolver().query(Uri.withAppendedPath(GitclubContent.User.CONTENT_URI, "email/" + emailAddress),
                        null, null, null, null);
                SLog.d("loadInBackground cursor=" + cursor);
                if (cursor != null) {
                    ArrayList<User> users = User.fromCursor(cursor);
                    if (users != null && users.size() > 0) {
                        return users.get(0);
                    } else {
                        mUserPresenter.getUser(mEmailAddress);
                    }
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                SLog.d("onStartLoading");
                forceLoad();
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        SLog.d("onLoadFinished user=" + data);
        if (data != null) {
            mUserHead.setImageURI(data.avatarUrl);
            mUserName.setText(data.login);
            mUserEmail.setText(data.email);
        }
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {

    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
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

                break;
        }
    }


    @Override
    public void user(User user) {
        mUserHead.setImageURI(user.avatarUrl);
        mUserName.setText(user.login);
        mUserEmail.setText(user.email);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
