package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
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

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

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
import cn.jas0n.amovie.ui.activity.SeasonDetailActivity;
import cn.jas0n.amovie.ui.view.FixedGridView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class DramaFragment extends LazyFragment {

    protected View mView;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;

    protected RecyclerArrayAdapter mAdapter;
    private DramaBean mDramaBean;
    private List<RecBean.RecDramaItem> mLastUpdate = new ArrayList<>();
    private List<RecBean.RecDramaItem> mRecommended = new ArrayList<>();
    private List<RecBean.RecDramaItem> mHot = new ArrayList<>();

    private View mHeaderView;
    private RecyclerArrayAdapter.ItemView mHeader;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_drama, container, false);
        }
        ButterKnife.bind(this, mView);

        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setErrorView(R.layout.view_error_large);
        mAdapter = new DramaAdapter(getContext());
        ((DramaAdapter) mAdapter).setClickSeason(getClickSeason());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doOnRefresh();
            }
        });
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getSeasonIndex("3.0.3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DramaBean>() {
                    @Override
                    public void call(DramaBean dramaBean) {
                        mDramaBean = dramaBean;
                        fillData(dramaBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mRecyclerView.showError();
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

        mAdapter.clear();

        mHeader = new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout
                        .layout_drama_header, parent, false);
                mHeaderView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return mHeaderView;
            }

            @Override
            public void onBindView(View headerView) {
                DramaGridAdapter lastUpdateAdapter = new DramaGridAdapter(mLastUpdate, getContext());
                DramaGridAdapter recAdapter = new DramaGridAdapter(mRecommended, getContext());
                TextView lastUpdateCategory = (TextView) headerView.findViewById(R.id.category);
                FixedGridView lastUpdateGrid = (FixedGridView) headerView.findViewById(R.id.grid);
                TextView recCategory = (TextView) headerView.findViewById(R.id.rec_category);
                FixedGridView recGrid = (FixedGridView) headerView.findViewById(R.id.rec_grid);
                TextView hotCategory = (TextView) headerView.findViewById(R.id.hot_category);
                lastUpdateCategory.setText("最近更新");
                recCategory.setText("小编推荐");
                hotCategory.setText("热门剧集");
                lastUpdateGrid.setAdapter(lastUpdateAdapter);
                recGrid.setAdapter(recAdapter);
                lastUpdateAdapter.setClickSeason(getClickSeason());
                recAdapter.setClickSeason(getClickSeason());
            }
        };
        mAdapter.addHeader(mHeader);
        mAdapter.addAll(mHot);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    protected void doOnRefresh() {
        initData();
    }

    private ClickSeason getClickSeason() {
        return new ClickSeason() {
            @Override
            public void onClickSeason(ImageView imageView, RecBean.RecDramaItem dramaItem) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(SeasonDetailActivity.newIntent(getContext(), dramaItem),
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(SeasonDetailActivity.newIntent(getContext(), dramaItem));
                }
            }
        };
    }
}
