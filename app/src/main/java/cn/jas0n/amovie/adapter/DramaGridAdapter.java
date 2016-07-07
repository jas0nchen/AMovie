package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class DramaGridAdapter extends android.widget.BaseAdapter {
    private List<RecBean.RecDramaItem> mData;
    private Context mContext;

    public DramaGridAdapter(List<RecBean.RecDramaItem> mData, Context mContext) {
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
        return mData.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_drama_item, viewGroup, false);
            holder = new ViewHolder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mName.setText(mData.get(i).getTitle());
        holder.mUpdate.setText(String.format(mContext.getString(R.string.update_to), mData.get(i)
                .getUpInfo()));
        Glide.with(mContext).load(mData.get(i).getVerticalUrl()).centerCrop().crossFade().into
                (holder.mCover);
        final ViewHolder finalHolder = holder;
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSeason.onClickSeason(finalHolder.mCover, mData.get(i));
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.layout)
        RelativeLayout mLayout;
        @BindView(R.id.cover)
        ImageView mCover;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.update)
        TextView mUpdate;
    }

    private ClickSeason clickSeason;

    public void setClickSeason(ClickSeason clickSeason) {
        this.clickSeason = clickSeason;
    }
}
