package com.sample.structure.screen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.sample.structure.screen.R;

/**
 * アプリ起動時のスプラッシュ画面アクティビティー
 */
public class SplashActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //通常はログインなどの通信処理を行った後にMainActivityを起動させるがいったんモックとして
        //2秒後にMainActivityを起動する
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(SplashActivity.this.isFinishing()){
                    //既にユーザーがアプリを終了させようとしている場合は特になにもしない
                    return;
                }
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();

            }
        },2000);
    }

}
