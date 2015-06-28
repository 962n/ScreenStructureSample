package com.sample.structure.screen.osinterface;

import android.content.Context;
import android.util.Log;

import com.example.model_lib.FilePathInterface;

/**
 * Created by takahiro on 2015/06/29.
 */
public class FilesDirProvider implements FilePathInterface{
    private Context context;
    public FilesDirProvider(Context context) {
        this.context = context;
    }

    @Override
    public String getFilesDir() {
        String path = context.getFilesDir().toString();
        Log.d("gage", "path = "+path);
        Log.d("gage", "thared = "+Thread.currentThread().getName());
        return context.getFilesDir().toString();
    }
}
