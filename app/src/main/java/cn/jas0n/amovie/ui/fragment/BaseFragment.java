package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.RecAdapter;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class BaseFragment extends LazyFragment {

    protected View mView;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;

    protected RecyclerArrayAdapter mAdapter;
    private Handler mHandler;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_base, container, false);
        }
        ButterKnife.bind(this, mView);

        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setErrorView(R.layout.view_error_large);
        mAdapter = new RecAdapter(getContext());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doOnRefresh();
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {
    }

    protected void doOnRefresh(){}

    protected void doOnLoad(){}

    public void setHandler(Handler handler){
        this.mHandler = handler;
    }

    public Handler getHandler(){
        return mHandler;
    }
}
