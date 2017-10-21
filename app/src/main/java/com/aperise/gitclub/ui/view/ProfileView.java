package com.aperise.gitclub.ui.view;

import com.aperise.gitclub.model.Event;
import com.aperise.gitclub.model.Repository;

import java.util.List;

/**
 * Created by le on 5/16/17.
 */

public interface ProfileView extends Loading {
    void profile(List<Repository> repos);

    void events(List<Event> events);

    void overview(List<Repository> repos, List<Event> events);
}
