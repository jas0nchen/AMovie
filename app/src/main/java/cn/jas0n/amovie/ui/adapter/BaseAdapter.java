package cn.jas0n.amovie.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mData;
    protected Context mContext;

    public BaseAdapter(List<T> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);

    public abstract void bindHolder(RecyclerView.ViewHolder holder, int position);
}
