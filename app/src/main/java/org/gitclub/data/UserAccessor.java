package org.gitclub.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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

    /**
     * @param user
     * @return true if insertOrUpdate success, otherwise false
     */
    public boolean insertOrUpdateByEmail(User user) {
        ArrayList<User> users = User.fromCursor(query(GitclubContent.User.CONTENT_URI, null, GitclubContent.UserColumns.EMAIL + "=?", new String[]{user.email}, null));
        String selection = null;
        String[] selectionArgs = null;
        if (users != null && users.size() > 0) {
            selection = GitclubContent.UserColumns._ID + "=?";
            selectionArgs = new String[]{String.valueOf(users.get(0).id)};
        }
        return insertOrUpdate(user, selection, selectionArgs);
    }

    /**
     * @param user
     * @param selection
     * @param selectionArgs
     * @return true if insertOrUpdate success, otherwise false
     */
    public boolean insertOrUpdate(User user, String selection, String[] selectionArgs) {
        if (selection != null && selection.length() > 0) {
            int rows = update(GitclubContent.User.CONTENT_URI, user.toContentValues(), selection, selectionArgs);
            SLog.d(this, "insertOrUpdate-> update rows=" + rows);
            if (rows > 0) {
                return true;
            }
        } else {
            Uri uri = insert(GitclubContent.User.CONTENT_URI, user.toContentValues());
            SLog.d(this, "insertOrUpdate-> insert uri=" + uri);
            if (uri != null) {
                return true;
            }
        }
        return true;
    }

    /**
     * @param email
     * @return A User or null
     */
    public User queryByEmail(String email) {
        SLog.d(this, "queryByEmail paramter: email=" + email);
        if (email == null) {
            return null;
        }
        ArrayList<User> users = User.fromCursor(query(GitclubContent.User.CONTENT_URI, null, GitclubContent.UserColumns.EMAIL + "=?", new String[]{email}, null));
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    /**
     * @param email
     * @return The Primary key(_id) or -1
     */
    public long queryKeyByEmail(String email) {
        SLog.d(this, "queryByEmail paramter: email=" + email);
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
