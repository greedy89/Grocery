package com.senos.seno.grocery;

import android.app.Application;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

@Database(name = ApplicationGrocery.DATABASE_NAME, version = ApplicationGrocery.DATABASE_VERSION)
public class ApplicationGrocery extends Application{
    public static final String DATABASE_NAME = "groceryDb";
    public static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
