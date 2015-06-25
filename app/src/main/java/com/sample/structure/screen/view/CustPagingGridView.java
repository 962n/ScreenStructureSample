package com.sample.structure.screen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.paging.gridview.HeaderGridView;
import com.paging.gridview.LoadingView;

/**
 * PagingGridの場合に追加読み込みプログレスの表示•非表示ができないのでそれをコントロールできるようにしたクラス
 * ※ほぼPagingGridをコピーペーストし、プログレスの切り替えのみを実装
 */
public class CustPagingGridView extends HeaderGridView {

    public interface Pagingable {
        void onLoadMoreItems();
    }

    private boolean isLoading;
    private boolean hasMoreItems;
    private Pagingable pagingableListener;
    private LoadingView loadinView;

    public CustPagingGridView(Context context) {
        super(context);
        init();
    }

    public CustPagingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustPagingGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPagingableListener(Pagingable pagingableListener) {
        this.pagingableListener = pagingableListener;
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
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                        if (pagingableListener != null) {
                            isLoading = true;
                            pagingableListener.onLoadMoreItems();
                        }

                    }
                }
            }
        });
    }

}
