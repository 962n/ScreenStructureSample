package com.sample.structure.screen.provider;

import android.content.Context;
import android.os.Handler;

import com.sample.structure.screen.utils.DebugLog;

import java.util.ArrayList;

/**
 * データ供給用のクラス
 */
public class DataProvider {

    private static final String TAG = DataProvider.class.getSimpleName();
    private static final Object sInstanceLock = new Object();
    private static DataProvider sInstance;
    private static final int MAX_SIZE = 200;

    private ArrayList<Object> mList;
    //初回のデータ取得が完了しているかどうか
    private boolean mIsInitFinish;

    /**
     * データの取得完了時のコールバックです。
     */
    public interface OnResponseListener{
        /**
         * 本コールバックが呼ばれたら描画更新をしてください
         * @param provider DataProviderクラス
         */
        public void onResponse(DataProvider provider);
    }

    /**
     * データプロバイダークラスを取得します
     * @param context アプリケーションコンテキスト
     * @return  共有インスタンス
     */
    public static DataProvider getSharedInstance(Context context){
        synchronized (sInstanceLock){
            if(sInstance == null){
                sInstance = new DataProvider(context);
            }
        }
        return sInstance;
    }

    /**
     * プライベートコンストラクター
     * @param context アプリケーションコンテキスト
     */
    private DataProvider(Context context){
        DebugLog.d(TAG, "DataProvider constracter");
        mList = new ArrayList<Object>();
        mIsInitFinish = false;
    }

    /**
     * データ数を返却します
     * @return データ数
     */
    public int getCount(){
        return mList.size();
    }

    /**
     * データを返却します
     * @param index データのインデックス
     * @return データ
     */
    public Object getItem(int index){
        return mList.get(index);
    }

    /**
     * 初回データを取得する
     * @param listener 取得完了リスナー
     */
    public void getDataFirst(final OnResponseListener listener){
        mList.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mIsInitFinish) {
                    mIsInitFinish = true;
                }
                for (int i = 0; i < 20; i++) {
                    mList.add(new Object());
                }
                if (listener != null) {
                    listener.onResponse(DataProvider.this);
                }
            }
        }, 2000);
    }

    /**
     * 追加データを取得する
     * @param listener 取得完了リスナー
     */
    public void getMoreData(final OnResponseListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    mList.add(new Object());
                }
                if (listener != null) {
                    listener.onResponse(DataProvider.this);
                }
            }
        }, 2000);
    }

    /**
     * 追加取得可能なデータがあるかどうか
     * @return true:可能/false:不可
     */
    public boolean hasNext(){
        boolean hasNext = false;
        if(MAX_SIZE > mList.size()){
            hasNext = true;
        }
        return hasNext;
    }

    /**
     * 初回のデータ取得が完了済みかどうか
     * @return true:取得済み/false:未取得
     */
    public boolean isInitFinish(){
        return mIsInitFinish;
    }



}
