package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.DramaGridAdapter;
import cn.jas0n.amovie.adapter.VideoGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.CateBean;
import cn.jas0n.amovie.bean.DramaBean;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.activity.SeasonDetailActivity;
import cn.jas0n.amovie.ui.activity.VideoDetailActivity;
import cn.jas0n.amovie.ui.view.FixedGridView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class CategoryFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected View mView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.category)
    TextView mLastUpdateCate;
    @BindView(R.id.grid)
    FixedGridView mLastUpdateGrid;
    @BindView(R.id.rec_category)
    TextView mRecCate;
    @BindView(R.id.rec_grid)
    FixedGridView mRecGrid;
    @BindView(R.id.hot_category)
    TextView mHotCate;
    @BindView(R.id.hot_grid)
    FixedGridView mHotGrid;
    @BindView(R.id.error)
    TextView mError;
    @BindView(R.id.empty)
    TextView mEmpty;

    private int cateId;

    private VideoGridAdapter mLastUpdateAdapter;
    private VideoGridAdapter mRecAdapter;
    private VideoGridAdapter mHotAdapter;
    private List<RecBean.HotVideoItem> mLastUpdate = new ArrayList<>();
    private List<RecBean.HotVideoItem> mRecommended = new ArrayList<>();
    private List<RecBean.HotVideoItem> mHot = new ArrayList<>();

    public static CategoryFragment newInstance(int cateId) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cate, container, false);
        }
        ButterKnife.bind(this, mView);

        showProgress();
        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRefreshLayout.setOnRefreshListener(this);
        cateId = getArguments().getInt("cateId");
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getCategoryIndex(cateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CateBean>() {
                    @Override
                    public void call(CateBean cateBean) {
                        Logger.d(cateBean.toString());
                        if (cateBean.getData().getHot().size() == 0)
                            showEmpty();
                        else
                            fillData(cateBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        showError();
                    }
                });
    }

    private void fillData(CateBean dramaBean) {
        mLastUpdate.clear();
        mRecommended.clear();
        mHot.clear();
        mLastUpdate = dramaBean.getData().getLastest();
        mRecommended = dramaBean.getData().getRecommended();
        mHot = dramaBean.getData().getHot();
        if (mLastUpdate.size() >= 4) {
            mLastUpdate = mLastUpdate.subList(0, 4);
        }
        if (mRecommended.size() >= 4) {
            mRecommended = mRecommended.subList(0, 4);
        }
        if (mHot.size() >= 4) {
            mHot = mHot.subList(0, 4);
        }

        mLastUpdateAdapter = new VideoGridAdapter(mLastUpdate, getContext());
        mRecAdapter = new VideoGridAdapter(mRecommended, getContext());
        mHotAdapter = new VideoGridAdapter(mHot, getContext());

        mLastUpdateGrid.setAdapter(mLastUpdateAdapter);
        mRecGrid.setAdapter(mRecAdapter);
        mHotGrid.setAdapter(mHotAdapter);
        mLastUpdateAdapter.setClickVideo(getClickVideo());
        mRecAdapter.setClickVideo(getClickVideo());
        mHotAdapter.setClickVideo(getClickVideo());

        showContent();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    private ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video),
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), image,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video));
                }
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
}
