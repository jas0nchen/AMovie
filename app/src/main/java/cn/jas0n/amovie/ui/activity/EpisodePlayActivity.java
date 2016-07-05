package cn.jas0n.amovie.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.Episode;
import cn.jas0n.amovie.videoplayer.VideoPlayView;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class EpisodePlayActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private VideoPlayView mVideoPlayView;
    @BindView(R.id.video_layout)
    FrameLayout mVideoLayout;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mVideoLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private Episode mEpisode;

    public static Intent newIntent(Context context, Episode episode) {
        Intent intent = new Intent(context, EpisodePlayActivity.class);
        intent.putExtra("episode", episode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_play);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mVisible = true;

        initData();
    }

    private void initData() {
        mEpisode = (Episode) getIntent().getSerializableExtra("episode");
        mVideoPlayView = new VideoPlayView(this);
        mVideoLayout.addView(mVideoPlayView);
        if (mEpisode.getData().getM3u8() != null)
            mVideoPlayView.start(mEpisode.getData().getM3u8().getUrl());
        mVideoPlayView.setKeepScreenOn(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mVideoLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
