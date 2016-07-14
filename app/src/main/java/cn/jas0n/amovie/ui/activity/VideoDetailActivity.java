package cn.jas0n.amovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jaouan.revealator.Revealator;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.VideoDetailPagerAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.M3U8ById;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.VideoDetail;
import cn.jas0n.amovie.ui.fragment.CommentListFragment;
import cn.jas0n.amovie.ui.fragment.DescFragment;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.ui.view.FixedViewPager;
import cn.jas0n.amovie.ui.view.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import cn.jas0n.amovie.util.Utils;
import cn.jas0n.amovie.util.ViewGroupUtils;
import fm.jiecao.jcvideoplayer_lib.JCBuriedPointStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class VideoDetailActivity extends SwipeBackActivity implements View.OnClickListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_image)
    ImageView mDetailImage;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tab_layout)
    RelativeLayout mTabLayout;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    FixedViewPager mPager;
    @BindView(R.id.fake_tab)
    TabLayout mFakeTab;
    @BindView(R.id.fake_tab_layout)
    RelativeLayout mFakeTabLayout;
    @BindView(R.id.video)
    JCVideoPlayerStandardShowShareButtonAfterFullscreen mJCVideo;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    private List<Fragment> mFragments = new ArrayList<>();
    private VideoDetailPagerAdapter mAdapter;
    private RecBean.HotVideoItem mVideo;
    private VideoDetail mDetail;
    private boolean isM3U8Ready = false;
    private String[] mQuality;
    private String mCurrentQuality;
    private M3U8ById m3U8Info;
    private boolean isVideoPlayBeforeActivityPause;

    public static Intent newIntent(Context context, RecBean.HotVideoItem video) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("video", video);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);

        setupToolbar();
        setupFab();
        setupScrollView();
        initVideoInfo();
    }

    private void setupScrollView() {
        final Rect rect = new Rect();
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int deltaY = oldScrollY - scrollY;
                if (deltaY <= 0) {
                    ViewGroupUtils.getDescendantRect(mCoordinator, mAppBar, rect);
                    if (rect.bottom <= ViewCompat.getMinimumHeight(mToolbar) + Utils
                            .getStatusBarHeight(VideoDetailActivity.this)) {
                        mFakeTabLayout.setVisibility(View.VISIBLE);
                    } else {
                        mFakeTabLayout.setVisibility(View.GONE);
                    }
                } else {
                    if (scrollY == 0)
                        mFakeTabLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupAdapter() {
        mFragments.add(DescFragment.newInstance(mDetail));
        mFragments.add(CommentListFragment.newInstance(String.valueOf(mVideo.getId()), CommentListFragment.VIDEO));
        mAdapter = new VideoDetailPagerAdapter(getSupportFragmentManager(), new String[]
                {getString(R.string.description), String.format(getString(R.string.comments),
                        mDetail.getData().getUserVideoView().getCommentCount())}, mFragments);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
        mFakeTab.setupWithViewPager(mPager);

        ViewCompat.setElevation(mFakeTabLayout, 3f);
        ViewCompat.setElevation(mTabLayout, 3f);
    }

    private void initVideoInfo() {
        mVideo = (RecBean.HotVideoItem) getIntent().getSerializableExtra("video");

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        Glide.with(this).load(mVideo.getUrl()).centerCrop().crossFade().into(mDetailImage);
        fetchM3U8ById();
        fetchVideoDetail();
    }

    private void fetchVideoDetail() {
        AMovieService.builder().getApiService().getVideoDetail(String.valueOf(mVideo.getId()), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoDetail>() {
                    @Override
                    public void call(VideoDetail videoDetail) {
                        Logger.d(videoDetail.toString());
                        mDetail = videoDetail;
                        setupAdapter();
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void fetchM3U8ById() {
        AMovieService.builder().getApiService().getM3U8ByVideoId(String.valueOf(mVideo.getId()),
                "high").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<M3U8ById>() {
                    @Override
                    public void call(M3U8ById m3U8ById) {
                        isM3U8Ready = true;
                        m3U8Info = m3U8ById;
                        mCurrentQuality = m3U8ById.getData().getM3u8().getCurrentQuality();
                        mQuality = m3U8ById.getData().getM3u8().getQualityArr();
                        ColorStateList colorStateList = ContextCompat.getColorStateList
                                (VideoDetailActivity.this, R.color.selector_fab);
                        mFab.setBackgroundTintList(colorStateList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private void setupFab() {
        mFab.setOnClickListener(this);
    }

    private void doClickFab() {
        if (isM3U8Ready) {
            Revealator.reveal(mJCVideo)
                    .from(mFab)
                    .withChildsAnimation()
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            CoordinatorLayout.LayoutParams params =
                                    (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                            params.setBehavior(null);
                            mPager.setDisableAppbar(true);
                            mPager.requestLayout();
                            initVideoPlayer();
                        }
                    })
                    .start();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                mFab.hide();
        }
    }

    private void initVideoPlayer() {
        mJCVideo.setUp(m3U8Info.getData().getM3u8().getUrl(), mVideo.getTitle());
        Glide.with(this).load(mVideo.getUrl()).centerCrop().crossFade().into(mJCVideo
                .thumbImageView);
        JCVideoPlayerStandardShowShareButtonAfterFullscreen.setJcBuriedPointStandard(jcBuriedPointStandard);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.mipmap.ic_arrow_left_white_24dp);
        mToolbar.setNavigationIcon(upArrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static JCBuriedPointStandard jcBuriedPointStandard = new JCBuriedPointStandard() {
        @Override
        public void onClickStartThumb(String url, Object... objects) {
            Log.i("Buried_Point", "onClickStartThumb" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickBlank(String url, Object... objects) {
            Log.i("Buried_Point", "onClickBlank" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickBlankFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onClickBlankFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickStartIcon(String url, Object... objects) {
            Log.i("Buried_Point", "onClickStartIcon" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickStartError(String url, Object... objects) {
            Log.i("Buried_Point", "onClickStartError" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickStop(String url, Object... objects) {
            Log.i("Buried_Point", "onClickStop" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickStopFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onClickStopFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickResume(String url, Object... objects) {
            Log.i("Buried_Point", "onClickResume" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickResumeFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onClickResumeFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickSeekbar(String url, Object... objects) {
            Log.i("Buried_Point", "onClickSeekbar" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onClickSeekbarFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onClickSeekbarFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onAutoComplete(String url, Object... objects) {
            Log.i("Buried_Point", "onAutoComplete" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onAutoCompleteFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onAutoCompleteFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onEnterFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onEnterFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onQuitFullscreen(String url, Object... objects) {
            Log.i("Buried_Point", "onQuitFullscreen" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onTouchScreenSeekVolume(String url, Object... objects) {
            Log.i("Buried_Point", "onTouchScreenSeekVolume" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }

        @Override
        public void onTouchScreenSeekPosition(String url, Object... objects) {
            Log.i("Buried_Point", "onTouchScreenSeekVolume" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " mUrl is : " + url);
        }
    };

    @Override
    public void onClick(View view) {
        if(view == mFab){
            doClickFab();
        }
    }
}
