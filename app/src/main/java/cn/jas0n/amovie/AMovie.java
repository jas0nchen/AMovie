package cn.jas0n.amovie;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import cn.jas0n.amovie.realm.Account;
import cn.jas0n.amovie.util.ThemeHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class AMovie extends Application implements ThemeUtils.switchColor {

    private static AMovie mInstance;
    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mInstance == null)
            mInstance = this;

        LeakCanary.install(this);
        Logger.init();
        ThemeUtils.setSwitchColor(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
        RealmConfiguration configuration = new RealmConfiguration.Builder
                (this).name("amovie.realm").schemaVersion(1).build();
        Realm.setDefaultConfiguration(configuration);
        mRealm = Realm.getDefaultInstance();
    }

    public static AMovie getInstance() {
        return mInstance;
    }

    public boolean isLogin() {
        RealmResults<Account> accounts = mRealm.where(Account.class).findAll();
        if (accounts.size() > 0)
            return true;
        else
            return false;
    }

    public Account getAccount() {
        Account account = null;
        if (isLogin())
            account = mRealm.where(Account.class).findFirst();
        return account;
    }

    public String getToken() {
        if (getAccount() != null)
            return getAccount().getToken();
        else
            return "";
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = getTheme(context);
        int colorId = -1;

        if (theme != null) {
            colorId = getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        }
        return null;
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xfffb7299:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case 0xffb85671:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case 0x99f0486c:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return -1;
    }
}
