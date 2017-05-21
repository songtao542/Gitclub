package org.gitclub.ui.view;

import org.gitclub.model.Event;
import org.gitclub.model.Repository;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface ProfileView extends Loading {
    void profile(List<Repository> repos);

    void events(List<Event> events);

    void overview(List<Repository> repos, List<Event> events);
}
