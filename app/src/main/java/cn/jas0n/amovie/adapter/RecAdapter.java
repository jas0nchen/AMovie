package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ClickVideo clickVideo;
    private Map<Integer,String> categoryMap = new HashMap<>();

    private List<RecBean.HotVideoItem> videos = new ArrayList<>();

    public RecAdapter(List data, Context context) {
        super(data, context);
        init();
    }

    private void init() {
        int count = 0;
        this.categoryMap.clear();
        this.videos.clear();
        for(int i = 0; i < mData.size(); i++){
            this.videos.addAll(((RecBean.Video) mData.get(i)).getVideos());
            categoryMap.put(count, ((RecBean.Video) mData.get(i)).getTitle());
            count += ((RecBean.Video) mData.get(i)).getVideos().size();
        }
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
        return videos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIDEO_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new RecHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_item,
                parent, false));
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, int position) {
        final RecHolder recHolder = (RecHolder) holder;
        final RecBean.HotVideoItem videoItem = videos.get(position);
        if(categoryMap.keySet().contains(position)){
            Logger.d("Category -->" + categoryMap.get(position));
            recHolder.mCategoryLayout.setVisibility(View.VISIBLE);
            recHolder.mTitle.setText(categoryMap.get(position));
        }else{
            recHolder.mCategoryLayout.setVisibility(View.GONE);
        }

        recHolder.mItem.setCover(videoItem.getUrl());
        recHolder.mItem.setTitle(videoItem.getTitle());
        recHolder.mItem.setViewCount(videoItem.getViewCount());
        recHolder.mItem.setCommentCount(videoItem.getDanmuCount());
        if (videoItem.getAuthor() != null) {
            recHolder.mItem.setUserName(videoItem.getAuthor().getNickName());
            recHolder.mItem.setAvatar(videoItem.getAuthor().getHeadImgUrl());
        }
        recHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickVideo.onVideoClicked(recHolder.mItem.getCover(), videoItem);
            }
        });
    }

    class RecHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_layout)
        RelativeLayout mCategoryLayout;
        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;
        @BindView(R.id.item)
        CustomVideoItemLayout mItem;

        public RecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
