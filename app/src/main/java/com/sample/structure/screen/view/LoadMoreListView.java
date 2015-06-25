package com.sample.structure.screen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.paging.gridview.LoadingView;
import com.sample.structure.screen.utils.DebugLog;

/**
 * 追加読み込みプログレスを表示させるためのListViewクラス
 * ※PagingGridViewの設計思想をそのまま本クラスに踏襲
 */
public class LoadMoreListView extends ListView{
    public interface OnLoadMoreListener {
        void onLoadMoreItems();
    }

    private static final String TAG = LoadMoreListView.class.getSimpleName();
    private boolean isLoading;
    private boolean hasMoreItems;
    private OnLoadMoreListener pagingableListener;
    private LoadingView loadinView;

    public LoadMoreListView(Context context) {
        super(context);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        DebugLog.d(TAG,"setOnLoadMore");
        this.pagingableListener = listener;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        if(!this.hasMoreItems) {
            loadinView.setVisibility(View.GONE);
        } else {
            loadinView.setVisibility(View.VISIBLE);
        }
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }


    public void onFinishLoading(boolean hasMoreItems) {
        setHasMoreItems(hasMoreItems);
        setIsLoading(false);
    }

    private void init() {
        DebugLog.d(TAG,"init");
        isLoading = false;
        loadinView = new LoadingView(getContext());
        addFooterView(loadinView);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //DO NOTHING...
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    DebugLog.d(TAG,"totalItemCount = "+totalItemCount);
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                        DebugLog.d(TAG,"oraora");
                        if (pagingableListener != null) {
                            DebugLog.d(TAG,"onLoadmore");
                            isLoading = true;
                            pagingableListener.onLoadMoreItems();
                        }

                    }
                }
            }
        });
    }

}
