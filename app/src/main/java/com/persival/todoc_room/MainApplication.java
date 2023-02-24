package com.persival.todoc_room;

import android.app.Application;

public class MainApplication extends Application {
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
    }
}
