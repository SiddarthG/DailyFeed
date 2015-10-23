package com.hackerearth.sid.dailyfeed;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {
    private  static MyApp sInstance;
    @Override
    public void onCreate(){
        super.onCreate();
        sInstance=this;
    }

    public static MyApp getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return getsInstance().getApplicationContext();
    }
}
