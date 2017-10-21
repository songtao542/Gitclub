package com.aperise.gitclub.ui.view;

import com.aperise.gitclub.model.Gist;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface GistsView extends Loading {
    void gists(List<Gist> gists);
}
