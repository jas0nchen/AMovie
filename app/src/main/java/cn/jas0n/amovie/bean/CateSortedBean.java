package cn.jas0n.amovie.bean;

import java.io.Serializable;

/**
 * Author: Jas0n
 * Date: 2016/7/15
 * E-mail:chendong90x@gmail.com
 */
public class CateSortedBean implements Serializable {
    private int mType;
    private RecBean.HotVideoItem mVideo;
    private String mTitle;
    private boolean isFirst;

    public CateSortedBean(int mType, RecBean.HotVideoItem mVideo, String mTitle, boolean isFirst) {
        this.mType = mType;
        this.mVideo = mVideo;
        this.mTitle = mTitle;
        this.isFirst = isFirst;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public RecBean.HotVideoItem getmVideo() {
        return mVideo;
    }

    public void setmVideo(RecBean.HotVideoItem mVideo) {
        this.mVideo = mVideo;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
