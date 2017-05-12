package org.gitclub.ui.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.gitclub.GithubApi;
import org.gitclub.model.AccessToken;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by le on 5/9/17.
 */
@Module
public class LoginModule {

    LoginApi mApi;

    public LoginModule() {
    }

    @Provides
    public LoginApi provideLoginApi() {
        if (mApi == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("User-Agent", "Gitclub-App")
                            .build();
                    return chain.proceed(request);
                }
            });
            GsonBuilder gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://github.com/")
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .client(builder.build())
                    .build();
            mApi = retrofit.create(LoginApi.class);
        }
        return mApi;
    }


    public interface LoginApi {
        //https://github.com/login/oauth/access_token
        @POST("login/oauth/access_token")
        Call<AccessToken> accessToken(@Query("client_id") String clientId,
                                      @Query("client_secret") String clientSecret,
                                      @Query("code") String code);

        @POST("login/oauth/access_token")
        Observable<AccessToken> rxAccessToken(@Query("client_id") String clientId,
                                              @Query("client_secret") String clientSecret,
                                              @Query("code") String code);

        //https://github.com/login/session
        //@POST("session")
        //Call<Object> login(@Query("utf8")String utf8,@Query("authenticity_token")String authenticity_token,@Query("login") String username, @Query("password") String password);
    }
}


