package com.aperise.gitclub.net;

import com.aperise.gitclub.model.AccessToken;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public interface GithubApi {

    String CLIENT_ID = "8c148bb9efb6382368f4";
    String CLIENT_SECRET = "2a7befa5276c01a74f5b919020c2c3f6a4f6f1c9";
    /**
     * Before use,you need to add scope,example: user:email
     */
    String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=";


    /**
     * Before use,you need to add code
     */
    String ACCESS_TOKEN = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&accept=:json&code=";


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
