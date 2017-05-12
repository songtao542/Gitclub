package org.gitclub.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.gitclub.provider.GitclubContent;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by le on 3/31/17.
 */

public class User {
    public String login;
    public int id;// 5105572,
    public String avatarUrl;// "https;////avatars3.githubusercontent.com/u/5105572?v=3",
    public String gravatarId;// "",
    public String url;// "https;////api.github.com/users/songtao542",
    public String htmlUrl;// "https;////github.com/songtao542",
    public String followersUrl;// "https;////api.github.com/users/songtao542/followers",
    public String followingUrl;// "https;////api.github.com/users/songtao542/following{/other_user}",
    public String gistsUrl;// "https;////api.github.com/users/songtao542/gists{/gist_id}",
    public String starredUrl;// "https;////api.github.com/users/songtao542/starred{/owner}{/repo}",
    public String subscriptionsUrl;// "https;////api.github.com/users/songtao542/subscriptions",
    public String organizationsUrl;// "https;////api.github.com/users/songtao542/orgs",
    public String reposUrl;// "https;////api.github.com/users/songtao542/repos",
    public String eventsUrl;// "https;////api.github.com/users/songtao542/events{/privacy}",
    public String receivedEventsUrl;// "https;////api.github.com/users/songtao542/received_events",
    public String type;// "User",
    public boolean siteAdmin;// false,
    public String name;// null,
    public String company;// null,
    public String blog;// null,
    public String location;// null,
    public String email;// null,
    public String hireable;// null,
    public String bio;// null,
    public int publicRepos;// 2,
    public int publicGists;// 0,
    public int followers;// 4,
    public int following;// 3,
    public String createdAt;// "2013-07-28T05;//27;//44Z",
    public String updatedAt;// "2017-03-04T15;//21;//22Z"
    public int totalPrivateRepos;//0,
    public int ownedPrivateRepos;// 0,
    public int diskUsage;//30857,
    public int collaborators;// 0,
    public boolean twoFactorAuthentication;// false,
//             "plan": {
//        "name": "free",
//                "space": 976562499,
//                "collaborators": 0,
//                "private_repos": 0


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(GitclubContent.UserColumns.LOGIN, login);
        values.put(GitclubContent.UserColumns.ID, id);
        values.put(GitclubContent.UserColumns.AVATAR_URL, avatarUrl);
        values.put(GitclubContent.UserColumns.GRAVATAR_ID, gravatarId);
        values.put(GitclubContent.UserColumns.URL, url);
        values.put(GitclubContent.UserColumns.HTML_URL, htmlUrl);
        values.put(GitclubContent.UserColumns.FOLLOWERS_URL, followersUrl);
        values.put(GitclubContent.UserColumns.FOLLOWING_URL, followingUrl);
        values.put(GitclubContent.UserColumns.GISTS_URL, gistsUrl);
        values.put(GitclubContent.UserColumns.STARRED_URL, starredUrl);
        values.put(GitclubContent.UserColumns.SUBSCRIPTIONS_URL, subscriptionsUrl);
        values.put(GitclubContent.UserColumns.ORGANIZATIONS_URL, organizationsUrl);
        values.put(GitclubContent.UserColumns.REPOS_URL, reposUrl);
        values.put(GitclubContent.UserColumns.EVENTS_URL, eventsUrl);
        values.put(GitclubContent.UserColumns.RECEIVED_EVENTS_URL, receivedEventsUrl);
        values.put(GitclubContent.UserColumns.TYPE, type);
        values.put(GitclubContent.UserColumns.SITE_ADMIN, siteAdmin ? 1 : 0);
        values.put(GitclubContent.UserColumns.NAME, name);
        values.put(GitclubContent.UserColumns.COMPANY, company);
        values.put(GitclubContent.UserColumns.BLOG, blog);
        values.put(GitclubContent.UserColumns.LOCATION, location);
        values.put(GitclubContent.UserColumns.EMAIL, email);
        values.put(GitclubContent.UserColumns.HIREABLE, hireable);
        values.put(GitclubContent.UserColumns.BIO, bio);
        values.put(GitclubContent.UserColumns.PUBLIC_REPOS, publicRepos);
        values.put(GitclubContent.UserColumns.PUBLIC_GISTS, publicGists);
        values.put(GitclubContent.UserColumns.FOLLOWERS, followers);
        values.put(GitclubContent.UserColumns.FOLLOWING, following);
        values.put(GitclubContent.UserColumns.CREATED_AT, createdAt);
        values.put(GitclubContent.UserColumns.UPDATED_AT, updatedAt);
        values.put(GitclubContent.UserColumns.TOTAL_PRIVATE_REPOS, totalPrivateRepos);
        values.put(GitclubContent.UserColumns.OWNED_PRIVATE_REPOS, ownedPrivateRepos);
        values.put(GitclubContent.UserColumns.DISK_USAGE, diskUsage);
        values.put(GitclubContent.UserColumns.COLLABORATORS, collaborators);
        values.put(GitclubContent.UserColumns.TWO_FACTOR_AUTHENTICATION, twoFactorAuthentication ? 1 : 0);
        return values;
    }

    public static ArrayList<User> fromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ArrayList<User> users = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            User user = new User();
            user.login = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.LOGIN));
            user.id = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.ID));
            user.avatarUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.AVATAR_URL));
            user.gravatarId = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.GRAVATAR_ID));
            user.url = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.URL));
            user.htmlUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.HTML_URL));
            user.followersUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.FOLLOWERS_URL));
            user.followingUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.FOLLOWING_URL));
            user.gistsUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.GISTS_URL));
            user.starredUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.STARRED_URL));
            user.subscriptionsUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.SUBSCRIPTIONS_URL));
            user.organizationsUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.ORGANIZATIONS_URL));
            user.reposUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.REPOS_URL));
            user.eventsUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.EVENTS_URL));
            user.receivedEventsUrl = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.RECEIVED_EVENTS_URL));
            user.type = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.TYPE));
            user.siteAdmin = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.SITE_ADMIN)) == 1 ? true : false;
            user.name = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.NAME));
            user.company = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.COMPANY));
            user.blog = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.BLOG));
            user.location = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.LOCATION));
            user.email = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.EMAIL));
            user.hireable = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.HIREABLE));
            user.bio = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.BIO));
            user.publicRepos = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.PUBLIC_REPOS));
            user.publicGists = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.PUBLIC_GISTS));
            user.followers = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.FOLLOWERS));
            user.following = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.FOLLOWING));
            user.createdAt = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.CREATED_AT));
            user.updatedAt = cursor.getString(cursor.getColumnIndex(GitclubContent.UserColumns.UPDATED_AT));
            user.totalPrivateRepos = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.TOTAL_PRIVATE_REPOS));
            user.ownedPrivateRepos = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.OWNED_PRIVATE_REPOS));
            user.diskUsage = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.DISK_USAGE));
            user.collaborators = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.COLLABORATORS));
            user.twoFactorAuthentication = cursor.getInt(cursor.getColumnIndex(GitclubContent.UserColumns.TWO_FACTOR_AUTHENTICATION)) == 1 ? true : false;
            users.add(user);
        }
        return users;
    }

    @Override
    public String toString() {
        Field[] fields = User.class.getDeclaredFields();
        StringBuilder builder = new StringBuilder("{\n");
        try {
            for (Field f : fields) {
                builder.append(f.getName() + ":" + f.get(this) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("}");
        return builder.toString();
    }
}
