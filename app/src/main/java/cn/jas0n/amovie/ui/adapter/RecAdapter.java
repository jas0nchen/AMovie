package cn.jas0n.amovie.ui.adapter;

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

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
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
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.view.CustomVideoItemLayout;
import cn.jas0n.amovie.ui.view.FixedGridView;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecAdapter extends RecyclerArrayAdapter<RecBean.HotVideoItem> {

    private Map<Integer, String> mCateMap = new HashMap<>();
    private ClickVideo mClickVideo;

    public RecAdapter(Context context) {
        super(context);
    }

    public void setClickVideo(ClickVideo clickVideo) {
        this.mClickVideo = clickVideo;
    }

    public void setCateMap(Map<Integer, String> cateMap) {
        this.mCateMap = cateMap;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecHolder(parent, this);
    }

    public class RecHolder extends BaseViewHolder<RecBean.HotVideoItem> {

        RecyclerArrayAdapter mAdapter;
        RelativeLayout mCategoryLayout;
        TextView mTitle;
        TextView mMore;
        CustomVideoItemLayout mItem;

        public RecHolder(ViewGroup parent, RecyclerArrayAdapter arrayAdapter) {
            super(parent, R.layout.layout_rec_item);

            this.mAdapter = arrayAdapter;
            mCategoryLayout = $(R.id.category_layout);
            mTitle = $(R.id.category);
            mMore = $(R.id.more);
            mItem = $(R.id.item);
        }

        @Override
        public void setData(final RecBean.HotVideoItem item) {
            if (mCateMap.containsKey(mAdapter.getPosition(item))) {
                mCategoryLayout.setVisibility(View.VISIBLE);
                mTitle.setText(mCateMap.get(mAdapter.getPosition(item)));
            } else {
                mCategoryLayout.setVisibility(View.GONE);
            }
            mItem.setCover(item.getUrl());
            mItem.setTitle(item.getTitle());
            mItem.setViewCount(item.getViewCount());
            mItem.setCommentCount(item.getDanmuCount());
            if (item.getAuthor() != null) {
                mItem.setUserName(item.getAuthor().getNickName());
                mItem.setAvatar(item.getAuthor().getHeadImgUrl());
            }

            mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickVideo.onVideoClicked(mItem.getCover(), item);
                }
            });
        }
    }
}
