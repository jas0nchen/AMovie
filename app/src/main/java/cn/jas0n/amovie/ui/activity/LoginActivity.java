package cn.jas0n.amovie.ui.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.AMovie;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.api.AMovieApi;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Login;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.util.MD5Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {

    @BindView(R.id.name)
    TextInputEditText mName;
    @BindView(R.id.password)
    TextInputEditText mPassword;
    @BindView(R.id.login)
    TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLogin.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if(view == mLogin){
            doLogin();
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
                        Logger.d(login.toString());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }
}
