package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.CateSortedBean;
import cn.jas0n.amovie.bean.Constant;
import cn.jas0n.amovie.ui.adapter.NewVideoAdapter;
import cn.jas0n.amovie.ui.adapter.VideoGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.CateBean;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.activity.CategoryQueryActivity;
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

    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;

    private int cateId;
    private String mTitle;

    List<CateSortedBean> mSortedCateList = new ArrayList<>();
    private List<RecBean.HotVideoItem> mLastUpdate = new ArrayList<>();
    private List<RecBean.HotVideoItem> mRecommended = new ArrayList<>();
    private List<RecBean.HotVideoItem> mHot = new ArrayList<>();

    private NewVideoAdapter mAdapter;

    public static CategoryFragment newInstance(int cateId, String title) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_new_cate, container, false);
        }
        ButterKnife.bind(this, mView);

        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        cateId = getArguments().getInt("cateId");
        mTitle = getArguments().getString("title");

        mRecyclerView.setRefreshingColorResources(R.color.colorAccent);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setRefreshListener(this);
        mAdapter = new NewVideoAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshing(true);
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
                        fillData(cateBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private void fillData(CateBean dramaBean) {
        mSortedCateList.clear();
        mLastUpdate.clear();
        mRecommended.clear();
        mHot.clear();
        mAdapter.clear();

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

        CateSortedBean bean;
        CateSortedBean lastUpdateTitle = new CateSortedBean(Constant.TYPE_TITLE, null, "Latest " +
                "Update", true);
        mSortedCateList.add(lastUpdateTitle);
        for (RecBean.HotVideoItem item : mLastUpdate) {
            bean = new CateSortedBean(Constant.TYPE_NORMAL_VIDEO, item, "", false);
            mSortedCateList.add(bean);
        }

        CateSortedBean recommendTitle = new CateSortedBean(Constant.TYPE_TITLE, null,
                "Recommended", true);
        mSortedCateList.add(recommendTitle);
        for (RecBean.HotVideoItem item : mRecommended) {
            bean = new CateSortedBean(Constant.TYPE_NORMAL_VIDEO, item, "", false);
            mSortedCateList.add(bean);
        }

        CateSortedBean hotTitle = new CateSortedBean(Constant.TYPE_TITLE, null,
                "Hots", true);
        mSortedCateList.add(hotTitle);
        for (RecBean.HotVideoItem item : mHot) {
            bean = new CateSortedBean(Constant.TYPE_NORMAL_VIDEO, item, "", false);
            mSortedCateList.add(bean);
        }

        mAdapter.addAll(mSortedCateList);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    private ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                startActivity(VideoDetailActivity.newIntent(getContext(), video));
            }
        };
    }

    @Override
    public void onRefresh() {
        initData();
    }

}
