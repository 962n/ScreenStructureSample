package com.sample.structure.screen.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sample.structure.screen.R;
import com.sample.structure.screen.provider.DataProvider;
import com.sample.structure.screen.view.RoundImageView;

/**
 * GridFragmentのデータアダプタークラス
 */
public class GridDataAdapter extends BaseAdapter {

    private DataProvider mProvider;
    private Context mContext;
    private int mColumn;

    //グリッドビューの一個のビューのサイズ
    private int mGridSize;

    /**
     * コンストラクター
     * @param context アプリケーションコンテキスト
     * @param column  グリッドビューのカラム数
     */
    public GridDataAdapter(Context context,int column){
        mContext = context;

        mColumn = column;

        // WindowManagerのインスタンス取得
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        // Displayのインスタンス取得
        Display display = wm.getDefaultDisplay();
        mGridSize = display.getWidth()/column;
    }

    public void setProvider(DataProvider provider){
        mProvider = provider;
    }

    @Override
    public int getCount() {
        if(mProvider == null){
            return 0;
        }

        /*
        * PagingGridViewの特性上、例えば3カラムのGridにした場合に
        * 3×n個数分のデータ数でなければ追加読み込み時のプログレスが
        * 正しく表示されないため、ここでデータ数をみて3×n個分のデータ数にまるめて返却する
        */

        int providerCount = mProvider.getCount();

        if(providerCount == 0){
            return 0;
        }

        //カラム数、データ数から補填する個数を算出
        int count = 0;
        if(providerCount % mColumn > 0){
            count = mColumn - (providerCount % mColumn);
        }
        return providerCount + count;
    }

    @Override
    public Object getItem(int position) {
        if(mProvider == null || position > (mProvider.getCount()-1)){
            return null;
        }
        return mProvider.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if(convertView == null){
            RoundImageView image = new RoundImageView(mContext);
            image.setImageResource(R.drawable.ic_launcher);
            image.setLayoutParams(new AbsListView.LayoutParams(mGridSize,mGridSize));
            convertView = image;
        } else {

        }
        if(this.getItem(position) == null){
            //カラム×n個数分の補正用のpositionであればnullが返ってくるので
            //その場合はviewはnullで返却する。
            convertView.setVisibility(View.GONE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
