package cn.jas0n.amovie.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.util.Utils;

/**
 * Author: Jas0n
 * Date: 2016/7/11
 * E-mail:chendong90x@gmail.com
 */
public abstract class RecyclerBaseActivity extends SwipeBackActivity implements
        SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(R.id.fake_status)
    View mFakeStatus;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecycler;
    protected List mData;

    protected RecyclerArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_base);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        if(mToolbar != null)
            setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(getTitleById());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            mFakeStatus.getLayoutParams().height = Utils.getStatusBarHeight(this);
        }

        mRecycler.setRefreshingColorResources(R.color.colorAccent);
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecycler.setRefreshListener(this);
        mRecycler.getRecyclerView().setHasFixedSize(true);
        setupAdapter();
        mAdapter.setError(R.layout.view_error);
        mAdapter.setNoMore(R.layout.view_nomore);
        if(isLoadMore()){
            mAdapter.setMore(R.layout.view_more, this);
        }
        mRecycler.setAdapter(mAdapter);

        mData = new ArrayList();

        mRecycler.setRefreshing(true, true);
    }

    protected abstract void initData();

    protected abstract void setupAdapter();

    public abstract String getTitleById();

    protected abstract boolean isLoadMore();

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    protected ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(VideoDetailActivity.newIntent(RecyclerBaseActivity.this, video),
                            ActivityOptions.makeSceneTransitionAnimation(RecyclerBaseActivity.this, image,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(VideoDetailActivity.newIntent(RecyclerBaseActivity.this, video));
                }
            }
        };
    }

    protected ClickSeason getClickSeason() {
        return new ClickSeason() {
            @Override
            public void onClickSeason(ImageView imageView, RecBean.RecDramaItem dramaItem) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(SeasonDetailActivity.newIntent(RecyclerBaseActivity.this, dramaItem),
                            ActivityOptions.makeSceneTransitionAnimation(RecyclerBaseActivity.this, imageView,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(SeasonDetailActivity.newIntent(RecyclerBaseActivity.this, dramaItem));
                }
            }
        };
    }

}
