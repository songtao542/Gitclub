package org.gitclub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangsongtao on 2017/5/20.
 */

public class Event extends Model {


    /**
     * type : Event
     * public : true
     * payload : {}
     * repo : {"id":3,"name":"octocat/Hello-World","url":"https://api.github.com/repos/octocat/Hello-World"}
     * actor : {"id":1,"login":"octocat","gravatar_id":"","avatar_url":"https://github.com/images/error/octocat_happy.gif","url":"https://api.github.com/users/octocat"}
     * org : {"id":1,"login":"github","gravatar_id":"","url":"https://api.github.com/orgs/github","avatar_url":"https://github.com/images/error/octocat_happy.gif"}
     * created_at : 2011-09-06T17:26:27Z
     * id : 12345
     */

    public String type;
    @SerializedName("public")
    public boolean isPublic;
    public Payload payload;
    public Repository repo;
    public Actor actor;
    public Organization org;
    public String createdAt;
    public String id;


}
