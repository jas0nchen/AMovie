package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
import android.content.res.Configuration;
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

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.DramaGridAdapter;
import cn.jas0n.amovie.adapter.HotVideoGridAdapter;
import cn.jas0n.amovie.adapter.RecAdapter;
import cn.jas0n.amovie.adapter.VideoGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.activity.SeasonDetailActivity;
import cn.jas0n.amovie.ui.activity.VideoDetailActivity;
import cn.jas0n.amovie.ui.view.CustomVideoItemLayout;
import cn.jas0n.amovie.ui.view.FixedGridView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    protected View mView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.hot_more)
    TextView mHotMore;
    @BindView(R.id.hot_grid)
    FixedGridView mHotGrid;
    @BindView(R.id.drama_more)
    TextView mDramaMore;
    @BindView(R.id.drama_grid)
    FixedGridView mDramaGrid;
    @BindView(R.id.original_more)
    TextView mOriginalMore;
    @BindView(R.id.original_first)
    CustomVideoItemLayout mOriginalFirst;
    @BindView(R.id.original_grid)
    FixedGridView mOriginalGrid;
    @BindView(R.id.entertain_more)
    TextView mEntertainMore;
    @BindView(R.id.entertain_first)
    CustomVideoItemLayout mEntertainFirst;
    @BindView(R.id.entertain_grid)
    FixedGridView mEntertainGrid;
    @BindView(R.id.movie_more)
    TextView mMovieMore;
    @BindView(R.id.movie_grid)
    FixedGridView mMovieGrid;
    @BindView(R.id.open_more)
    TextView mOpenMore;
    @BindView(R.id.open_first)
    CustomVideoItemLayout mOpenFirst;
    @BindView(R.id.open_grid)
    FixedGridView mOpenGrid;
    @BindView(R.id.music_more)
    TextView mMusicMore;
    @BindView(R.id.music_first)
    CustomVideoItemLayout mMusicFirst;
    @BindView(R.id.music_grid)
    FixedGridView mMusicGrid;
    @BindView(R.id.tech_more)
    TextView mTechMore;
    @BindView(R.id.tech_first)
    CustomVideoItemLayout mTechFirst;
    @BindView(R.id.tech_grid)
    FixedGridView mTechGrid;
    @BindView(R.id.live_more)
    TextView mLiveMore;
    @BindView(R.id.live_first)
    CustomVideoItemLayout mLiveFirst;
    @BindView(R.id.live_grid)
    FixedGridView mLiveGrid;
    @BindView(R.id.sport_more)
    TextView mSportMore;
    @BindView(R.id.sport_first)
    CustomVideoItemLayout mSportFirst;
    @BindView(R.id.sport_grid)
    FixedGridView mSportGrid;
    @BindView(R.id.document_more)
    TextView mDocumentMore;
    @BindView(R.id.document_first)
    CustomVideoItemLayout mDocumentFirst;
    @BindView(R.id.document_grid)
    FixedGridView mDocumentGrid;
    @BindView(R.id.error)
    TextView mError;
    @BindView(R.id.empty)
    TextView mEmpty;

    private HotVideoGridAdapter mHotAdapter;
    private DramaGridAdapter mDramaAdapter;
    private VideoGridAdapter mOriAdapter;
    private VideoGridAdapter mEntertainAdapter;
    private HotVideoGridAdapter mMovieAdapter;
    private VideoGridAdapter mOpenAdapter;
    private VideoGridAdapter mMusicAdapter;
    private VideoGridAdapter mTechAdapter;
    private VideoGridAdapter mLiveAdapter;
    private VideoGridAdapter mSportAdapter;
    private VideoGridAdapter mDocumentAdapter;

    private List<RecBean.HotVideoItem> mHotList = new ArrayList<>();
    private List<RecBean.RecDramaItem> mRecList = new ArrayList<>();
    private List<RecBean.Video> mVideoList = new ArrayList<>();
    private Map<String, RecBean.Video> mVideoMap = new HashMap<>();

    public static RecFragment newInstanse(String title) {
        RecFragment fragment = new RecFragment();
        fragment.setDefaultFragmentTitle(title);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_rec, container, false);
        }
        ButterKnife.bind(this, mView);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRefreshLayout.setOnRefreshListener(this);
        showProgress();
        return mView;
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getRecVideos("3.0.3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RecBean>() {
                    @Override
                    public void call(RecBean recBean) {
                        fillData(recBean);
                        setupViews();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        showError();
                    }
                });
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    private void fillData(RecBean recBean) {
        mVideoList.clear();
        mHotList.clear();
        mRecList.clear();
        mVideoList = recBean.getData().getVideo();
        mHotList = recBean.getData().getHotVideo();
        mRecList = recBean.getData().getRecDrama();

        for (RecBean.Video video : mVideoList) {
            mVideoMap.put(video.getTitle(), video);
        }
    }

    private void setupViews() {
        if (mHotList.size() >= 4) {
            mHotAdapter = new HotVideoGridAdapter(mHotList.subList(0, 4), getContext());
        } else {
            mHotAdapter = new HotVideoGridAdapter(mHotList, getContext());
        }
        if (mRecList.size() >= 6) {
            mDramaAdapter = new DramaGridAdapter(mRecList.subList(0, 6), getContext());
        } else {
            mDramaAdapter = new DramaGridAdapter(mRecList, getContext());
        }

        mHotGrid.setAdapter(mHotAdapter);
        mDramaGrid.setAdapter(mDramaAdapter);
        mHotAdapter.setClickVideo(getClickVideo());
        mDramaAdapter.setClickSeason(getClickSeason());

        fillMovie();
        fillVideo("原创", mOriAdapter, mOriginalFirst, mOriginalGrid);
        fillVideo("娱乐", mEntertainAdapter, mEntertainFirst, mEntertainGrid);
        fillVideo("公开课", mOpenAdapter, mOpenFirst, mOpenGrid);
        fillVideo("音乐", mMusicAdapter, mMusicFirst, mMusicGrid);
        fillVideo("科技", mTechAdapter, mTechFirst, mTechGrid);
        fillVideo("生活", mLiveAdapter, mLiveFirst, mLiveGrid);
        fillVideo("体育", mSportAdapter, mSportFirst, mSportGrid);
        fillVideo("纪录片", mDocumentAdapter, mDocumentFirst, mDocumentGrid);

        mHotMore.setOnClickListener(this);
        mDramaMore.setOnClickListener(this);
        mOriginalMore.setOnClickListener(this);
        mEntertainMore.setOnClickListener(this);
        mMovieMore.setOnClickListener(this);
        mOpenMore.setOnClickListener(this);
        mMusicMore.setOnClickListener(this);
        mTechMore.setOnClickListener(this);
        mLiveMore.setOnClickListener(this);
        mSportMore.setOnClickListener(this);
        mDocumentMore.setOnClickListener(this);

        showContent();
    }

    private void fillVideo(String cat, VideoGridAdapter adapter, CustomVideoItemLayout firstItem,
                           FixedGridView gridView) {
        List<RecBean.HotVideoItem> videoItems = mVideoMap.get(cat).getVideos();
        if (firstItem != null) {
            firstItem.setClickVideo(getClickVideo());
            firstItem.setVideo(videoItems.get(0));
            videoItems.remove(0);
        }

        if (firstItem != null && videoItems.size() >= 4) {
            adapter = new VideoGridAdapter(videoItems.subList(0, 4), getContext());
        } else {
            adapter = new VideoGridAdapter(videoItems, getContext());
        }

        adapter.setClickVideo(getClickVideo());
        gridView.setAdapter(adapter);
    }

    private void fillMovie() {
        List<RecBean.HotVideoItem> videoItems = mVideoMap.get("电影").getVideos();
        mMovieAdapter = new HotVideoGridAdapter(videoItems, getContext());
        mMovieAdapter.setClickVideo(getClickVideo());
        mMovieGrid.setAdapter(mMovieAdapter);
    }

    private ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video));
            }
        };
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void showError() {
        hideAll();
        mError.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        hideAll();
        mEmpty.setVisibility(View.VISIBLE);
    }

    private void showProgress(){
        hideAll();
        mRefreshLayout.setProgressViewOffset(false, 0, 150);
        mRefreshLayout.setRefreshing(true);
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
    public void onRefresh() {
        initData();
    }

    @Override
    public void onClick(View view) {
        if (view == mHotMore) {

        } else if (view == mDramaMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(1);
        } else if (view == mOriginalMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(2);
        } else if (view == mEntertainMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(3);
        } else if (view == mMovieMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(4);
        } else if (view == mOpenMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(5);
        } else if (view == mMusicMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(6);
        } else if (view == mTechMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(7);
        } else if (view == mLiveMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(8);
        } else if (view == mSportMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(9);
        } else if (view == mDocumentMore) {
            ((HomeFragment)getParentFragment()).setTabIndex(10);
        }
    }
}
