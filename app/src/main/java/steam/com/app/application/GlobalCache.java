package steam.com.app.application;

import android.content.Context;

/**
 * @author changsunhaipeng
 */
public class GlobalCache {
    private static final GlobalCache instance = new GlobalCache();
    private Context mContext;

    public GlobalCache() {
    }

    public static GlobalCache getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().mContext;
    }

    public void registerContext(Context context) {
        this.mContext = context;
    }
}
