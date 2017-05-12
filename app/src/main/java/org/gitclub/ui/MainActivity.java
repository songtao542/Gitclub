package org.gitclub.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.gitclub.ApplicationComponent;
import org.gitclub.GitApplication;
import org.gitclub.R;
import org.gitclub.model.User;
import org.gitclub.provider.GitclubContent;
import org.gitclub.ui.module.DaggerMainComponent;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<User> {

    @Inject
    SharedPreferences mSharedPreferences;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    //    @BindView(R.id.userhead)
    protected SimpleDraweeView mUserHead;
    //    @BindView(R.id.username)
    protected TextView mUserName;
    //    @BindView(R.id.useremail)
    protected TextView mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent appComponent = ((GitApplication) getApplication()).getApplicationComponent();

        mSharedPreferences = appComponent.sharedPreferences();
        String login = mSharedPreferences.getString("login", null);
        SLog.d("login=" + login);
        if (login == null) {
            intentToLogin();
        }

        setContentView(R.layout.fragment_main);

        DaggerMainComponent.builder().applicationComponent(appComponent).build().inject(this);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //mDrawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
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
    }

    private void intentToLogin() {
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

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
                String emailAddress = getIntent().getStringExtra("EXTRA_EMAIL");
                Cursor cursor = getContext().getContentResolver().query(Uri.withAppendedPath(GitclubContent.User.CONTENT_URI, "email/" + emailAddress),
                        null, null, null, null);
                SLog.d("loadInBackground cursor=" + cursor);
                if (cursor != null) {
                    ArrayList<User> users = User.fromCursor(cursor);
                    if (users != null && users.size() > 0) {
                        return users.get(0);
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
        } else {
            intentToLogin();
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


    public void onFragmentInteraction(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case 1:

                break;
        }
    }
}
