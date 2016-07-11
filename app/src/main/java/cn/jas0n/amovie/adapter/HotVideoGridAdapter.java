package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickVideo;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class HotVideoGridAdapter extends android.widget.BaseAdapter {
    private List<RecBean.HotVideoItem> mData;
    private Context mContext;
    private ClickVideo clickVideo;
    private Handler mHandler;

    public HotVideoGridAdapter(List<RecBean.HotVideoItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_hot_video_item, viewGroup,
                    false);
            holder = new ViewHolder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTitle.setText(mData.get(i).getTitle());
        final ViewHolder finalHolder1 = holder;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(mContext).load(mData.get(i).getUrl()).centerCrop().crossFade().into
                        (finalHolder1.mCover);
            }
        }, 400);
        if (mData.get(i).getAuthor() != null) {
            holder.mName.setText(mData.get(i).getAuthor().getNickName());
        }
        holder.mViewCount.setText(String.valueOf(mData.get(i).getViewCount()));
        holder.mCommentCount.setText(String.valueOf(mData.get(i).getDanmuCount()));

        final ViewHolder finalHolder = holder;
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickVideo.onVideoClicked(finalHolder.mCover, mData.get(i));
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.layout)
        RelativeLayout mLayout;
        @BindView(R.id.cover)
        ImageView mCover;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.view_count)
        TextView mViewCount;
        @BindView(R.id.comment_count)
        TextView mCommentCount;
    }

    public void setClickVideo(ClickVideo clickVideo) {
        this.clickVideo = clickVideo;
    }
}
