package org.gitclub.model;

/**
 * Created by wangsongtao on 2017/5/29.
 */

public class Milestone extends Model {
    public String url;
    public String htmlUrl;
    public String labelsUrl;
    public int id;
    public int number;
    public String state;
    public String title;
    public String description;
    public User creator;
    public int openIssues;
    public int closedIssues;
    public String createdAt;
    public String updatedAt;
    public String closedAt;
    public String dueOn;
}
