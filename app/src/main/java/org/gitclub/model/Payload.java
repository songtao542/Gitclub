package org.gitclub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangsongtao on 2017/5/20.
 */

public class Payload extends Model {

    /**
     * push_id : 1752308532
     * size : 1
     * distinct_size : 1
     * ref_type : repository
     * master_branch : master
     * description : null
     * pusher_type : user
     * ref : refs/heads/master
     * head : ccef34c92503d599a2302fa3d450074b759e9b08
     * before : 152872ee9b66f4811f99c7e1826dd3b64c70c0c9
     * commits : [{"sha":"ccef34c92503d599a2302fa3d450074b759e9b08","author":{"email":"takahiko03@gmail.com","name":"takahi-i"},"message":"Bump v1.9","distinct":true,"url":"https://api.github.com/repos/redpen-cc/redpen-doc/commits/ccef34c92503d599a2302fa3d450074b759e9b08"}]
     */

    private int pushId;
    private int size;
    private int distinctSize;
    private String ref;
    private String head;
    private String before;
    private List<Commit> commits;
    private String refType;
    private String masterBranch;
    private String description;
    private String pusherType;



}
