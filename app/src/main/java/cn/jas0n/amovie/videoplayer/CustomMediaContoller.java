package cn.jas0n.amovie.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.orhanobut.logger.Logger;

import cn.jas0n.amovie.R;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @Description ${控制器}
 */
public class CustomMediaContoller implements IMediaController {
    private static final int SET_VIEW_HIDE = 1;
    private static final int TIME_OUT = 5000;
    private static final int MESSAGE_SHOW_PROGRESS = 2;
    private static final int PAUSE_IMAGE_HIDE = 3;
    private View itemView;
    private View view;
    private boolean isShow;
    private IjkVideoView videoView;
    private boolean isScroll;

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

    private SeekBar seekBar;
    AudioManager audioManager;
    private ProgressBar progressBar;

    private GestureDetector gestureDetector;
    private MyGestureListener gestureListener;
    private boolean isSound;
    private boolean isDragging;

    private boolean isPause;

    private boolean isShowContoller;
    private ImageView sound, full, play;
    private TextView time, allTime;
    private PointF lastPoint;
    private Context context;
    private ImageView pauseImage;
    private Bitmap bitmap;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SET_VIEW_HIDE:
                    isShow = false;
                    itemView.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_PROGRESS:
                    setProgress();
                    if (!isDragging && isShow) {
                        msg = obtainMessage(MESSAGE_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000);
                    }
                    break;
                case PAUSE_IMAGE_HIDE:
                    pauseImage.setVisibility(View.GONE);
                    break;
            }
        }
    };

    public CustomMediaContoller(Context context, View view) {
        this.view = view;
        itemView = view.findViewById(R.id.media_contoller);
        this.videoView = (IjkVideoView) view.findViewById(R.id.main_video);
        itemView.setVisibility(View.GONE);
        isShow = false;
        isDragging = false;

        isShowContoller = true;
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        initView();
        initAction();
    }

    public void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.loading);
        seekBar = (SeekBar) itemView.findViewById(R.id.seekbar);
        allTime = (TextView) itemView.findViewById(R.id.all_time);
        time = (TextView) itemView.findViewById(R.id.time);
        full = (ImageView) itemView.findViewById(R.id.full);
        sound = (ImageView) itemView.findViewById(R.id.sound);
        play = (ImageView) itemView.findViewById(R.id.player_btn);
        pauseImage = (ImageView) view.findViewById(R.id.pause_image);

        gestureListener = new MyGestureListener(itemView);
        gestureDetector = new GestureDetector(context, gestureListener);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public void start() {
        pauseImage.setVisibility(View.GONE);
        itemView.setVisibility(View.GONE);
        play.setImageResource(R.mipmap.ic_pause_circle_outline_white_36dp);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void pause() {
        play.setImageResource(R.mipmap.ic_play_circle_outline_white_36dp);
        videoView.pause();
        bitmap = videoView.getBitmap();
        if (bitmap != null) {
            pauseImage.setImageBitmap(bitmap);
            pauseImage.setVisibility(View.VISIBLE);
        }
    }

    public void reStart() {
        play.setImageResource(R.mipmap.ic_pause_circle_outline_white_36dp);
        videoView.start();
        if (bitmap != null) {
            handler.sendEmptyMessageDelayed(PAUSE_IMAGE_HIDE, 100);
            bitmap.recycle();
            bitmap = null;
//                        pauseImage.setVisibility(View.GONE);
        }
    }

    private long duration;

    private void initAction() {
        isSound = false;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String string = generateTime((long) (duration * progress * 1.0f / 100));
                time.setText(string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setProgress();
                isDragging = true;
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                handler.removeMessages(MESSAGE_SHOW_PROGRESS);
                show();
                handler.removeMessages(SET_VIEW_HIDE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isDragging = false;
                videoView.seekTo((int) (duration * seekBar.getProgress() * 1.0f / 100));
                handler.removeMessages(MESSAGE_SHOW_PROGRESS);
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                isDragging = false;
                handler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS, 1000);
                show();
            }
        });

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event))
                    return true;

                // 处理手势结束
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        endGesture();
                        break;
                }

                return false;
            }
        });

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {

                Log.e("setOnInfoListener", what + "");
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //开始缓冲
                        if (progressBar.getVisibility() == View.GONE)
                            progressBar.setVisibility(View.VISIBLE);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //开始播放
                        progressBar.setVisibility(View.GONE);
                        break;

                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                        statusChange(STATUS_PLAYING);
                        progressBar.setVisibility(View.GONE);
                        break;

                    case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        progressBar.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSound) {
                    //静音
                    sound.setImageResource(R.mipmap.ic_volume_off_white_36dp);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                } else {
                    //取消静音
                    sound.setImageResource(R.mipmap.ic_volume_high_white_36dp);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                }
                isSound = !isSound;
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    pause();
                } else {
                    reStart();
                }
            }
        });

        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("full", "full");
                if (getScreenOrientation((Activity) context) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    full.setImageResource(R.mipmap.ic_fullscreen_white_36dp);
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    full.setImageResource(R.mipmap.ic_fullscreen_exit_white_36dp);
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }

        });
    }

    public void setShowContoller(boolean isShowContoller) {
        this.isShowContoller = isShowContoller;
        handler.removeMessages(SET_VIEW_HIDE);
        itemView.setVisibility(View.GONE);
    }

    public int getScreenOrientation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    @Override
    public void hide() {
        if (isShow) {
            handler.removeMessages(MESSAGE_SHOW_PROGRESS);
            isShow = false;
            handler.removeMessages(SET_VIEW_HIDE);
            itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isShowing() {
        return isShow;
    }

    @Override
    public void setAnchorView(View view) {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
    }

    @Override
    public void show(int timeout) {
        handler.sendEmptyMessageDelayed(SET_VIEW_HIDE, timeout);
    }

    @Override
    public void show() {
        if (!isShowContoller)
            return;
        isShow = true;
        progressBar.setVisibility(View.GONE);
        itemView.setVisibility(View.VISIBLE);
        handler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
        show(TIME_OUT);
    }

    @Override
    public void showOnce(View view) {
    }

    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public void setVisiable() {
        show();
    }

    private long setProgress() {
        if (isDragging) {
            return 0;
        }

        long position = videoView.getCurrentPosition();
        long duration = videoView.getDuration();
        this.duration = duration;
        if (!generateTime(duration).equals(allTime.getText().toString()))
            allTime.setText(generateTime(duration));
        if (seekBar != null) {
            if (duration > 0) {
                long pos = 100L * position / duration;
                seekBar.setProgress((int) pos);
            }
            int percent = videoView.getBufferPercentage();
            seekBar.setSecondaryProgress(percent);
        }
        String string = generateTime(position);
        time.setText(string);
        return position;
    }

    private VedioIsPause vedioIsPause;

    public interface VedioIsPause {
        void pause(boolean pause);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
    }

    public void setPauseImageHide() {
        pauseImage.setVisibility(View.GONE);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private View item;

        public MyGestureListener(View item) {
            this.item = item;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        /**
         * 滑动
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();

            int windowWidth = item.getWidth();
            int windowHeight = item.getHeight();

            if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        Logger.d("OnVolumeSlide --> " + percent);
        if (mVolume == -1) {
            mVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            //mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            //mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        //ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        //lp.width = findViewById(R.id.operation_full).getLayoutParams().width
        //        * index / mMaxVolume;
        //mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        Logger.d("OnBrightnessSlide --> " + percent);
        if (mBrightness < 0) {
            mBrightness = ((Activity) context).getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            //mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            //mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = ((Activity) context).getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        ((Activity) context).getWindow().setAttributes(lpa);

        //ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        //lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa
        //        .screenBrightness);
        //mOperationPercent.setLayoutParams(lp);
    }
}
