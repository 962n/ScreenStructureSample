package com.sample.structure.screen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.structure.screen.R;
import com.sample.structure.screen.provider.DataProvider;
/**
 *
 */
public class DetailPagerAdapter extends PagerAdapter{

    private static final String TAG = DetailPagerAdapter.class.getSimpleName();

    private Context mContext;
    private DataProvider mProvider;
    private LayoutInflater mInflater;

    public DetailPagerAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setProvider(DataProvider provider){
        mProvider = provider;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // View を生成
        View view = mInflater.inflate(R.layout.detail_pager_item,container,false);
        TextView textView = (TextView)view.findViewById(R.id.pager_item_label);
        textView.setText("Page:" + (position + 1));
        textView.setGravity(Gravity.CENTER);

        // コンテナに追加
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(mProvider == null){
            return 0;
        }
        return mProvider.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        Log.d(TAG, "isViewFromObject");
        Log.d(TAG, "view = "+view);
        Log.d(TAG, "object = "+o);
        return view.equals(o);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem");
        Log.d(TAG, "object = "+object);
        Log.d(TAG, "position = "+position);
        container.removeView((View) object);
    }
}
