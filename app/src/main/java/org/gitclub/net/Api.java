package org.gitclub.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.gitclub.data.AccessTokenStore;
import org.gitclub.model.AccessToken;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class Api {

    private AccessTokenStore mAccessTokenStore;
    private String mUserEmail;

    private GithubApi mGithubApi;
    private GithubApiV3 mGithubApiV3;

    @Inject
    public Api(AccessTokenStore accessTokenStore) {
        mAccessTokenStore = accessTokenStore;
    }

    public void setUserEmail(String email) {
        mUserEmail = email;
    }

    public GithubApiV3 getGithubApiV3() {
        return createApiV3();
    }

    public GithubApi getGithubApi() {
        return createApi();
    }

    private GithubApiV3 createApiV3() {
        if (mUserEmail != null && mGithubApiV3 != null) {
            return mGithubApiV3;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(interceptor);
        AccessToken token = mAccessTokenStore.getAccessToken(mUserEmail);
        builder.addInterceptor(new HeaderInterceptor(token.accessToken));
        GsonBuilder gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(builder.build())
                .build();
        mGithubApiV3 = retrofit.create(GithubApiV3.class);
        return mGithubApiV3;
    }

    private GithubApi createApi() {
        if (mGithubApi != null) {
            return mGithubApi;
        }
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
        mGithubApi = retrofit.create(GithubApi.class);
        return mGithubApi;
    }

    static class HeaderInterceptor implements Interceptor {

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
