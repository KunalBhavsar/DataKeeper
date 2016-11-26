package com.jyotitech.passwordskeeper.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kunal on 05/11/16.
 */

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils sharedPreferenceUtils;

    private SharedPreferences sharedPreferences;

    private String KEY_PIN = "user_pin";
    private String KEY_FIREBASE_UID = "firebase_uid";


    private SharedPreferenceUtils(Context context, String appName) {
        sharedPreferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
    }

    public static void init(Context context, String appName) {
        if (sharedPreferenceUtils == null) {
            sharedPreferenceUtils = new SharedPreferenceUtils(context, appName);
        }
    }

    public static SharedPreferenceUtils getInstance() {
        return sharedPreferenceUtils;
    }

    public void setPin(int pin) {
        sharedPreferences.edit().putInt(KEY_PIN, pin).apply();
    }

    public int getPin() {
        return sharedPreferences.getInt(KEY_PIN, 0);
    }

    public void setFirebaseUid(String firebaseUid)  {
        sharedPreferences.edit().putString(KEY_FIREBASE_UID, firebaseUid).apply();
    }

    public String getFirebaseUid() {
        return sharedPreferences.getString(KEY_FIREBASE_UID, null);
    }
}
