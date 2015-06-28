package com.sample.structure.screen;

import android.app.Application;

import com.example.model_lib.OsInterfaceSettings;
import com.sample.structure.screen.osinterface.FilesDirProvider;
import com.sample.structure.screen.osinterface.MainThreadHandler;

/**
 * Created by takahiro on 2015/06/29.
 */
public class StructureApplication extends Application{
    OsInterfaceSettings osSettings;

    @Override
    public void onCreate() {
        osSettings = OsInterfaceSettings.getInstance();
        osSettings.init(new MainThreadHandler(),new FilesDirProvider(getApplicationContext()));
        super.onCreate();
    }

}
