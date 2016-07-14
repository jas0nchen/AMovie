package cn.jas0n.amovie.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickVideo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/7/12
 * E-mail:chendong90x@gmail.com
 */
public class VideoAdapter extends RecyclerArrayAdapter<RecBean.HotVideoItem> {

    private Context mContext;
    private ClickVideo mClickVideo;

    public VideoAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<RecBean.HotVideoItem> {

        RelativeLayout mLayout;
        ImageView mCover;
        TextView mTitle;
        TextView mName;
        TextView mViewCount;
        TextView mCommentCount;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.layout_video_recycler_item);
            mLayout = (RelativeLayout) $(R.id.layout);
            mCover = (ImageView) $(R.id.cover);
            mTitle = (TextView) $(R.id.title);
            mName = (TextView) $(R.id.name);
            mViewCount = (TextView) $(R.id.view_count);
            mCommentCount = (TextView) $(R.id.comment_count);
        }

        @Override
        public void setData(final RecBean.HotVideoItem data) {
            mTitle.setText(data.getTitle());
            Glide.with(mContext).load(data.getUrl()).centerCrop().crossFade().into(mCover);
            if (data.getAuthor() != null) {
                mName.setText(data.getAuthor().getNickName());
            }
            mViewCount.setText(String.valueOf(data.getViewCount()));
            mCommentCount.setText(String.valueOf(data.getDanmuCount()));

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickVideo.onVideoClicked(mCover, data);
                }
            });
        }
    }

    public void setClickVideo(ClickVideo clickVideo) {
        this.mClickVideo = clickVideo;
    }
}
