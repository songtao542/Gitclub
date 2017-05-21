package org.gitclub.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import org.gitclub.GitApplication;
import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.data.UserAccessor;
import org.gitclub.data.UserTokenStore;
import org.gitclub.net.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by le on 3/31/17.
 */
@Module
public class ApplicationModule {

    private GitApplication mApplication;

    private SharedPreferences mSharedPreferences;

    private UserTokenStore mUserTokenStore;
    private Api mApi;

    public ApplicationModule(GitApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    public GitApplication application() {
        return mApplication;
    }

    @Singleton
    @Provides
    public Context context() {
        return mApplication;
    }

    @Singleton
    @Provides
    public SharedPreferences sharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mApplication.getSharedPreferences("gitclub", Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    @Singleton
    @Provides
    public UserTokenStore accessTokenStore(AccessTokenAccessor accessTokenAccessor, UserAccessor userAccessor) {
        if (mUserTokenStore == null) {
            mUserTokenStore = new UserTokenStore(accessTokenAccessor, userAccessor);
        }
        return mUserTokenStore;
    }


    @Singleton
    @Provides
    public Api api(UserTokenStore userTokenStore) {
        if (mApi == null) {
            mApi = new Api(userTokenStore);
        }
        return mApi;
    }
}
