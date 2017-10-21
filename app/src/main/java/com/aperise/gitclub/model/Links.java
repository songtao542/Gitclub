package com.aperise.gitclub.model;

/**
 * Created by wangsongtao on 2017/5/29.
 */

public class Links extends Model {

    /**
     * self : {"href":"https://api.github.com/repos/octocat/Hello-World/pulls/1347"}
     * html : {"href":"https://github.com/octocat/Hello-World/pull/1347"}
     * issue : {"href":"https://api.github.com/repos/octocat/Hello-World/issues/1347"}
     * comments : {"href":"https://api.github.com/repos/octocat/Hello-World/issues/1347/comments"}
     * review_comments : {"href":"https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments"}
     * review_comment : {"href":"https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}"}
     * commits : {"href":"https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits"}
     * statuses : {"href":"https://api.github.com/repos/octocat/Hello-World/statuses/6dcb09b5b57875f334f61aebed695e2e4193db5e"}
     */

    public Href self;
    public Href html;
    public Href issue;
    public Href comments;
    public Href reviewComments;
    public Href reviewComment;
    public Href commits;
    public Href statuses;
}
