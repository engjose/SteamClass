package steam.com.app.application;

import android.app.Application;

/**
 * File: SteamClassApplication.java
 */
public class SteamClassApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalCache.getInstance().registerContext(this);
    }
}
