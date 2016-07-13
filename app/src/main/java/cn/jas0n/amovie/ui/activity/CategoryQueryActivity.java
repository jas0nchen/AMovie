package cn.jas0n.amovie.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.ConstellationAdapter;
import cn.jas0n.amovie.adapter.DramaAdapter;
import cn.jas0n.amovie.adapter.HomePagerAdapter;
import cn.jas0n.amovie.adapter.ListDropDownAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.SeasonQueryBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.ui.fragment.CategoryFragment;
import cn.jas0n.amovie.ui.fragment.DramaFragment;
import cn.jas0n.amovie.ui.fragment.LazyFragment;
import cn.jas0n.amovie.ui.fragment.RecFragment;
import cn.jas0n.amovie.ui.fragment.VideoQueryFragment;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/7/12
 * E-mail:chendong90x@gmail.com
 */
public class CategoryQueryActivity extends SwipeBackActivity {

    private static final int ROWS = 21;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mPager;

    private String mTitle;
    private int mCateId;
    private int mCurrentIndex;
    private String mSorts[] = new String[]{"updateTime", "recommended", "heat"};
    private String mTitles[] = new String[3];

    private HomePagerAdapter mAdapter;
    private List<LazyFragment> mFragments = new ArrayList<>();

    public static Intent newIntent(Context context, int cateId, int index, String title){
        Intent intent = new Intent(context, CategoryQueryActivity.class);
        intent.putExtra("cateId", cateId);
        intent.putExtra("index", index);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_query);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        if (mToolbar != null)
            setSupportActionBar(mToolbar);

        mTitle = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCateId = getIntent().getIntExtra("cateId", 2);
        mCurrentIndex = getIntent().getIntExtra("index", 0);
        mTitles = new String[]{getString(R.string.last_update), getString(R.string.recommended),
                getString(R.string.hot)};
        for (int i = 0; i < mSorts.length; i++) {
            mFragments.add(VideoQueryFragment.newInstance(mCateId, mSorts[i]));
        }
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(Utils.dip2px(this, 8f));
        mTab.setupWithViewPager(mPager);
        mPager.setCurrentItem(mCurrentIndex);
    }
}
