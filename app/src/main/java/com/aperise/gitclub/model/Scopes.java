package com.aperise.gitclub.model;

/**
 * Created by le on 5/10/17.
 */

public class Scopes {

    public static final String user = "user";//	Grants read/write access to profile info only. Note that this scope includes user:email and user:follow.
    public static final String user$email = "user:email";//	Grants read access to a user's email addresses.
    public static final String user$follow = "user:follow";//	Grants access to follow or unfollow other users.
    public static final String public_repo = "public_repo";//	Grants read/write access to code, commit statuses, collaborators, and deployment statuses for public repositories and organizations. Also required for starring public repositories.
    public static final String repo = "repo";//	Grants read/write access to code, commit statuses, invitations, collaborators, adding team memberships, and deployment statuses for public and private repositories and organizations.
    public static final String repo_deployment = "repo_deployment";//	Grants access to deployment statuses for public and private repositories. This scope is only necessary to grant other users or services access to deployment statuses, without granting access to the code.
    public static final String repo$status = "repo:status";//	Grants read/write access to public and private repository commit statuses. This scope is only necessary to grant other users or services access to private repository commit statuses without granting access to the code.
    public static final String delete_repo = "delete_repo";//	Grants access to delete adminable repositories.
    public static final String notifications = "notifications";//	Grants read access to a user's notifications. repo also provides this access.
    public static final String gist = "gist";//	Grants write access to gists.
    public static final String read$repo_hook = "read:repo_hook";//	Grants read and ping access to hooks in public or private repositories.
    public static final String write$repo_hook = "write:repo_hook";//	Grants read, write, and ping access to hooks in public or private repositories.
    public static final String admin$repo_hook = "admin:repo_hook";//	Grants read, write, ping, and delete access to hooks in public or private repositories.
    public static final String admin$org_hook = "admin:org_hook";//	Grants read, write, ping, and delete access to organization hooks. Note: OAuth tokens will only be able to perform these actions on organization hooks which were created by the OAuth application. Personal access tokens will only be able to perform these actions on organization hooks created by a user.
    public static final String read$org = "read:org";//	Read-only access to organization, teams, and membership.
    public static final String write$org = "write:org";//	Publicize and unpublicize organization membership.
    public static final String admin$org = "admin:org";//Fully manage organization, teams, and memberships.
    public static final String read$public_key = "read:public_key";//List and view details for public keys.
    public static final String write$public_key = "write:public_key";//Create, list, and view details for public keys.
    public static final String admin$public_key = "admin:public_key";//Fully manage public keys.
    public static final String read$gpg_key = "read:gpg_key";//List and view details for GPG keys.
    public static final String write$gpg_key = "write:gpg_key";//Create, list, and view details for GPG keys.
    public static final String admin$gpg_key = "admin:gpg_key";//Fully manage GPG keys.
}
