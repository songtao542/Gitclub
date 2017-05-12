package org.gitclub.utils;

import org.gitclub.model.User;

import java.lang.reflect.Field;

/**
 * Created by le on 5/9/17.
 */

public class ToString {

    private ToString() {
    }

    public static String toString(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder("{\n");
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName() + ":" + f.get(obj) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("}");
        return builder.toString();
    }
}
