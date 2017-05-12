package org.gitclub;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by le on 5/8/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    /**
     * @return
     */
    GithubApi api();

    /**
     * @return
     */
    Context applicationContext();

    /**
     * @return
     */
    SharedPreferences sharedPreferences();
}
