package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class VideoGridAdapter extends android.widget.BaseAdapter {
    private List<RecBean.HotVideoItem> mData;
    private Context mContext;

    public VideoGridAdapter(List<RecBean.HotVideoItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size() - 1;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i + 1);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i + 1).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_video_item, viewGroup,
                    false);
            holder = new ViewHolder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTitle.setText(mData.get(i + 1).getTitle());
        Glide.with(mContext).load(mData.get(i + 1).getUrl()).centerCrop().crossFade().into
                (holder.mCover);
        if (mData.get(i + 1).getAuthor() != null) {
            Glide.with(mContext).load(mData.get(i + 1).getAuthor().getHeadImgUrl()).centerCrop()
                    .crossFade().into(holder.mAvatar);
            holder.mName.setText(mData.get(i + 1).getAuthor().getNickName());
        }
        holder.mViewCount.setText(String.valueOf(mData.get(i + 1).getViewCount()));
        holder.mCommentCount.setText(String.valueOf(mData.get(i + 1).getDanmuCount()));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.cover)
        ImageView mCover;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.avatar)
        CircleImageView mAvatar;
        @BindView(R.id.view_count)
        TextView mViewCount;
        @BindView(R.id.comment_count)
        TextView mCommentCount;
    }
}
