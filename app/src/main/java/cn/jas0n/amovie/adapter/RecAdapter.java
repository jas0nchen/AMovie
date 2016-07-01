package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.ui.view.CustomVideoItemLayout;
import cn.jas0n.amovie.ui.view.FixedGridView;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecAdapter extends BaseAdapter implements View.OnClickListener {
    private static final int VIDEO_ITEM = 1;
    private static final int DRAMA_ITEM = 2;
    private static final int HOT_ITEM = 3;

    private ClickVideo clickVideo;
    private List<RecBean.HotVideoItem> mHot;
    private List<RecBean.RecDramaItem> mDrama;

    public RecAdapter(List<RecBean.HotVideoItem> hot, List<RecBean.RecDramaItem> drama, List
            data, Context context) {
        super(data, context);
        this.mHot = hot;
        this.mDrama = drama;
    }

    @Override
    public void onClick(View view) {

    }

    public interface ClickVideo {
        void onVideoClicked(ImageView image, RecBean.HotVideoItem video);
    }

    public void setClickVideo(ClickVideo clickVideo) {
        this.clickVideo = clickVideo;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HOT_ITEM;
        } else if (position == 1) {
            return DRAMA_ITEM;
        } else
            return VIDEO_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT_ITEM) {
            return new HotHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_hot_item,
                    parent, false));
        } else if (viewType == VIDEO_ITEM) {
            return new RecHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_item,
                    parent, false));
        } else {
            return new DramaHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_drama_item,
                    parent, false));
        }
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HotHolder hotHolder = (HotHolder) holder;
            hotHolder.mTitle.setText("热门");
            VideoGridAdapter adapter = new VideoGridAdapter(mHot, mContext);
            hotHolder.mHotGrid.setAdapter(adapter);
        } else if (position == 1) {
            DramaHolder dramaHolder = (DramaHolder) holder;
            DramaGridAdapter adapter = new DramaGridAdapter(mDrama, mContext);
            dramaHolder.mDramaGrid.setAdapter(adapter);
            dramaHolder.mTitle.setText("英美剧");
        } else {
            final RecHolder recHolder = (RecHolder) holder;
            final RecBean.Video video = (RecBean.Video) mData.get(position - 2);
            recHolder.mTitle.setText(video.getTitle());
            recHolder.mItem.setCover(video.getVideos().get(0).getUrl());
            recHolder.mItem.setTitle(video.getVideos().get(0).getTitle());
            recHolder.mItem.setViewCount(video.getVideos().get(0).getViewCount());
            recHolder.mItem.setCommentCount(video.getVideos().get(0).getDanmuCount());
            if (video.getVideos().get(0).getAuthor() != null) {
                recHolder.mItem.setUserName(video.getVideos().get(0).getAuthor().getNickName());
                recHolder.mItem.setAvatar(video.getVideos().get(0).getAuthor().getHeadImgUrl());
            }
            recHolder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickVideo.onVideoClicked(recHolder.mItem.getCover(), video.getVideos().get(0));
                }
            });
            List<RecBean.HotVideoItem> items = video.getVideos();
            VideoGridAdapter adapter = new VideoGridAdapter(items, mContext);
            recHolder.mVideoGrid.setAdapter(adapter);
        }
    }

    class RecHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;
        @BindView(R.id.item)
        CustomVideoItemLayout mItem;
        @BindView(R.id.video_grid)
        FixedGridView mVideoGrid;

        public RecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HotHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;
        @BindView(R.id.hot_grid)
        GridView mHotGrid;

        public HotHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DramaHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;
        @BindView(R.id.drama_grid)
        GridView mDramaGrid;

        public DramaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
