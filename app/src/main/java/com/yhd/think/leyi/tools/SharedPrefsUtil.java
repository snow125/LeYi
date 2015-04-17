package com.yhd.think.leyi.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.yhd.think.leyi.data.Constants;

/**
 * Created by snow on 2015/2/23.
 */
public class SharedPrefsUtil {

    private static Context context;

    public static void init(Context _context){
        context = _context;
    }

    public static void putValue(String key, int value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit();
        sp.putInt(key, value);
        sp.commit();
    }
    public static void putValue(String key, boolean value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit();
        sp.putBoolean(key, value);
        sp.commit();
    }
    public static void putValue(String key, String value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }
    public static int getValue(String key, int defValue) {
        SharedPreferences sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }
    public static boolean getValue(String key, boolean defValue) {
        SharedPreferences sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }
    public static String getValue(String key, String defValue) {
        SharedPreferences sp =  context.getSharedPreferences(Constants.TAG_SP, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

}
