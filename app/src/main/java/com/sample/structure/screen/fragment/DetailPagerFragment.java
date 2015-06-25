package com.sample.structure.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.structure.screen.R;
import com.sample.structure.screen.adapter.DetailPagerAdapter;
import com.sample.structure.screen.provider.DataProvider;

/**
 * ダミーフラグメントとりあえず表示する用
 */
public class DetailPagerFragment extends Fragment {

    public static final String KEY_PAGE_POSITION = "KEY_PAGE_POSITION";
    private ViewPager mViewPager;
    private DataProvider mDataProvider;
    private DetailPagerAdapter mPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataProvider = DataProvider.getSharedInstance(this.getActivity().getApplicationContext());
        mPagerAdapter = new DetailPagerAdapter(this.getActivity());
        mPagerAdapter.setProvider(mDataProvider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            int position = bundle.getInt(KEY_PAGE_POSITION,0);
            mViewPager.setCurrentItem(position);
        }
        view.findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mViewPager.getCurrentItem()+1;
                if( index < mPagerAdapter.getCount()){
                    mViewPager.setCurrentItem( index );
                } else {
                    Toast.makeText(getActivity(),"追加読み込み中です",Toast.LENGTH_SHORT).show();
                    loadMore();
                }
            }
        });
        view.findViewById(R.id.left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mViewPager.getCurrentItem();
                if( index > 0){
                    mViewPager.setCurrentItem( index -1 );
                }

            }
        });
    }


    public void loadMore(){
        mDataProvider.getMoreData(new DataProvider.OnResponseListener() {
            @Override
            public void onResponse(DataProvider provider) {
                mPagerAdapter.notifyDataSetChanged();
            }
        });
    }
}
