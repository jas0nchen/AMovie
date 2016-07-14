package cn.jas0n.amovie.ui.adapter;

import android.content.Context;
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
import cn.jas0n.amovie.bean.SeasonDetail;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class EpisodeGridAdapter extends android.widget.BaseAdapter {

    private List<SeasonDetail.EpisodeBrief> mData;
    private Context mContext;

    public EpisodeGridAdapter(List<SeasonDetail.EpisodeBrief> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
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
        return Long.parseLong(mData.get(i).getEpisode());
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_episode_item, viewGroup,
                    false);
            holder = new ViewHolder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTv.setText(mData.get(i).getEpisode());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.layout)
        RelativeLayout mLayout;
        @BindView(R.id.tv)
        TextView mTv;
    }
}
