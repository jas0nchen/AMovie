package cn.jas0n.amovie.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.view.SlidePannel;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Description 播放view
 */
public class VideoPlayView extends RelativeLayout implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {


    private CustomMediaContoller mediaController;
    private View player_btn, view, slidePannelView;
    private IjkVideoView mVideoView;
    private Handler handler = new Handler();
    private boolean isPause;

    private View rView;
    private Context mContext;
    private boolean portrait;

    private SlidePannel mSlidePannel;
    private GestureDetector gestureDetector;
    private MyGestureListener gestureListener;
    /**
     * 最大声音
     */
    private int mMaxVolume;
    /**
     * 当前声音
     */
    private int mVolume = -1;
    /**
     * 当前亮度
     */
    private float mBrightness = -1f;
    AudioManager audioManager;
//    private OrientationEventListener orientationEventListener;

    public VideoPlayView(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    private void initViews() {
        rView = LayoutInflater.from(mContext).inflate(R.layout.view_video_item, this, true);
        view = findViewById(R.id.media_contoller);
        slidePannelView = findViewById(R.id.slide_pannel);
        mVideoView = (IjkVideoView) findViewById(R.id.main_video);
        mediaController = new CustomMediaContoller(mContext, rView);
        mSlidePannel = new SlidePannel(mContext, slidePannelView);
        mVideoView.setMediaController(mediaController);

        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                view.setVisibility(View.GONE);
                mediaController.pause();
                if (mediaController.getScreenOrientation((Activity) mContext)
                        == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    //横屏播放完毕，重置
                    ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    setLayoutParams(layoutParams);
                }
                if (completionListener != null)
                    completionListener.completion(mp);
            }
        });
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        gestureListener = new MyGestureListener(rView);
        gestureDetector = new GestureDetector(mContext, gestureListener);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    }

    public boolean isPlay() {
        return mVideoView.isPlaying();
    }

    public void pause() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    public void start(String path) {
        Uri uri = Uri.parse(path);
        if (mediaController != null)
            mediaController.start();
        if (!mVideoView.isPlaying()) {
            mVideoView.setVideoURI(uri);
            mVideoView.start();
        } else {
            mVideoView.stopPlayback();
            mVideoView.setVideoURI(uri);
            mVideoView.start();
        }
    }

    public void start() {
        if (mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }

    public void setContorllerVisiable() {
        mediaController.setVisiable();
    }

    public void setFullAction() {
        mediaController.setFullAction();
    }

    public void setPortraitAction() {
        mediaController.setPortraitAction();
    }

    public void seekTo(int msec) {
        mVideoView.seekTo(msec);
    }

    public void onChanged(Configuration configuration) {
        portrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT;
        doOnConfigurationChanged(portrait);
    }

    public void doOnConfigurationChanged(final boolean portrait) {
        if (mVideoView != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setFullScreen(!portrait);
                    if (portrait) {
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        Log.e("handler", "400");
                        setLayoutParams(layoutParams);
                        requestLayout();
                    } else {
                        int heightPixels = ((Activity) mContext).getResources().getDisplayMetrics().heightPixels;
                        int widthPixels = ((Activity) mContext).getResources().getDisplayMetrics().widthPixels;
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.height = heightPixels;
                        layoutParams.width = widthPixels;
                        Log.e("handler", "height==" + heightPixels + "\nwidth==" + widthPixels);
                        setLayoutParams(layoutParams);
                    }
                }
            });
//            orientationEventListener.enable();
        }
    }

    public void stop() {
        if (mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
        }
    }

    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
//        orientationEventListener.disable();
    }

    private void setFullScreen(boolean fullScreen) {
        if (mContext != null && mContext instanceof Activity) {
            WindowManager.LayoutParams attrs = ((Activity) mContext).getWindow().getAttributes();
            if (fullScreen) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                ((Activity) mContext).getWindow().setAttributes(attrs);
                ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ((Activity) mContext).getWindow().setAttributes(attrs);
                ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }

    }

    public void setShowContoller(boolean isShowContoller) {
        mediaController.setShowContoller(isShowContoller);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (gestureDetector.onTouchEvent(motionEvent))
            return true;

        // 处理手势结束
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }

        return true;
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public long getPalyPostion() {
        return mVideoView.getCurrentPosition();
    }

    public void release() {
        mVideoView.release(true);
    }

    public int VideoStatus() {
        return mVideoView.getmCurrentState();
    }

    public void setMediaControllerStop() {
        mediaController.pause();
    }

    private CompletionListener completionListener;

    public void setCompletionListener(CompletionListener completionListener) {
        this.completionListener = completionListener;
    }

    public interface CompletionListener {
        void completion(IMediaPlayer mp);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private View item;

        public MyGestureListener(View item) {
            this.item = item;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mediaController.isShowing())
                mediaController.hide();
            else
                mediaController.show();
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mVideoView.isPlaying()) {
                mediaController.pause();
            }else {
                mediaController.start();
            }
            return true;
        }

        /**
         * 滑动
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (mediaController.isShowing())
                mediaController.hide();

            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            int x = (int) e2.getRawX();
            int deltaX = Math.abs((int) (x - mOldX));
            int deltaY = Math.abs((int) (y - mOldY));

            int windowWidth = item.getWidth();
            int windowHeight = item.getHeight();

            if (deltaX > 40 && deltaY <= 40) {
                onProgressSlide((x - mOldX) / windowWidth);
            } else if (deltaX <= 40 && deltaY > 40) {
                if (mOldX > windowWidth * 3.0 / 5.0)// 右边滑动
                    onVolumeSlide((mOldY - y) / windowHeight);
                else if (mOldX < windowWidth * 2.0 / 5.0)// 左边滑动
                    onBrightnessSlide((mOldY - y) / windowHeight);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    /**
     * 滑动改变进度条
     *
     * @param percent
     */
    private void onProgressSlide(float percent) {
        int position = mVideoView.getCurrentPosition();
        int duration = mVideoView.getDuration();
        int newPosition = (int) (position + percent * duration / 10);

        if (newPosition > duration)
            newPosition = duration;

        if (newPosition < 0)
            newPosition = 0;

        mSlidePannel.showSlideProgress(position, percent, duration);
        mVideoView.seekTo(newPosition);
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        mSlidePannel.showSlideVolume(index * 100 / mMaxVolume);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = ((Activity) mContext).getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
        }
        WindowManager.LayoutParams lpa = ((Activity) mContext).getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        ((Activity) mContext).getWindow().setAttributes(lpa);

        mSlidePannel.showSlideBrightness(lpa.screenBrightness);
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSlidePannel.hide();
        }
    };

    public void setQualities(String currentQuality, String[] qualities) {
        mediaController.setQualities(currentQuality, qualities);
    }
}
