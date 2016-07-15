package cn.jas0n.amovie.ui.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.AMovie;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.api.AMovieApi;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Login;
import cn.jas0n.amovie.realm.Account;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.util.MD5Utils;
import cn.jas0n.amovie.util.Utils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.name)
    TextInputEditText mName;
    @BindView(R.id.password)
    TextInputEditText mPassword;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.api_provider)
    TextView mApiProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLogin.setOnClickListener(this);
        mApiProvider.setAutoLinkMask(Linkify.ALL);
        mApiProvider.setMovementMethod(LinkMovementMethod.getInstance());

        initBack();
    }

    private void initBack() {
        Utils.setTint(this, mBack, R.color.colorAccent, R.mipmap.ic_arrow_left_white_24dp);
        mBack.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view == mLogin) {
            doLogin();
        }else if(view == mBack){
            finish();
        }
    }

    private void doLogin() {
        AMovieService.builder().getApiService().loginByMobile(mName.getText().toString().trim(),
                MD5Utils.MD5(mPassword.getText().toString().trim()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Login>() {
                    @Override
                    public void call(Login login) {
                        saveAccountToRealm(login);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    private void saveAccountToRealm(Login login) {
        Login.User user = login.getData().getUser();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(Account.class);
        realm.commitTransaction();
        realm.beginTransaction();
        Account account = realm.createObject(Account.class);
        account.setSilverCount(user.getSilverCount());
        account.setHeadImgUrl(user.getHeadImgUrl());
        account.setConfirmed(user.isConfirmed());
        account.setNickName(user.getNickName());
        account.setScore(user.getScore());
        account.setReplyCount(user.getReplyCount());
        account.setMobile(user.getMobile());
        account.setSex(user.getSex());
        account.setBirthday(user.getBirthday());
        account.setCity(user.getCity());
        account.setFansCount(user.getFansCount());
        account.setFocusUserCount(user.getFocusUserCount());
        account.setRoleInfo(user.getRoleInfo());
        account.setConfirmInfo(user.getConfirmInfo());
        account.setArticleCount(user.getArticleCount());
        account.setLoginFrom(user.getLoginFrom());
        account.setFavoriteCount(user.getFavoriteCount());
        account.setSeriesCount(user.getSeriesCount());
        account.setActorCount(user.getActorCount());
        account.setLevelStr(user.getLevelStr());
        account.setLevel(user.getLevel());
        account.setWmSign(user.getWmSign());
        account.setSign(user.getSign());
        account.setToken(user.getToken());
        account.setId(user.getId());
        realm.commitTransaction();
    }
}
