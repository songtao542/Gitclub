package org.gitclub.net;

import org.gitclub.model.Gist;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wangsongtao on 2017/5/28.
 */

public interface GistApi {

    //https://gist.github.com/songtao542
    @GET("{username}")
//    Observable<String> rxgists(@Path("username") String username, @Query("page") int page, @Query("per_page") int pageSize);
    Observable<String> rxgists(@Path("username") String username);
}
