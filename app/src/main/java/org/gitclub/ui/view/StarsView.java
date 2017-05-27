package org.gitclub.ui.view;

import org.gitclub.model.Event;
import org.gitclub.model.Repository;
import org.gitclub.model.Star;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface StarsView extends Loading {
    void stars(List<Star> stars);
}
