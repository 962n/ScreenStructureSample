package com.sample.structure.screen.osinterface;

import android.os.Handler;

import com.example.model_lib.MainThreadInterface;


/**
 * Created by takahiro on 2015/06/29.
 */
public class MainThreadHandler implements MainThreadInterface{
    private Handler handler;
    public MainThreadHandler() {
        handler = new Handler();
    }

    @Override
    public void runUiThread(Runnable r) {
        handler.post(r);
    }
}
