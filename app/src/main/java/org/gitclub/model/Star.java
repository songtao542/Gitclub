package org.gitclub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by le on 5/27/17.
 */

public class Star extends Model {


    /**
     * id : 1296269
     * owner : {"login":"octocat","id":1,"avatar_url":"https://github.com/images/error/octocat_happy.gif","gravatar_id":"","url":"https://api.github.com/users/octocat","html_url":"https://github.com/octocat","followers_url":"https://api.github.com/users/octocat/followers","following_url":"https://api.github.com/users/octocat/following{/other_user}","gists_url":"https://api.github.com/users/octocat/gists{/gist_id}","starred_url":"https://api.github.com/users/octocat/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/octocat/subscriptions","organizations_url":"https://api.github.com/users/octocat/orgs","repos_url":"https://api.github.com/users/octocat/repos","events_url":"https://api.github.com/users/octocat/events{/privacy}","received_events_url":"https://api.github.com/users/octocat/received_events","type":"User","site_admin":false}
     * name : Hello-World
     * full_name : octocat/Hello-World
     * description : This your first repo!
     * private : false
     * fork : false
     * url : https://api.github.com/repos/octocat/Hello-World
     * html_url : https://github.com/octocat/Hello-World
     * archive_url : http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}
     * assignees_url : http://api.github.com/repos/octocat/Hello-World/assignees{/user}
     * blobs_url : http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}
     * branches_url : http://api.github.com/repos/octocat/Hello-World/branches{/branch}
     * clone_url : https://github.com/octocat/Hello-World.git
     * collaborators_url : http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}
     * comments_url : http://api.github.com/repos/octocat/Hello-World/comments{/number}
     * commits_url : http://api.github.com/repos/octocat/Hello-World/commits{/sha}
     * compare_url : http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}
     * contents_url : http://api.github.com/repos/octocat/Hello-World/contents/{+path}
     * contributors_url : http://api.github.com/repos/octocat/Hello-World/contributors
     * deployments_url : http://api.github.com/repos/octocat/Hello-World/deployments
     * downloads_url : http://api.github.com/repos/octocat/Hello-World/downloads
     * events_url : http://api.github.com/repos/octocat/Hello-World/events
     * forks_url : http://api.github.com/repos/octocat/Hello-World/forks
     * git_commits_url : http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}
     * git_refs_url : http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}
     * git_tags_url : http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}
     * git_url : git:github.com/octocat/Hello-World.git
     * hooks_url : http://api.github.com/repos/octocat/Hello-World/hooks
     * issue_comment_url : http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}
     * issue_events_url : http://api.github.com/repos/octocat/Hello-World/issues/events{/number}
     * issues_url : http://api.github.com/repos/octocat/Hello-World/issues{/number}
     * keys_url : http://api.github.com/repos/octocat/Hello-World/keys{/key_id}
     * labels_url : http://api.github.com/repos/octocat/Hello-World/labels{/name}
     * languages_url : http://api.github.com/repos/octocat/Hello-World/languages
     * merges_url : http://api.github.com/repos/octocat/Hello-World/merges
     * milestones_url : http://api.github.com/repos/octocat/Hello-World/milestones{/number}
     * mirror_url : git:git.example.com/octocat/Hello-World
     * notifications_url : http://api.github.com/repos/octocat/Hello-World/notifications{?since, all, participating}
     * pulls_url : http://api.github.com/repos/octocat/Hello-World/pulls{/number}
     * releases_url : http://api.github.com/repos/octocat/Hello-World/releases{/id}
     * ssh_url : git@github.com:octocat/Hello-World.git
     * stargazers_url : http://api.github.com/repos/octocat/Hello-World/stargazers
     * statuses_url : http://api.github.com/repos/octocat/Hello-World/statuses/{sha}
     * subscribers_url : http://api.github.com/repos/octocat/Hello-World/subscribers
     * subscription_url : http://api.github.com/repos/octocat/Hello-World/subscription
     * svn_url : https://svn.github.com/octocat/Hello-World
     * tags_url : http://api.github.com/repos/octocat/Hello-World/tags
     * teams_url : http://api.github.com/repos/octocat/Hello-World/teams
     * trees_url : http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}
     * homepage : https://github.com
     * language : null
     * forks_count : 9
     * stargazers_count : 80
     * watchers_count : 80
     * size : 108
     * default_branch : master
     * open_issues_count : 0
     * topics : ["octocat","atom","electron","API"]
     * has_issues : true
     * has_wiki : true
     * has_pages : false
     * has_downloads : true
     * pushed_at : 2011-01-26T19:06:43Z
     * created_at : 2011-01-26T19:01:12Z
     * updated_at : 2011-01-26T19:14:43Z
     * permissions : {"admin":false,"push":false,"pull":true}
     * allow_rebase_merge : true
     * allow_squash_merge : true
     * allow_merge_commit : true
     * subscribers_count : 42
     * network_count : 0
     */

    public int id;
    public User owner;
    public String name;
    public String fullName;
    public String description;
    @SerializedName("public")
    public boolean isPublic;
    public boolean fork;
    public String url;
    public String htmlUrl;
    public String archiveUrl;
    public String assigneesUrl;
    public String blobsUrl;
    public String branchesUrl;
    public String cloneUrl;
    public String collaboratorsUrl;
    public String commentsUrl;
    public String commitsUrl;
    public String compareUrl;
    public String contentsUrl;
    public String contributorsUrl;
    public String deploymentsUrl;
    public String downloadsUrl;
    public String eventsUrl;
    public String forksUrl;
    public String gitCommitsUrl;
    public String gitRefsUrl;
    public String gitTagsUrl;
    public String gitUrl;
    public String hooksUrl;
    public String issueCommentUrl;
    public String issueEventsUrl;
    public String issuesUrl;
    public String keysUrl;
    public String labelsUrl;
    public String languagesUrl;
    public String mergesUrl;
    public String milestonesUrl;
    public String mirrorUrl;
    public String notificationsUrl;
    public String pullsUrl;
    public String releasesUrl;
    public String sshUrl;
    public String stargazersUrl;
    public String statusesUrl;
    public String subscribersUrl;
    public String subscriptionUrl;
    public String svnUrl;
    public String tagsUrl;
    public String teamsUrl;
    public String treesUrl;
    public String homepage;
    public String language;
    public int forksCount;
    public int stargazersCount;
    public int watchersCount;
    public int size;
    public String defaultBranch;
    public int openIssuesCount;
    public boolean hasIssues;
    public boolean hasWiki;
    public boolean hasPages;
    public boolean hasDownloads;
    public String pushedAt;
    public String createdAt;
    public String updatedAt;
    public Permission  permissions;
    public boolean allowRebaseMerge;
    public boolean allowSquashMerge;
    public boolean allowMergeCommit;
    public int subscribersCount;
    public int networkCount;
    public List<String> topics;





}
