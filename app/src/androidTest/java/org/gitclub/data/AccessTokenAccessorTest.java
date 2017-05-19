package org.gitclub.data;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.gitclub.model.AccessToken;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by le on 5/16/17.
 */
@RunWith(AndroidJUnit4.class)
public class AccessTokenAccessorTest {

    @Test
    public void accessTokenAccessorTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        String email = "songtao542@gmail.com";
        AccessTokenAccessor accessor = new AccessTokenAccessor(appContext);
        AccessToken token = new AccessToken();
        String tokenStr = "token" + System.currentTimeMillis();
        token.accessToken = tokenStr;
        token.scope = "scope1 scope2";
        token.tokenType = "type1";
        token.email = email;
        long userid = System.currentTimeMillis();
        token.userId = userid;
        boolean result = accessor.insertOrUpdateByEmail(token);
        assertTrue(result);

        boolean result1 = accessor.updateUserKeyByEmail(email, System.currentTimeMillis() + 12345);
        assertTrue(result1);

        AccessToken qtoken = accessor.queryByEmail(email);
        assertNotNull(qtoken);
        assertEquals(qtoken.email, email);
        assertEquals(qtoken.accessToken, tokenStr);
        assertNotEquals(qtoken.userId, userid);
    }
}
