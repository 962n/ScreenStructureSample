package com.sample.structure.screen.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sample.structure.android.view.SlidingTabLayout.OnCurrentTabItemClickListener;
import com.sample.structure.screen.R;
import com.sample.structure.screen.activity.DetailActivity;
import com.sample.structure.screen.adapter.ListDataAdapter;
import com.sample.structure.screen.provider.DataProvider;
import com.sample.structure.screen.utils.DebugLog;
import com.sample.structure.screen.view.LoadMoreListView;

/**
 * 詳細表示用のフラグメント
 */
public class ListFragment extends Fragment implements OnCurrentTabItemClickListener{

    private static final String TAG = ListFragment.class.getSimpleName();
    //プルダウン更新用のレイヤークラス
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private DataProvider mDataProvider;

    private LoadMoreListView mListView;

    private ListDataAdapter mListAdapter;

    private static final int GRID_COLUMN = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataProvider = DataProvider.getSharedInstance(this.getActivity().getApplicationContext());
        mListAdapter = new ListDataAdapter(this.getActivity());
        mListAdapter.setProvider(mDataProvider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);

        // スワイプで更新時のプログレスの色設定
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        // Listenerをセット
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mListView = (LoadMoreListView) view.findViewById(R.id.listview);


        //追加読み込み時のリスナーを設定
        mListView.setOnLoadMoreListener(mLoadMoreListener);

        mListView.setAdapter(mListAdapter);

        if(!mDataProvider.isInitFinish()) {
            //初回のデータ取得が完了していない場合はデータ読み込みを開始する
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    //onViewCreated契機でsetRefreshingを行うとattachされるタイミングの
                    //関係上、プログレスがでなくなってしまうのでpostで遅延させる
                    mSwipeRefreshLayout.setRefreshing(true);
                    mOnRefreshListener.onRefresh();
                }
            });
        } else {
            //初回のデータ読み込みが既に完了している場合は追加読み込みを可能にする
            mListView.setHasMoreItems(true);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailPagerFragment.KEY_PAGE_POSITION,position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_back);
            }
        });



    }

    //プルリフレッシュ時に呼ばれるコールバック
    //※初回画面遷移時のデータ読み込みにも利用する
    SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            DebugLog.d(TAG, "onRefresh");
            mListView.setHasMoreItems(false);
            mDataProvider.getDataFirst(new DataProvider.OnResponseListener() {
                @Override
                public void onResponse(DataProvider provider) {
                    //追加読み込み可能にする
                    mListView.setHasMoreItems(true);
                    //プル更新プログレスを消す
                    mSwipeRefreshLayout.setRefreshing(false);
                    mListAdapter.notifyDataSetChanged();
                }
            });
            //データを0で表示したいのでAdapterを再更新する
            //※getDataFirst内でデータを0にしている
            mListAdapter.notifyDataSetChanged();
        }
    };

    //追加読み込み時に呼ばれるリスナー
    LoadMoreListView.OnLoadMoreListener mLoadMoreListener = new LoadMoreListView.OnLoadMoreListener() {
        @Override
        public void onLoadMoreItems() {
            DebugLog.d(TAG,"onLoadMoreItems");
            if(!mDataProvider.hasNext()){
                DebugLog.d(TAG,"追加なし");
                //読み込むデータがない場合は追加読み込みのコールバックを呼ばれないようにする
                mListView.onFinishLoading(false);
                return;
            }

            DebugLog.d(TAG,"追加あり");
            mDataProvider.getMoreData(new DataProvider.OnResponseListener() {
                @Override
                public void onResponse(DataProvider provider) {
                    mListView.onFinishLoading(true);
                    mListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    public void onCurrentTabItemClick() {
        DebugLog.d(TAG, "onCurrentTabItemClick");
        if(mListView != null){

        }
    }



}
