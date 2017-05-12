package org.gitclub.model;

import android.content.ContentValues;

import org.gitclub.provider.GitclubContent;

import java.lang.reflect.Field;

/**
 * Created by le on 5/9/17.
 */

public class AccessToken {
    //{"access_token":"b482e9061b6a6c32c3ac72dd0cd94ed6093bb887","token_type":"bearer","scope":"user:email"}
    public String accessToken;
    public String tokenType;
    public String scope;

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(GitclubContent.AccessTokenColumns.ACCESS_TOKEN, accessToken);
        values.put(GitclubContent.AccessTokenColumns.TOKEN_TYPE, tokenType);
        values.put(GitclubContent.AccessTokenColumns.SCOPE, scope);
        return values;
    }

    @Override
    public String toString() {
        Field[] fields = getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder("{\n");
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName() + ":" + f.get(this) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("}");
        return builder.toString();
    }
}
