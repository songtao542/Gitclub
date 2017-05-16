package org.gitclub.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.ProviderTestCase2;

import org.gitclub.model.AccessToken;
import org.gitclub.provider.GitclubContent;
import org.gitclub.provider.GitclubProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by le on 5/16/17.
 */
@RunWith(AndroidJUnit4.class)
public class AccessTokenAccessorTest extends ProviderTestCase2<GitclubProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public AccessTokenAccessorTest(Class<GitclubProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    @Test
    public void testSave() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("org.gitclub", appContext.getPackageName());
        AccessTokenAccessor accessor = new AccessTokenAccessor(appContext);
        AccessToken token = new AccessToken();
        token.accessToken = "1234567890";
        token.scope = "scope1 scope2";
        token.tokenType = "type1";
        assertNull(accessor.save(token));

//        appContext.getContentResolver().query(GitclubContent.AccessToken.CONTENT_URI, null, GitclubContent.AccessTokenColumns.ACCESS_TOKEN + "=?", new String[]{"1234567890"}, null);
    }
}
