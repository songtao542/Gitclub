package com.aperise.gitclub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by le on 5/16/17.
 */

public class Repository extends Model {

    public String id;//1296269,
    public User owner;
    public String name;//"Hello-World",
    public String fullName;//"octocat/Hello-World",
    public String description;//"This your first repo!",
    @SerializedName("private")
    public boolean isPrivate;//false,
    public boolean fork;//true,
    public String url;//"https://api.github.com/repos/octocat/Hello-World",
    public String htmlUrl;//"https://github.com/octocat/Hello-World",
    public String archiveUrl;//"http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
    public String assigneesUrl;//"http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
    public String blobsUrl;//"http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
    public String branchesUrl;//"http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
    public String cloneUrl;//"https://github.com/octocat/Hello-World.git",
    public String collaboratorsUrl;//"http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
    public String commentsUrl;//"http://api.github.com/repos/octocat/Hello-World/comments{/number}",
    public String commitsUrl;//"http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
    public String compareUrl;//"http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
    public String contentsUrl;//"http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
    public String contributorsUrl;//"http://api.github.com/repos/octocat/Hello-World/contributors",
    public String deploymentsUrl;//"http://api.github.com/repos/octocat/Hello-World/deployments",
    public String downloadsUrl;//"http://api.github.com/repos/octocat/Hello-World/downloads",
    public String eventsUrl;//"http://api.github.com/repos/octocat/Hello-World/events",
    public String forksUrl;//"http://api.github.com/repos/octocat/Hello-World/forks",
    public String gitCommitsUrl;//"http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
    public String gitRefsUrl;//"http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
    public String gitTagsUrl;//"http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
    public String gitUrl;//"git:github.com/octocat/Hello-World.git",
    public String hooksUrl;//"http://api.github.com/repos/octocat/Hello-World/hooks",
    public String issueCommentUrl;//"http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
    public String issueEventsUrl;//"http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
    public String issuesUrl;//"http://api.github.com/repos/octocat/Hello-World/issues{/number}",
    public String keysUrl;//"http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
    public String labelsUrl;//"http://api.github.com/repos/octocat/Hello-World/labels{/name}",
    public String languagesUrl;//"http://api.github.com/repos/octocat/Hello-World/languages",
    public String mergesUrl;//"http://api.github.com/repos/octocat/Hello-World/merges",
    public String milestonesUrl;//"http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
    public String mirrorUrl;//"git:git.example.com/octocat/Hello-World",
    public String notificationsUrl;//"http://api.github.com/repos/octocat/Hello-World/notifications{?since, all, participating}",
    public String pullsUrl;//"http://api.github.com/repos/octocat/Hello_World/pulls{/number}",
    public String releasesUrl;//"http://api.github.com/repos/octocat/Hello_World/releases{/id}",
    public String sshUrl;//"git@github.com:octocat/Hello_World.git",
    public String stargazersUrl;//"http://api.github.com/repos/octocat/Hello_World/stargazers",
    public String statusesUrl;//"http://api.github.com/repos/octocat/Hello_World/statuses/{sha}",
    public String subscribersUrl;//"http://api.github.com/repos/octocat/Hello_World/subscribers",
    public String subscriptionUrl;//"http://api.github.com/repos/octocat/Hello_World/subscription",
    public String svnUrl;//"https://svn.github.com/octocat/Hello_World",
    public String tagsUrl;//"http://api.github.com/repos/octocat/Hello_World/tags",
    public String teamsUrl;//"http://api.github.com/repos/octocat/Hello_World/teams",
    public String treesUrl;//"http://api.github.com/repos/octocat/Hello_World/git/trees{/sha}",
    public String homepage;//"https://github.com",
    public String language;//null,
    public int forksCount;//9,
    public int stargazersCount;//80,
    public int watchersCount;//80,
    public int size;//108,
    public String defaultBranch;//"master",
    public int openIssuesCount;//0,
    public String[] topics;
    public boolean hasIssues;//true,
    public boolean hasWiki;//true,
    public boolean hasPages;//false,
    public boolean hasDownloads;//true,
    public String pushedAt;// "20110126T19:06:43Z",
    public String createdAt;// "20110126T19:01:12Z",
    public String updatedAt;// "20110126T19:14:43Z",
    public Permission permissions;
    public boolean allowRebaseMerge;// true,
    public boolean allowSquashMerge;// true,
    public boolean allowMergeCommit;// true,
    public int subscribersCount;// 42,
    public int networkCount;// 0

}
