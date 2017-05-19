package org.gitclub.data;

import android.content.Context;

import org.gitclub.model.AccessToken;

import java.util.HashMap;

/**
 * Created by wangsongtao on 2017/5/14.
 */


public class AccessTokenStore extends AccessTokenAccessor {

    private HashMap<String, AccessToken> store = new HashMap<>();
    private String mUserEmail;
    private AccessToken mAccessToken;

    public AccessTokenStore(Context context) {
        super(context);
    }

    /**
     * @param email
     * @return AccessToken or null.
     */
    public AccessToken getAccessToken(String email) {
        AccessToken accessToken = store.get(email);
        if (accessToken == null) {
            accessToken = queryByEmail(email);
            storeAccessToken(email, accessToken);
        }
        return accessToken;
    }

    /**
     * @return The latest stored AccessToken.
     */
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    /**
     * @return The latest stored Email Address.
     */
    public String getEmailAddress() {
        return mUserEmail;
    }

    /**
     * Store the email address and token;Update the latest email address and token.
     *
     * @param email
     * @param accessToken
     */
    public void storeAccessToken(String email, AccessToken accessToken) {
        if (accessToken != null) {
            mUserEmail = email;
            mAccessToken = accessToken;
            store.put(email, accessToken);
        }
    }


}
