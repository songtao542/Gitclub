package org.gitclub.model;

/**
 * Created by wangsongtao on 2017/5/29.
 */

public class Ref extends Model {
    public String label;
    public String ref;
    public String sha;
    public User user;
    public Repository repo;
}
