package com.android.library;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {

    public static final String VERSION_CODE = "001.100.010";
    
    public static Context curContext;
    public static Handler handler;
    public static boolean DEBUG = true;
    
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        curContext = this;
    }

    public static long getRealTime() {
        return System.currentTimeMillis();
    }

    public static String getSid() {
        return "sid_001_002_003";
    }
}