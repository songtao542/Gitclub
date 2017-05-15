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
     * @return
     */
    public AccessToken getAccessToken(String email) {
        if (store.get(email) == null) {
            AccessToken accessToken = queryByEmail(email);
            if (accessToken != null) {
                store.put(email, accessToken);
            }
        }
        return store.get(email);
    }

    /**
     * @return The latest AccessToken.
     */
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    /**
     * @return
     */
    public String getEmailAddress() {
        return mUserEmail;
    }

    public void storeAccessToken(String email, AccessToken accessToken) {
        if (accessToken != null) {
            mUserEmail = email;
            mAccessToken = accessToken;
            store.put(email, accessToken);
        }
    }


}
