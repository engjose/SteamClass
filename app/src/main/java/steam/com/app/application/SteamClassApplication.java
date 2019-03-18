package steam.com.app.application;

import android.app.Application;

/**
 * File: SteamClassApplication.java
 * <p>
 * Author: zshp
 * <p>
 * Create: 2019/3/18 11:16 PM
 */
public class SteamClassApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalCache.getInstance().registerContext(this);
    }
}
