package com.aperise.gitclub.data;

import com.aperise.gitclub.model.AccessToken;
import com.aperise.gitclub.model.User;

/**
 * Created by wangsongtao on 2017/5/14.
 */


public class UserTokenStore {

    private AccessTokenAccessor mAccessTokenAccessor;
    private UserAccessor mUserAccessor;

    private AccessToken mAccessToken;
    private User mUser;

    public UserTokenStore(AccessTokenAccessor accessTokenAccessor, UserAccessor userAccessor) {
        this.mAccessTokenAccessor = accessTokenAccessor;
        this.mUserAccessor = userAccessor;
    }

    /**
     * @return The latest stored AccessToken.
     */
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    public User getUser() {
        return mUser;
    }


    /**
     * Store the token.
     *
     * @param accessToken
     */
    public void storeAccessToken(AccessToken accessToken) {
        if (accessToken != null) {
            mAccessToken = accessToken;
        }
    }

    /**
     * Store the user.
     *
     * @param user
     */
    public void storeUser(User user) {
        if (user != null) {
            mUser = user;
        }
    }

    /**
     * Restore the user and token from db.
     *
     * @param email
     * @return true if restored,otherwise false.
     */
    public boolean restoreUserToken(String email) {
        mUser = mUserAccessor.queryByEmail(email);
        mAccessToken = mAccessTokenAccessor.queryByEmail(email);
        if (mUser == null || mAccessToken == null) {
            return false;
        }
        return true;
    }

    /**
     * 保存或者更新token.
     *
     * @param accessToken
     */
    public void insertOrUpdateTokenByEmail(AccessToken accessToken) {
        mAccessTokenAccessor.insertOrUpdateByEmail(accessToken);
    }

    /**
     * 保存或者更新user.
     *
     * @param user
     */
    public void insertOrUpdateUserByEmail(User user) {
        mUserAccessor.insertOrUpdateByEmail(user);
    }

    /**
     * 查询user key from User table.
     *
     * @param email
     * @return The user key
     */
    public long queryUserKeyByEmail(String email) {
        return mUserAccessor.queryKeyByEmail(email);
    }

    /**
     * 更新AccessToken's user key.
     *
     * @param email
     * @param userId
     */
    public void updateTokenUserKeyByEmail(String email, long userId) {
        mAccessTokenAccessor.updateUserKeyByEmail(email, userId);
    }
}
