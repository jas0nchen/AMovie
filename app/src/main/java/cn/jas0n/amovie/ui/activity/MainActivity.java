package cn.jas0n.amovie.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.AMovie;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.HomePagerAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.ConstantCategory;
import cn.jas0n.amovie.realm.Account;
import cn.jas0n.amovie.ui.fragment.HomeFragment;
import cn.jas0n.amovie.ui.fragment.LazyFragment;
import cn.jas0n.amovie.ui.fragment.RecFragment;
import cn.jas0n.amovie.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
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
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.nav_view)
    NavigationView mNav;

    private Fragment mCurrentFragment;

    private CircleImageView mAvatar;
    private TextView mUserName;
    private TextView mIntro;

    private Account mAccount;
    private Realm mRealm;

    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View headerView = mNav.getHeaderView(0);
        mAvatar = (CircleImageView) headerView.findViewById(R.id.avatar);
        mUserName = (TextView) headerView.findViewById(R.id.username);
        mIntro = (TextView) headerView.findViewById(R.id.intro);

        mRealm = Realm.getDefaultInstance();
        mTransaction = getSupportFragmentManager().beginTransaction();
        mCurrentFragment = new HomeFragment();
        mTransaction.add(R.id.container, mCurrentFragment, HomeFragment.class.getName());
        mTransaction.commitAllowingStateLoss();

        setupDrawer();
    }

    private void syncAccountInfo() {
        mAccount = AMovie.getInstance().getAccount();

        if (mAccount == null)
            return;

        if (!TextUtils.equals("http://img.rrmj.tv/", mAccount.getHeadImgUrl())) {
            Glide.with(this).load(mAccount.getHeadImgUrl())
                    .centerCrop().into(mAvatar);
        }
        mUserName.setText(mAccount.getNickName());
        if (!TextUtils.isEmpty(mAccount.getSign()))
            mIntro.setText(mAccount.getSign());
    }

    private void setupDrawer() {
        mNav.setNavigationItemSelectedListener(this);
        mNav.setCheckedItem(R.id.nav_home);
        mAvatar.setImageResource(R.mipmap.icon_user_big);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        mTransaction = getSupportFragmentManager().beginTransaction();
        Fragment newFragment = null;
        String tag = null;
        if (id == R.id.nav_home) {
            tag = HomeFragment.class.getName();
            newFragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (newFragment == null) {
                newFragment = new HomeFragment();
            }
        } else if (id == R.id.nav_sub) {
            tag = RecFragment.class.getName();
            newFragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (newFragment == null)
                newFragment = RecFragment.newInstanse("Rec");
        } else if (id == R.id.nav_collect) {

        } else if (id == R.id.nav_download) {

        } else if (id == R.id.nav_share) {

        }
        if(!newFragment.isAdded()) {
            mTransaction.add(R.id.container, newFragment, tag).hide(mCurrentFragment).commitAllowingStateLoss();
        }else{
            mTransaction.show(newFragment).hide(mCurrentFragment).commitAllowingStateLoss();
        }
        mCurrentFragment = newFragment;

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncAccountInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawer;
    }
}
