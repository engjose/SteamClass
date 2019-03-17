package steam.com.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Store {

    public static final long DEFAULT_LONG_TYPE = -1L;
    public static final float DEFAULT_FLOAT_TYPE = -123;
    public static final int DEFAULT_INT_TYPE = -123;
    public static final String DEFAULT_STRING_TYPE = "";
    private static final String PACK_NAME = "store";

    private Store() {
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putString(key, value).apply();
    }

    // 默认值为 "" 所以做判断的时候 注意用TextUtils
    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, DEFAULT_STRING_TYPE);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putLong(key, value).apply();
    }

    // 默认值为 -1L 注意做判断是时候多加个 为-1L的判断
    public static long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, DEFAULT_LONG_TYPE);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    // 默认值为 -123 注意做判断的时候 多加个 为-123的判断
    public static int getInt(Context context, String key) {
        int result = DEFAULT_INT_TYPE;
        result = getSharedPreferences(context).getInt(key, DEFAULT_INT_TYPE);
        return result;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defVal) {
        return getSharedPreferences(context).getBoolean(key, defVal);
    }

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    // 默认值为 -123 注意做判断是时候多加个 为-123的判断
    public static float getFloat(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, DEFAULT_FLOAT_TYPE);
    }

    // 存储对象类型
    public static synchronized boolean saveObject(Context context, String key,
                                                  Object obj) {
        if (obj == null) {
            return false;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            Gson gson = new Gson();
            String value = gson.toJson(obj);
            editor.putString(key, value);
        } catch (Exception e) {
            Log.e("Store", e.getMessage());
        }
        return editor.commit();
    }

    // 获取对象类型
    public static <Object> Object getObject(Context context, String key,
                                            Class<Object> classOfT) {
        SharedPreferences preferences = getSharedPreferences(context);
        String str = preferences.getString(key, "");
        if (str.length() == 0) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(str, classOfT);
        } catch (Exception e) {
            Log.e("Store", e.getMessage());
        }
        return null;
    }

    // 获取对象类型
    public static <Object> Object getObject(Context context, String key,
                                            Type classOfT) {
        SharedPreferences preferences = getSharedPreferences(context);
        String str = preferences.getString(key, "");
        if (str.length() == 0) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(str, classOfT);
        } catch (Exception e) {
            Log.e("Store", e.getMessage());
        }
        return null;
    }

    public static void remove(Context context, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(key);
    }


    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PACK_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

}
