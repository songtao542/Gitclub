package org.gitclub;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.gitclub.utils.SLog;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by le on 3/31/17.
 */
@Module
public class ApplicationModule {

    Application mApplication;
    GithubApi mApi;
    String mApiToken;
    SharedPreferences mSharedPreferences;

    public ApplicationModule(Context context, String apiToken) {
        mApplication = (Application) context.getApplicationContext();
        mApiToken = apiToken;
    }

    @Singleton
    @Provides
    public GithubApi provideApi() {
        if (mApi == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
            if (mApiToken != null) {
                builder.addInterceptor(new HeaderInterceptor(mApiToken));
            }
            GsonBuilder gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .client(builder.build())
                    .build();
            mApi = retrofit.create(GithubApi.class);
        }
        return mApi;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mApplication.getSharedPreferences("gitclub", Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static class HeaderInterceptor implements Interceptor {

        String mToken;

        public HeaderInterceptor(String token) {
            mToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            //final String basic = "Basic " + Base64.encodeToString(("732fb2fac16dca0b2f1ff53077502f4d2d02c1a8" + ":x-oauth-basic").getBytes(), Base64.NO_WRAP);
            //String basic = ByteString.of(("7d8d34a1314c519241278fa2441b0b230ab78f7a" + ":x-oauth-basic").getBytes(Charset.forName("ISO-8859-1"))).base64();
            //String basic = Credentials.basic("songtao542", "wst472036045");
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3.full+json")
                    .addHeader("User-Agent", "Gitclub-App")
                    .addHeader("Authorization", "token " + mToken)
//                  .method(chain.request().method(), chain.request().body())
//                  .addHeader("Content-Type", "text/html; charset=UTF-8")
//                  .addHeader("Connection", "keep-alive")
//                  .addHeader("Access-Control-Allow-Origin", "*")
//                  .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
//                  .addHeader("Vary", "Accept-Encoding")
//                  .addHeader("Accept-Encoding", "*")
//                  .addHeader("Cookie", "add cookies here")
                    .build();
            return chain.proceed(request);
        }
    }

}
