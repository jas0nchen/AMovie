package cn.jas0n.amovie.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import cn.jas0n.amovie.ui.fragment.LazyFragment;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class VideoDetailPagerAdapter extends FragmentStatePagerAdapter {

    private String [] mTitle;
    private List<Fragment> mFragments;

    public VideoDetailPagerAdapter(FragmentManager fm, String[]mTitle, List<Fragment> mFragments) {
        super(fm);
        this.mTitle = mTitle;
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
