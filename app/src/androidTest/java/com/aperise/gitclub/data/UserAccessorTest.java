package com.aperise.gitclub.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.aperise.gitclub.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by le on 5/19/17.
 */
public class UserAccessorTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void userAccessorTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        String email = "songtao542@gmail.com";
        UserAccessor accessor = new UserAccessor(appContext);
        User user = new User();

        user.login = "songtao542";
        user.id = 5105572;
        user.avatarUrl = "https=//avatars3.githubusercontent.com/u/5105572?v=3";
        user.gravatarId = "";
        user.url = "https=//api.github.com/users/songtao542";
        user.htmlUrl = "https=//github.com/songtao542";
        user.followersUrl = "https=//api.github.com/users/songtao542/followers";
        user.followingUrl = "https=//api.github.com/users/songtao542/following{/other_user}";
        user.gistsUrl = "https=//api.github.com/users/songtao542/gists{/gist_id}";
        user.starredUrl = "https=//api.github.com/users/songtao542/starred{/owner}{/repo}";
        user.subscriptionsUrl = "https=//api.github.com/users/songtao542/subscriptions";
        user.organizationsUrl = "https=//api.github.com/users/songtao542/orgs";
        user.reposUrl = "https=//api.github.com/users/songtao542/repos";
        user.eventsUrl = "https=//api.github.com/users/songtao542/events{/privacy}";
        user.receivedEventsUrl = "https=//api.github.com/users/songtao542/received_events";
        user.type = "User";
        user.siteAdmin = false;
        user.name = null;
        user.company = null;
        user.blog = null;
        user.location = null;
        user.email = email;
        user.hireable = null;
        user.bio = null;
        user.publicRepos = 2;
        user.publicGists = 0;
        user.followers = 4;
        user.following = 3;
        user.createdAt = "2013-07-28T05=27=44Z";
        user.updatedAt = "2017-03-04T15=21=22Z";
        user.totalPrivateRepos = 0;
        user.ownedPrivateRepos = 0;
        user.diskUsage = 30857;
        user.collaborators = 0;
        user.twoFactorAuthentication = false;

        boolean result = accessor.insertOrUpdateByEmail(user);
        assertTrue(result);

        User u = accessor.queryByEmail(email);
        assertNotNull(u);

        long id = accessor.queryKeyByEmail(email);
        assertTrue(id != -1);
    }

}