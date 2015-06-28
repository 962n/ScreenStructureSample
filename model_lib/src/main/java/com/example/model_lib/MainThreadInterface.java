
package com.example.model_lib;

/**
 * Created by takahiro on 2015/06/28.
 */
public interface MainThreadInterface {
    /**
     * 引数で渡されたRunnableをUIスレッドで実行するようにしてください。
     * @param r
     */
    public void runUiThread(Runnable r);
}
