package cn.jas0n.amovie.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import cn.jas0n.amovie.util.Utils;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class FixedViewPager extends ViewPager {
    private Context mContext;
    private boolean disableAppbar;

    public FixedViewPager(Context context) {
        super(context);
        this.mContext = context;
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setDisableAppbar(boolean disableAppbar) {
        this.disableAppbar = disableAppbar;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        if (!disableAppbar)
            height = Utils.getFixedViewpagerHeight(mContext);
        else
            height = Utils.getUnScrollFixedViewpagerHeight(mContext);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
