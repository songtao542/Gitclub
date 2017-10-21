package com.aperise.gitclub.model;

/**
 * Created by wangsongtao on 2017/5/21.
 */

public class Commit extends Model {
    /**
     * sha : ccef34c92503d599a2302fa3d450074b759e9b08
     * author : {"email":"takahiko03@gmail.com","name":"takahi-i"}
     * message : Bump v1.9
     * distinct : true
     * url : https://api.github.com/repos/redpen-cc/redpen-doc/commits/ccef34c92503d599a2302fa3d450074b759e9b08
     */

    public String sha;
    public Author author;
    public String message;
    public boolean distinct;
    public String url;
}
