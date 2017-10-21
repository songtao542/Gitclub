package com.aperise.gitclub.ui.view;

import com.aperise.gitclub.model.Star;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface StarsView extends Loading {
    void stars(List<Star> stars);
}
