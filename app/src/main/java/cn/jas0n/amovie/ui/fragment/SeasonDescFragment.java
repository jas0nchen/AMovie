package cn.jas0n.amovie.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.EpisodeAdapter;
import cn.jas0n.amovie.adapter.EpisodeGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Episode;
import cn.jas0n.amovie.bean.SeasonDetail;
import cn.jas0n.amovie.ui.view.FixedGridView;
import cn.jas0n.amovie.ui.view.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class SeasonDescFragment extends LazyFragment implements View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SeasonDetail mDetail;
    private List<SeasonDetail.EpisodeBrief> mEpisodeList;
    private boolean isRefresh;
    private EpisodeAdapter mAdapter;

    public static SeasonDescFragment newInstance(SeasonDetail detail) {
        SeasonDescFragment fragment = new SeasonDescFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(SeasonDetail detail) {
        this.mDetail = detail;
        this.isRefresh = true;
        if (!isFirstLoad)
            fillDetail();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season_desc, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        if (!isRefresh)
            mDetail = (SeasonDetail) getArguments().getSerializable("detail");

        fillDetail();
    }

    private void fillDetail() {
        mEpisodeList = mDetail.getData().getSeason().getEpisode_brief();
        Collections.reverse(mEpisodeList);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new EpisodeAdapter(getContext(), mDetail, mEpisodeList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public void onClick(View view) {

    }
}
