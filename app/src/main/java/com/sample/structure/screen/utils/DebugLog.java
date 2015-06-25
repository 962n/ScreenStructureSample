package com.sample.structure.screen.utils;

import android.util.Log;

/**
 * デバッグログ出力クラス
 */
public class DebugLog {
    private static final boolean IS_DEBUG = true;

    public static void d(String tag, String msg){
        if(IS_DEBUG){
            Log.d(tag,msg);
        }
    }

}
