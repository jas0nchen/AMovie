package cn.jas0n.amovie.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.HomePagerAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.ui.fragment.LazyFragment;
import cn.jas0n.amovie.ui.fragment.BaseFragment;
import cn.jas0n.amovie.ui.fragment.RecFragment;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String[] mTitle = new String[11];

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.nav_view)
    NavigationView mNav;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mPager;

    private ImageView mAvatar;
    private TextView mUserName;
    private TextView mIntro;

    private HomePagerAdapter mAdapter;
    private List<LazyFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        View headerView = mNav.getHeaderView(0);
        mAvatar = (ImageView) headerView.findViewById(R.id.avatar);
        mUserName = (TextView) headerView.findViewById(R.id.username);
        mIntro = (TextView) headerView.findViewById(R.id.intro);
        setupToolbar();
        setupDrawer();
        setupFab();
        setupTab();
    }

    private void setupTab() {
        mTitle = new String[]{getString(R.string.recommend), getString(R.string.drama),
                getString(R.string.documentary), getString(R.string.original), getString(R.string.entertainment),
                getString(R.string.movie), getString(R.string.open_class), getString(R.string.music),
                getString(R.string.tech), getString(R.string.livelihood), getString(R.string.sport)};
        for (int i = 0; i < mTitle.length; i++) {
            mFragments.add(RecFragment.newInstanse(mTitle[i]));
        }
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), mTitle, mFragments);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(Utils.dip2px(this, 8f));
        mTab.setupWithViewPager(mPager);

        AMovieService.builder().getApiService().getConstantCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConstantCategory>() {
                    @Override
                    public void call(ConstantCategory constantCategory) {
                        Logger.d(constantCategory.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private void setupFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNav.setNavigationItemSelectedListener(this);
        mAvatar.setImageResource(R.mipmap.icon_user_big);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, VideoDetailActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
