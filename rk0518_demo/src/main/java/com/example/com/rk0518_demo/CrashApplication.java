package com.example.com.rk0518_demo;

import android.app.Application;

/**
 * Created by 老赵的拯救者 on 2018/5/18.
 */

public class CrashApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
