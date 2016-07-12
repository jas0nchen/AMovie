package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class DramaAdapter extends RecyclerArrayAdapter<RecBean.RecDramaItem> {

    private Context mContext;

    public DramaAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    class ViewHolder extends BaseViewHolder<RecBean.RecDramaItem>{
        LinearLayout mLayout;
        ImageView mCover;
        TextView mName;
        TextView mUpdate;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.layout_drama_item_card);
            mLayout = $(R.id.layout);
            mCover = $(R.id.cover);
            mName = $(R.id.name);
            mUpdate = $(R.id.update);
        }

        @Override
        public void setData(final RecBean.RecDramaItem data) {
            mName.setText(data.getTitle());
            mUpdate.setText(String.format(mContext.getString(R.string.update_to), data.getUpInfo()));
            Glide.with(mContext).load(data.getVerticalUrl()).centerCrop().crossFade().into
                    (mCover);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickSeason.onClickSeason(mCover, data);
                }
            });
        }
    }

    private ClickSeason clickSeason;

    public void setClickSeason(ClickSeason clickSeason){
        this.clickSeason = clickSeason;
    }
}
