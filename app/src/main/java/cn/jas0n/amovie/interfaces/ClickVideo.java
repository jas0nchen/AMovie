package cn.jas0n.amovie.interfaces;

import android.widget.ImageView;

import cn.jas0n.amovie.bean.RecBean;

/**
 * Author: Jas0n
 * Date: 2016/7/6
 * E-mail:chendong90x@gmail.com
 */
public interface ClickVideo {
    void onVideoClicked(ImageView image, RecBean.HotVideoItem video);
}
