package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.DramaAdapter;
import cn.jas0n.amovie.adapter.DramaGridAdapter;
import cn.jas0n.amovie.adapter.RecAdapter;
import cn.jas0n.amovie.adapter.VideoGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.DramaBean;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.ui.activity.HotSeasonsActivity;
import cn.jas0n.amovie.ui.activity.SeasonDetailActivity;
import cn.jas0n.amovie.ui.activity.SeasonQueryActivity;
import cn.jas0n.amovie.ui.view.FixedGridView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class DramaFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    protected View mView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.category)
    TextView mLastUpdateCate;
    @BindView(R.id.grid)
    FixedGridView mLastUpdateGrid;
    @BindView(R.id.more)
    TextView mLastUpdateMore;
    @BindView(R.id.rec_category)
    TextView mRecCate;
    @BindView(R.id.rec_grid)
    FixedGridView mRecGrid;
    @BindView(R.id.rec_more)
    TextView mRecMore;
    @BindView(R.id.hot_category)
    TextView mHotCate;
    @BindView(R.id.hot_grid)
    FixedGridView mHotGrid;
    @BindView(R.id.hot_more)
    TextView mHotMore;
    @BindView(R.id.error)
    TextView mError;
    @BindView(R.id.empty)
    TextView mEmpty;

    private DramaGridAdapter mLastUpdateAdapter;
    private DramaGridAdapter mRecAdapter;
    private DramaGridAdapter mHotAdapter;
    private List<RecBean.RecDramaItem> mLastUpdate = new ArrayList<>();
    private List<RecBean.RecDramaItem> mRecommended = new ArrayList<>();
    private List<RecBean.RecDramaItem> mHot = new ArrayList<>();

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_drama, container, false);
        }
        ButterKnife.bind(this, mView);

        showProgress();
        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRefreshLayout.setOnRefreshListener(this);

        mLastUpdateMore.setOnClickListener(this);
        mRecMore.setOnClickListener(this);
        mHotMore.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getSeasonIndex("3.0.3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DramaBean>() {
                    @Override
                    public void call(DramaBean dramaBean) {
                        if (dramaBean.getData().getHot().size() == 0)
                            showEmpty();
                        else
                            fillData(dramaBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        showError();
                    }
                });
    }

    private void fillData(DramaBean dramaBean) {
        mLastUpdate.clear();
        mRecommended.clear();
        mHot.clear();
        mLastUpdate = dramaBean.getData().getLastUpdate();
        mRecommended = dramaBean.getData().getRecommended();
        mHot = dramaBean.getData().getHot();

        mLastUpdateAdapter = new DramaGridAdapter(mLastUpdate, getContext());
        mRecAdapter = new DramaGridAdapter(mRecommended, getContext());
        mHotAdapter = new DramaGridAdapter(mHot, getContext());

        mLastUpdateGrid.setAdapter(mLastUpdateAdapter);
        mRecGrid.setAdapter(mRecAdapter);
        mHotGrid.setAdapter(mHotAdapter);
        mLastUpdateAdapter.setClickSeason(getClickSeason());
        mRecAdapter.setClickSeason(getClickSeason());
        mHotAdapter.setClickSeason(getClickSeason());

        showContent();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    private ClickSeason getClickSeason() {
        return new ClickSeason() {
            @Override
            public void onClickSeason(ImageView imageView, RecBean.RecDramaItem dramaItem) {
                startActivity(SeasonDetailActivity.newIntent(getContext(), dramaItem));
            }
        };
    }

    @Override
    public void onRefresh() {
        initData();
    }

    private void showError() {
        hideAll();
        mError.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        hideAll();
        mRefreshLayout.setProgressViewOffset(false, 0, 150);
        mRefreshLayout.setRefreshing(true);
    }

    private void showEmpty() {
        hideAll();
        mEmpty.setVisibility(View.VISIBLE);
    }

    private void showContent() {
        hideAll();
        mContent.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
        mContent.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.more:
                startActivity(new Intent(getContext(), SeasonQueryActivity.class));
                break;
            case R.id.rec_more:
                startActivity(HotSeasonsActivity.newIntent(getContext(), false, getString(R.string
                        .recommended)));
                break;
            case R.id.hot_more:
                startActivity(HotSeasonsActivity.newIntent(getContext(), true, getString(R.string
                        .hot_season)));
                break;
        }
    }
}
