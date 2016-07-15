package cn.jas0n.amovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.VideoDetailPagerAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.SeasonDetail;
import cn.jas0n.amovie.ui.fragment.CommentListFragment;
import cn.jas0n.amovie.ui.fragment.SeasonDescFragment;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.ui.view.FixedViewPager;
import cn.jas0n.amovie.ui.view.SeasonTabLayout;
import cn.jas0n.amovie.ui.view.TabEntity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class SeasonDetailActivity extends SwipeBackActivity implements OnTabSelectListener {

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
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.season_tab)
    SeasonTabLayout mSeasonTab;
    @BindView(R.id.tab_layout)
    RelativeLayout mTabLayout;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    FixedViewPager mPager;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    private RecBean.RecDramaItem mDrama;
    private SeasonDetail mDetail;
    private int mAppScrollRange;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String mCurrentSeasonTitle;
    private List<Fragment> mFragments = new ArrayList<>();
    private SeasonDescFragment mSeasonDescFragment;
    private CommentListFragment mCommentListFragment;
    private VideoDetailPagerAdapter mAdapter;

    public static Intent newIntent(Context context, RecBean.RecDramaItem dramaItem) {
        Intent intent = new Intent(context, SeasonDetailActivity.class);
        intent.putExtra("drama", dramaItem);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);
        ButterKnife.bind(this);
        initData();
        setupToolbar();
        setupFab();
        fetchSeasonDetail();
    }

    private void fetchSeasonDetail() {
        AMovieService.builder().getApiService().getSeasonDetail(String.valueOf(mDrama.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SeasonDetail>() {
                    @Override
                    public void call(SeasonDetail seasonDetail) {
                        mDetail = seasonDetail;
                        fillData();
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

    private void fillData() {
        int recommendCount = mDetail.getData().getSeason().getRecommend().size();
        String[] titles = new String[recommendCount];
        int[] selecetedIcons = new int[recommendCount];
        int[] unSelectedIcons = new int[recommendCount];
        int currentTabIndex = 0;
        for (int i = 0; i < recommendCount; i++) {
            if (mDetail.getData().getSeason().getId() == mDetail.getData().getSeason()
                    .getRecommend().get(i).getId())
                currentTabIndex = i;
            titles[i] = String.format(getString(R.string.season_num), i + 1);
        }
        for (int i = 0; i < recommendCount; i++) {
            mTabEntities.add(new TabEntity(titles[i], selecetedIcons[i], unSelectedIcons[i]));
        }
        mSeasonTab.setTabData(mTabEntities);
        mSeasonTab.setCurrentTab(currentTabIndex);
        mSeasonTab.setIndicatorColor(ContextCompat.getColor(this, R.color.black_60));
        mSeasonTab.setOnTabSelectListener(this);
    }

    private void initData() {
        mDrama = (RecBean.RecDramaItem) getIntent().getSerializableExtra("drama");
    }

    private void setupFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupAdapter() {
        mFragments.clear();
        mSeasonDescFragment = SeasonDescFragment.newInstance(mDetail);
        mCommentListFragment = CommentListFragment.newInstance(mDetail.getData().getSeason().getSid(),
                CommentListFragment.SEASON);
        mFragments.add(mSeasonDescFragment);
        mFragments.add(mCommentListFragment);
        mAdapter = new VideoDetailPagerAdapter(getSupportFragmentManager(), new String[]
                {getString(R.string.episodes), getString(R.string.comment)}, mFragments);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);

        ViewCompat.setElevation(mTabLayout, 3f);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        Glide.with(getApplicationContext()).load(mDrama.getHorizontalUrl()).centerCrop().crossFade().into
                (mDetailImage);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.mipmap.ic_arrow_left_white_24dp);
        mToolbar.setNavigationIcon(upArrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = Math.abs((float) verticalOffset / (float) mAppBar.getTotalScrollRange());
                mSeasonTab.setAlpha(1 - alpha);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onTabSelect(final int position) {
        int seasonId = mDetail.getData().getSeason().getRecommend().get(position).getId();
        mProgressBar.setVisibility(View.VISIBLE);
        AMovieService.builder().getApiService().getSeasonDetail(String.valueOf(seasonId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SeasonDetail>() {
                    @Override
                    public void call(SeasonDetail seasonDetail) {
                        Glide.with(getApplicationContext()).load(mDetail.getData().getSeason()
                                .getRecommend().get(position).getHorizontalUrl()).centerCrop().crossFade().into(mDetailImage);

                        mDetail = seasonDetail;
                        mSeasonDescFragment.setData(mDetail);
                        mCommentListFragment.setData(mDetail.getData().getSeason().getSid(), CommentListFragment.SEASON);
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

    @Override
    public void onTabReselect(int position) {

    }
}
