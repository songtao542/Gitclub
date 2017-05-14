package org.gitclub.data;

import android.content.Context;
import android.database.Cursor;

import org.gitclub.model.AccessToken;
import org.gitclub.model.User;
import org.gitclub.provider.GitclubContent;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class UserAccessor extends Accessor {

    @Inject
    public UserAccessor(Context context) {
        super(context);
    }

    public void insertOrUpdateByEmail(User user) {
        ArrayList<User> users = User.fromCursor(query(GitclubContent.User.CONTENT_URI, null, GitclubContent.UserColumns.EMAIL + "=?", new String[]{user.email}, null));
        String selection = null;
        String[] selectionArgs = null;
        if (users != null && users.size() > 0) {
            selection = GitclubContent.AccessTokenColumns._ID + "=?";
            selectionArgs = new String[]{String.valueOf(users.get(0).id)};
        }
        insertOrUpdate(user, selection, selectionArgs);
    }

    public void insertOrUpdate(User user, String selection, String[] selectionArgs) {
        if (selection != null && selection.length() > 0) {
            update(GitclubContent.AccessToken.CONTENT_URI, user.toContentValues(), selection, selectionArgs);
        } else {
            insert(GitclubContent.AccessToken.CONTENT_URI, user.toContentValues());
        }
    }

    public User queryByEmail(String email) {
        SLog.d("queryByEmail email=" + email);
        if (email == null) {
            return null;
        }
        ArrayList<User> users = User.fromCursor(query(GitclubContent.User.CONTENT_URI, null, GitclubContent.UserColumns.EMAIL + "=?", new String[]{email}, null));
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public long queryIdByEmail(String email) {
        SLog.d("queryByEmail email=" + email);
        if (email == null) {
            return -1;
        }
        Cursor cursor = query(GitclubContent.User.CONTENT_URI, new String[]{GitclubContent.UserColumns._ID}, GitclubContent.UserColumns.EMAIL + "=?", new String[]{email}, null);
        if (cursor != null && cursor.getCount() > 0) {
            try {
                cursor.moveToNext();
                return cursor.getLong(cursor.getColumnIndex(GitclubContent.UserColumns._ID));
            } finally {
                cursor.close();
            }
        }
        return -1;
    }
}
