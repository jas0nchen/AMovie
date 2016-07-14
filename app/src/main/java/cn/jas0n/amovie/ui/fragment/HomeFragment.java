package cn.jas0n.amovie.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.HomePagerAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.ui.activity.LoginActivity;
import cn.jas0n.amovie.ui.activity.MainActivity;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/7/6
 * E-mail:chendong90x@gmail.com
 */
public class HomeFragment extends LazyFragment {
    private String[] mTitle = new String[11];

    private View mView;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mPager;

    private HomePagerAdapter mAdapter;
    private List<LazyFragment> mFragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.app_bar_main, container, false);
        }
        ButterKnife.bind(this, mView);

        setupToolbar();
        setupFab();
        setupTab();
        return mView;
    }

    private void setupToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.home));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), ((MainActivity) getActivity()).getDrawerLayout(), mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ((MainActivity) getActivity()).getDrawerLayout().addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupTab() {
        mTitle = new String[]{getString(R.string.recommend), getString(R.string.drama),
                getString(R.string.original), getString(R.string.entertainment), getString(R.string.movie), getString(R.string.open_class), getString(R.string.music),
                getString(R.string.tech), getString(R.string.livelihood), getString(R.string.sport), getString(R.string.documentary)};
        mFragments.add(RecFragment.newInstanse(mTitle[0]));
        mFragments.add(new DramaFragment());
        for (int i = 2; i < 6; i++) {
            mFragments.add(CategoryFragment.newInstance(i, mTitle[i]));
        }
        for (int i = 6; i < mTitle.length; i++) {
            mFragments.add(CategoryFragment.newInstance(i+1, mTitle[i]));
        }
        mAdapter = new HomePagerAdapter(getChildFragmentManager(), mTitle, mFragments);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(Utils.dip2px(getContext(), 8f));
        mTab.setupWithViewPager(mPager);

        AMovieService.builder().getApiService().getConstantCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConstantCategory>() {
                    @Override
                    public void call(ConstantCategory constantCategory) {
                        constantCategory.getData().getCategory().size();
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

    @Override
    protected void initData() {

    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTabIndex(int index){
        mPager.setCurrentItem(index);
    }
}
