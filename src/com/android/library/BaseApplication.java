package com.android.library;

import android.app.Application;

public class BaseApplication extends Application {

    public static final String VERSION_CODE = "001.100.010";
    
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static long getRealTime() {
        return System.currentTimeMillis();
    }

    public static String getSid() {
        return "sid_001_002_003";
    }
}