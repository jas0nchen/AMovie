package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import cn.jas0n.amovie.R;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecAdapter extends BaseAdapter {

    public RecAdapter(List mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new RecHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_rec_item, parent,
                false));
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class RecHolder extends RecyclerView.ViewHolder {


        public RecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
