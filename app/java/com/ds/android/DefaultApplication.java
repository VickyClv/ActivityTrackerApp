package com.ds.android;

import android.app.Application;

public class DefaultApplication extends Application {
    static String username;

    public static  void setUsername (String userName) {
        username = userName;
    }

    public  static String getUsername() {
        return username;
    }
}
