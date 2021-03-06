package com.kun.news.nba.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kun.baselib.fragment.AbsFragment;
import com.kun.news.R;
import com.kun.news.app.Constant;
import com.kun.news.common.presenter.IBaseListView;
import com.kun.news.common.utils.UIUtils;
import com.kun.news.nba.adapter.NbaFeedAdapter;
import com.kun.news.nba.model.NewsIndex;
import com.kun.news.nba.presenter.NbaFeedIndexPresenter;
import com.kun.news.nba.presenter.NbaFeedIndexView;
import com.kun.news.nba.presenter.NbaFeedPresenter;
import com.kun.news.zhihu.event.ScrollEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by jiangkun on 16/9/24.
 */

public class NbaFragment extends AbsFragment implements IBaseListView, NbaFeedIndexView, OnRefreshListener, OnLoadMoreListener {
    SwipeToLoadLayout mSwipeToLoadLayout;
    RecyclerView mListView;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    private NbaFeedPresenter mPresenter;
    private NbaFeedAdapter mFeedAdapter;
    private NbaFeedIndexPresenter mIndexPresenter;
    private List<NewsIndex.IndexBean> mIndexBeen;
    int start = 0;
    final int count = 10;
    private String mType;

    public static NbaFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_NBA_TYPE, type);
        NbaFragment fragment = new NbaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter = new NbaFeedPresenter();
        mPresenter.bindView(this);

        mIndexPresenter = new NbaFeedIndexPresenter();
        mIndexPresenter.bindView(this);

        mFeedAdapter = new NbaFeedAdapter();
        mListView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mFeedAdapter);
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) < 10 || Math.abs(dx) > Math.abs(dy)) return;
                if (dy < 0) {//down->show
                    EventBus.getDefault().post(new ScrollEvent(ScrollEvent.SCROLL_DOWN));
                } else {//up->hide
                    EventBus.getDefault().post(new ScrollEvent(ScrollEvent.SCROLL_UP));
                }
            }
        });

        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        onRefresh();
    }

    @Override
    protected void initView(View root) {
        initArguments();
        mSwipeToLoadLayout = (SwipeToLoadLayout) root.findViewById(R.id.swipe_to_load_layout);
        mListView = (RecyclerView) root.findViewById(R.id.swipe_target);
    }

    private void initArguments() {
        Bundle bundle = getArguments();
        mType = bundle.getString(Constant.EXTRA_NBA_TYPE);
        if (TextUtils.isEmpty(mType)) {
            UIUtils.displayToast(getActivity(), "类型错误");
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_net_easy;
    }

    @Override
    public void onRefreshSuccess(List data, Object... extra) {
        mProgressBar.setVisibility(View.GONE);
        mFeedAdapter.setData(data);
        mSwipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFailed() {
        mSwipeToLoadLayout.setRefreshing(false);
        UIUtils.displayToast(getActivity(), "刷新失败!");
    }

    @Override
    public void onLoadMoreSuccess(List data, Object... extra) {
        mFeedAdapter.setDataAfterLoadMore(data);
        mSwipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onLoadMoreFailed() {
        mSwipeToLoadLayout.setLoadingMore(false);
        UIUtils.displayToast(getActivity(), "加载失败!");
    }

    //load NBA index
    @Override
    public void onLoadSuccess(NewsIndex newsIndex) {
        mIndexBeen = newsIndex.data;
        String ids = getIds();
        mPresenter.refreshData(ids, mType);
    }

    private String getIds() {
        String result = "";
        for (int i = start; i < start + count; i++) {
            if (i == start + count - 1) {
                result += mIndexBeen.get(i).id;
            } else
                result += mIndexBeen.get(i).id + ",";
        }
        start += count;
        return result;
    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onRefresh() {
        mIndexPresenter.refreshData(mType);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMoreData(getIds(), mType);
    }
}
