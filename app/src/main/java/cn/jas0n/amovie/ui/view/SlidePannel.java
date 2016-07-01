package cn.jas0n.amovie.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.util.Utils;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class SlidePannel {
    private Context mContext;
    private View mItemView;
    private ImageView mSrc;
    private TextView mText;

    public SlidePannel(Context context, View view) {
        this.mContext = context;
        this.mItemView = view;
        this.mSrc = (ImageView) mItemView.findViewById(R.id.src);
        this.mText = (TextView) mItemView.findViewById(R.id.text);
    }

    public void showSlideProgress(int oldPosition, float percent, int duration) {
        if (percent >= 0) {
            mSrc.setImageResource(R.mipmap.ic_skip_forward_white_36dp);
        } else {
            mSrc.setImageResource(R.mipmap.ic_skip_backward_white_36dp);
        }
        int newPosition = (int) (oldPosition + percent * duration / 10);
        if (newPosition <= 0)
            newPosition = 0;
        if (newPosition > duration)
            newPosition = duration;
        mText.setText(Utils.generateTime(newPosition) + "/" + Utils.generateTime(duration));
        show();
    }

    public void showSlideVolume(int newVolume) {
        if (newVolume == 0) {
            mSrc.setImageResource(R.mipmap.ic_volume_off_white_36dp);
        } else if (newVolume > 0 && newVolume <= 33) {
            mSrc.setImageResource(R.mipmap.ic_volume_low_white_36dp);
        } else if (newVolume > 33 && newVolume <= 67) {
            mSrc.setImageResource(R.mipmap.ic_volume_medium_white_36dp);
        } else {
            mSrc.setImageResource(R.mipmap.ic_volume_high_white_36dp);
        }
        mText.setText(newVolume + "%");
        show();
    }

    public void showSlideBrightness(float brightness) {
        mSrc.setImageResource(R.mipmap.ic_brightness_7_white_36dp);
        int bright = (int) (brightness * 100);
        mText.setText(bright + "%");
        show();
    }

    public void show() {
        mItemView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mItemView.setVisibility(View.GONE);
    }
}
