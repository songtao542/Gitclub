package org.gitclub.ui.view;

import org.gitclub.model.Gist;
import org.gitclub.model.Star;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface GistsView extends Loading {
    void gists(List<Gist> gists);
}
