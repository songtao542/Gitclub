package org.gitclub.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by le on 3/9/17.
 */

public class GitclubContent {
    public static final String AUTHORITY = "org.gitclub.provider";


    public static final String PARAMETER_LIMIT = "limit";

    public interface AccessTokenColumns extends BaseColumns {
        String USER_KEY = "userKey";
        String USER_EMAIL = "userEmail";
        String ACCESS_TOKEN = "accessToken";
        String TOKEN_TYPE = "tokenType";
        String SCOPE = "scope";
    }

    public interface UserColumns extends BaseColumns {
        String LOGIN = "login";
        String ID = "id";
        String AVATAR_URL = "avatarUrl";
        String GRAVATAR_ID = "gravatarId";
        String URL = "url";
        String HTML_URL = "htmlUrl";
        String FOLLOWERS_URL = "followersUrl";
        String FOLLOWING_URL = "followingUrl";
        String GISTS_URL = "gistsUrl";
        String STARRED_URL = "starredUrl";
        String SUBSCRIPTIONS_URL = "subscriptionsUrl";
        String ORGANIZATIONS_URL = "organizationsUrl";
        String REPOS_URL = "reposUrl";
        String EVENTS_URL = "eventsUrl";
        String RECEIVED_EVENTS_URL = "receivedEventsUrl";
        String TYPE = "type";
        String SITE_ADMIN = "siteAdmin";
        String NAME = "name";
        String COMPANY = "company";
        String BLOG = "blog";
        String LOCATION = "location";
        String EMAIL = "email";
        String HIREABLE = "hireable";
        String BIO = "bio";
        String PUBLIC_REPOS = "publicRepos";
        String PUBLIC_GISTS = "publicGists";
        String FOLLOWERS = "followers";
        String FOLLOWING = "following";
        String CREATED_AT = "createdAt";
        String UPDATED_AT = "updatedAt";
        String TOTAL_PRIVATE_REPOS = "totalPrivateRepos";
        String OWNED_PRIVATE_REPOS = "ownedPrivateRepos";
        String DISK_USAGE = "diskUsage";
        String COLLABORATORS = "collaborators";
        String TWO_FACTOR_AUTHENTICATION = "twoFactorAuthentication";
    }

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public GitclubContent() {
    }


    public static class AccessToken extends GitclubContent {
        public static final String TABLE_NAME = "AccessToken";
        public static final Uri CONTENT_URI = Uri.parse(GitclubContent.CONTENT_URI + "/access_token");
    }

    public static class User extends GitclubContent {
        public static final String TABLE_NAME = "User";
        public static final Uri CONTENT_URI = Uri.parse(GitclubContent.CONTENT_URI + "/user");
    }
}
