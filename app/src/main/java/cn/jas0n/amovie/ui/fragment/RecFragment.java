package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.BaseAdapter;
import cn.jas0n.amovie.adapter.RecAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.ui.activity.VideoDetailActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecFragment extends BaseFragment {

    private RecBean.Data mData;
    private List<RecBean.HotVideoItem> mHotList = new ArrayList<>();
    private List<RecBean.RecDramaItem> mRecList = new ArrayList<>();
    private List<RecBean.Video> mVideoList = new ArrayList<>();

    public static RecFragment newInstanse(String title) {
        RecFragment fragment = new RecFragment();
        fragment.setDefaultFragmentTitle(title);
        return fragment;
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getRecVideos("3.0.2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RecBean>() {
                    @Override
                    public void call(RecBean recBean) {
                        fillData(recBean);
                        setupViews();
                        hideLoadingView();
                        mRecyclerView.refreshComplete();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        hideLoadingView();
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    private void fillData(RecBean recBean) {
        mData = recBean.getData();
        mVideoList.clear();
        mHotList.clear();
        mRecList.clear();
        mVideoList = recBean.getData().getVideo();
        mHotList = recBean.getData().getHotVideo();
        mRecList = recBean.getData().getRecDrama();
    }

    private void setupViews() {
        mAdapter = new RecAdapter(mHotList, mRecList, mVideoList, getContext());
        ((RecAdapter) mAdapter).setClickVideo(getClickVideo());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    protected void doOnRefresh() {
        super.doOnRefresh();
        initData();
    }

    private RecAdapter.ClickVideo getClickVideo() {
        return new RecAdapter.ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                    startActivity(VideoDetailActivity.newIntent(getContext(), video),
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), image,
                                    "transitionCover").toBundle());
                }else {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video));
                }
            }
        };
    }
}
