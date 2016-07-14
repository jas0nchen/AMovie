package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.VideoAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.CategoryQueryBean;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.activity.VideoDetailActivity;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/7/12
 * E-mail:chendong90x@gmail.com
 */
public class VideoQueryFragment extends LazyFragment implements SwipeRefreshLayout
        .OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    private static final int ROWS = 20;
    protected View mView;
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;

    protected VideoAdapter mAdapter;

    private int mPage = 1;
    private int mCateId;
    private String mSort;
    private List<RecBean.HotVideoItem> mData = new ArrayList<>();

    public static VideoQueryFragment newInstance(int cateId, String sort) {
        VideoQueryFragment fragment = new VideoQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        bundle.putString("sort", sort);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_video_query, container, false);
        }
        ButterKnife.bind(this, mView);

        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.getRecyclerView().setHasFixedSize(true);

        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor
                (getContext(), R.color.divider), Utils.dip2px(getContext(), 0.5f),
                Utils.dip2px(getContext(), 160), Utils.dip2px(getContext(), 5f));
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapter = new VideoAdapter(getContext());
        mAdapter.setClickVideo(getClickVideo());
        mAdapter.setError(R.layout.view_error);
        mAdapter.setNoMore(R.layout.view_nomore);
        mAdapter.setMore(R.layout.view_more, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mCateId = getArguments().getInt("cateId");
        mSort = getArguments().getString("sort");
        mRecyclerView.setRefreshing(true, true);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        AMovieService.builder().getApiService().getCateQuery(mPage, ROWS, mSort, mCateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CategoryQueryBean>() {
                    @Override
                    public void call(CategoryQueryBean categoryQueryBean) {
                        Logger.d(categoryQueryBean.toString());
                        mPage++;
                        mData.clear();
                        mAdapter.clear();
                        mData.addAll(categoryQueryBean.getData().getResults());
                        mAdapter.addAll(mData);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mRecyclerView.showError();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        AMovieService.builder().getApiService().getCateQuery(mPage, ROWS, mSort, mCateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CategoryQueryBean>() {
                    @Override
                    public void call(CategoryQueryBean categoryQueryBean) {
                        Logger.d(categoryQueryBean.toString());
                        mPage++;
                        mData.addAll(categoryQueryBean.getData().getResults());
                        mAdapter.addAll(categoryQueryBean.getData().getResults());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mAdapter.pauseMore();
                    }
                });
    }

    private ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                startActivity(VideoDetailActivity.newIntent(getContext(), video));
            }
        };
    }
}
