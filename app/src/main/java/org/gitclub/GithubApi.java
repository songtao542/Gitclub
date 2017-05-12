package org.gitclub;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.gitclub.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by le on 3/31/17.
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

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);

    //       Accept: application/vnd.github.v3+json
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Gitclub-App"
    })
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user")
    Observable<User> rxGetUser();

    //https://api.github.com/user/repos?type=public
    @POST("user/repos")
    Call<JsonArray> repos(@Query("type") String type);

    @GET("user/repos")
    Call<JsonArray> repos();

    /**
     * @param scopes       array	A list of scopes that this authorization is in.
     * @param note         string	Required. A note to remind you what the OAuth token is for. Tokens not associated with a specific OAuth application (i.e. personal access tokens) must have a unique note.
     * @param noteUrl      string	A URL to remind you what app the OAuth token is for.
     * @param clientId     string	The 20 character OAuth app client key for which to create the token.
     * @param clientSecret string	The 40 character OAuth app client secret for which to create the token.
     * @param fingerprint  string	A unique string to distinguish an authorization from others created for the same client ID and user.
     * @return
     */
    @POST("authorizations")
    Call<JsonObject> authorizations(@Query("scopes") ArrayList<String> scopes,
                                    @Query("note") String note,
                                    @Query("note_url") String noteUrl,
                                    @Query("client_id") String clientId,
                                    @Query("client_secret") String clientSecret,
                                    @Query("fingerprint") String fingerprint);

    ///authorizations/clients/:client_id

    /**
     * @param scopes
     * @param note
     * @param noteUrl
     * @param clientSecret
     * @param fingerprint
     * @return
     */
    @POST("authorizations/clients/8c148bb9efb6382368f4")
    Call<JsonObject> authorizationsForApp(@Query("scopes") String scopes,
                                          @Query("note") String note,
                                          @Query("note_url") String noteUrl,
                                          @Query("client_secret") String clientSecret,
                                          @Query("fingerprint") String fingerprint);

}
