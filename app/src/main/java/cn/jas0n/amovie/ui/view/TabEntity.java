package cn.jas0n.amovie.ui.view;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Author: Jas0n
 * Date: 2016/7/5
 * E-mail:chendong90x@gmail.com
 */
public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
