package com.jyotitech.passwordskeeper;

import android.app.Application;

import com.jyotitech.passwordskeeper.utils.SharedPreferenceUtils;

/**
 * Created by Kunal on 05/11/16.
 */

public class PassKeeperApp extends Application {

    private static final String TAG = PassKeeperApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtils.init(this, TAG);
    }
}
