package org.gitclub.data;

import org.gitclub.model.AccessToken;

import java.util.HashMap;

/**
 * Created by wangsongtao on 2017/5/14.
 */


public class AccessTokenStore {

    HashMap<String, AccessToken> store = new HashMap<>();

    AccessTokenAccessor mAccessTokenAccessor;

    public AccessTokenStore(AccessTokenAccessor accessTokenAccessor) {
        mAccessTokenAccessor = accessTokenAccessor;
    }

    public AccessToken getAccessToken(String email) {
        if (store.get(email) == null) {
            AccessToken accessToken = mAccessTokenAccessor.queryByEmail(email);
            if (accessToken != null) {
                store.put(email, accessToken);
            }
        }
        return store.get(email);
    }

    public void setAccessToken(String email, AccessToken accessToken) {
        if (accessToken != null) {
            store.put(email, accessToken);
        }
    }
}
