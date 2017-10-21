package com.aperise.gitclub.ui.view;

import com.aperise.gitclub.model.User;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public interface LoginView extends Loading {
    void success(User user);
}
