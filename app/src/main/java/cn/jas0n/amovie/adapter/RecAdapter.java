package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.ui.view.CustomVideoItemLayout;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecAdapter extends BaseAdapter implements View.OnClickListener {

    private ClickVideo clickVideo;

    public RecAdapter(List mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public void onClick(View view) {

    }

    public interface ClickVideo {
        void onVideoClicked(ImageView image, RecBean.HotVideoItem video);
    }

    public void setClickVideo(ClickVideo clickVideo){
        this.clickVideo = clickVideo;
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new RecHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_item, parent,
                false));
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, int position) {
        final RecHolder recHolder = (RecHolder) holder;
        final RecBean.Video video = (RecBean.Video) mData.get(position);
        recHolder.mTitle.setText(video.getTitle());
        recHolder.mItem.setCover(video.getVideos().get(0).getUrl());
        recHolder.mItem.setTitle(video.getVideos().get(0).getTitle());
        recHolder.mItem.setViewCount(video.getVideos().get(0).getViewCount());
        recHolder.mItem.setCommentCount(video.getVideos().get(0).getDanmuCount());
        if(video.getVideos().get(0).getAuthor() != null){
            recHolder.mItem.setUserName(video.getVideos().get(0).getAuthor().getNickName());
            recHolder.mItem.setAvatar(video.getVideos().get(0).getAuthor().getHeadImgUrl());
        }
        int count = video.getVideos().size() >= 5 ? 5 : video.getVideos().size();
        switch (count) {
            case 5:
                fillData(video, 1, recHolder.mItem1);
                fillData(video, 2, recHolder.mItem2);
                fillData(video, 3, recHolder.mItem3);
                fillData(video, 4, recHolder.mItem4);
                break;
            case 4:
                fillData(video, 1, recHolder.mItem1);
                fillData(video, 2, recHolder.mItem2);
                fillData(video, 3, recHolder.mItem3);
                break;
            case 3:
                fillData(video, 1, recHolder.mItem1);
                fillData(video, 2, recHolder.mItem2);
                break;
            case 2:
                fillData(video, 1, recHolder.mItem1);
                break;
            case 1:
                break;
        }

        recHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickVideo.onVideoClicked(recHolder.mItem.getCover(), video.getVideos().get(0));
            }
        });
    }

    private void fillData(final RecBean.Video video, final int index, final CustomVideoItemLayout item) {
        if(video.getVideos().get(index).getAuthor() != null){
            item.setAvatar(video.getVideos().get(index).getAuthor().getHeadImgUrl());
            item.setUserName(video.getVideos().get(index).getAuthor().getNickName());
        }
        item.setCover(video.getVideos().get(index).getUrl());
        item.setTitle(video.getVideos().get(index).getTitle());
        item.setViewCount(video.getVideos().get(index).getViewCount());
        item.setCommentCount(video.getVideos().get(index).getDanmuCount());
        item.setVisibility(View.VISIBLE);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickVideo.onVideoClicked(item.getCover(), video.getVideos().get(index));
            }
        });
    }

    class RecHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;
        @BindView(R.id.item)
        CustomVideoItemLayout mItem;
        @BindView(R.id.item1)
        CustomVideoItemLayout mItem1;
        @BindView(R.id.item2)
        CustomVideoItemLayout mItem2;
        @BindView(R.id.item3)
        CustomVideoItemLayout mItem3;
        @BindView(R.id.item4)
        CustomVideoItemLayout mItem4;

        public RecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
