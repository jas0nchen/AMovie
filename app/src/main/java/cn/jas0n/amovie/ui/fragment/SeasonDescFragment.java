package cn.jas0n.amovie.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.AMovie;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.EpisodeGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Episode;
import cn.jas0n.amovie.bean.SeasonDetail;
import cn.jas0n.amovie.bean.VideoDetail;
import cn.jas0n.amovie.ui.activity.EpisodePlayActivity;
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
public class SeasonDescFragment extends LazyFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.user_layout)
    RelativeLayout mUserLayout;
    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.title_en)
    TextView mTitleEn;
    @BindView(R.id.view_count)
    TextView mViewCount;
    @BindView(R.id.score)
    TextView mScore;
    @BindView(R.id.brief)
    TextView mBrief;
    @BindView(R.id.username)
    TextView mName;
    @BindView(R.id.create_time)
    TextView mCreateTime;
    @BindView(R.id.follow)
    TextView mFollow;
    @BindView(R.id.episode_grid)
    FixedGridView mEpisodeGrid;

    private SeasonDetail mDetail;
    List<SeasonDetail.EpisodeBrief> mEpisodeList;
    private boolean isRefresh;
    private EpisodeGridAdapter mAdapter;

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
        mFollow.setOnClickListener(this);
    }

    private void fillDetail() {
        if (mDetail.getData().getSeason().getAuthor() != null) {
            Glide.with(this).load(mDetail.getData().getSeason().getAuthor().getHeadImgUrl()).centerCrop().crossFade
                    ().into(mAvatar);
            mName.setText(mDetail.getData().getSeason().getAuthor().getNickName());
            mCreateTime.setText(String.format(getString(R.string.created_at), mDetail.getData()
                    .getSeason().getCreateTimeStr()));
        } else {
            mUserLayout.setVisibility(View.GONE);
        }

        mTitle.setText(mDetail.getData().getSeason().getTitle());
        mTitleEn.setText(mDetail.getData().getSeason().getEnTitle());
        mViewCount.setText(String.format(getString(R.string.play_count), mDetail.getData()
                .getSeason().getViewCount()));
        mScore.setText(String.format(getString(R.string.score), mDetail.getData().getSeason()
                .getScore()));
        mBrief.setText(mDetail.getData().getSeason().getBrief());

        mEpisodeList = mDetail.getData().getSeason().getEpisode_brief();
        Collections.reverse(mEpisodeList);
        mAdapter = new EpisodeGridAdapter(mEpisodeList, getContext());
        mEpisodeGrid.setAdapter(mAdapter);
        mEpisodeGrid.setOnItemClickListener(this);
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public void onClick(View view) {
        if (view == mFollow) {
            doFollow();
        }
    }

    private void doFollow() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("正在获取播放链接中...");
        dialog.show();

        AMovieService.builder().getApiService().getEpisode("high", mDetail.getData().getSeason()
                .getSid(),mEpisodeList.get(i).getSid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Episode>() {
                    @Override
                    public void call(Episode episode) {
                        dialog.dismiss();
                        JCFullScreenActivity.startActivity(getContext(), episode.getData()
                                .getM3u8().getUrl(), JCVideoPlayerStandardShowShareButtonAfterFullscreen.class,
                                mDetail.getData().getSeason().getTitle() + " E " + mEpisodeList.get(i).getEpisode());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        dialog.dismiss();
                    }
                });
    }
}
