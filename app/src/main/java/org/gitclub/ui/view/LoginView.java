package org.gitclub.ui.view;

import org.gitclub.model.AccessToken;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public interface LoginView extends Loading {
    void accessToken(AccessToken accessToken);
}
