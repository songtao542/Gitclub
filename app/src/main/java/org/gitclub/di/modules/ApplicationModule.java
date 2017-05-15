package org.gitclub.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.gitclub.GitApplication;
import org.gitclub.data.AccessTokenAccessor;
import org.gitclub.data.AccessTokenStore;
import org.gitclub.net.Api;
import org.gitclub.net.GithubApi;
import org.gitclub.net.GithubApiV3;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by le on 3/31/17.
 */
@Module
public class ApplicationModule {

    private GitApplication mApplication;

    private SharedPreferences mSharedPreferences;

    private AccessTokenStore mAccessTokenStore;
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
    public AccessTokenStore accessTokenStore(Context context) {
        if (mAccessTokenStore == null) {
            mAccessTokenStore = new AccessTokenStore(context);
        }
        return mAccessTokenStore;
    }


    @Singleton
    @Provides
    public Api api(AccessTokenStore accessTokenStore) {
        if (mApi == null) {
            mApi = new Api(accessTokenStore);
        }
        return mApi;
    }
}
