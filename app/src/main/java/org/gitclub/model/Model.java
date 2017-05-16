package org.gitclub.model;

import java.lang.reflect.Field;

/**
 * Created by le on 5/16/17.
 */

public class Model {

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
