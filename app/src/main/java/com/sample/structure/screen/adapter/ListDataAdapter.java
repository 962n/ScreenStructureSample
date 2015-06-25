package com.sample.structure.screen.adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.structure.screen.R;
import com.sample.structure.screen.provider.DataProvider;

/**
 * GridFragmentのデータアダプタークラス
 */
public class ListDataAdapter extends BaseAdapter {

    private DataProvider mProvider;
    private Context mContext;
    private LayoutInflater mInflater;

    /**
     * コンストラクター
     * @param context アプリケーションコンテキスト
     */
    public ListDataAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setProvider(DataProvider provider){
        mProvider = provider;
    }

    @Override
    public int getCount() {
        if(mProvider == null){
            return 0;
        }

        int providerCount = mProvider.getCount();
        return providerCount;
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

        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.listview_item,parent,false);
            holder = new ViewHolder();
            holder.mText = (TextView)convertView.findViewById(R.id.list_item_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.mText.setText("No."+ (position+1));

        return convertView;
    }

    private static class ViewHolder{
        TextView mText;
    }
}
