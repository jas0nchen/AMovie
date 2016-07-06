package cn.jas0n.amovie.ui.fragment;

import android.app.ActivityOptions;
import android.content.res.Configuration;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.DramaGridAdapter;
import cn.jas0n.amovie.adapter.RecAdapter;
import cn.jas0n.amovie.adapter.VideoGridAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.activity.SeasonDetailActivity;
import cn.jas0n.amovie.ui.activity.VideoDetailActivity;
import cn.jas0n.amovie.ui.view.FixedGridView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/24
 * E-mail:chendong90x@gmail.com
 */
public class RecFragment extends BaseFragment {

    private boolean isPaused;
    private boolean isHeaderLoaded;
    private RecBean.Data mData;
    private List<RecBean.HotVideoItem> mHotList = new ArrayList<>();
    private List<RecBean.RecDramaItem> mRecList = new ArrayList<>();
    private List<RecBean.Video> mVideoList = new ArrayList<>();
    private List<Object> mVideos = new ArrayList<>();
    private Map<Integer, String> mCateMap = new HashMap<>();

    private View mHeaderView;
    private RecyclerArrayAdapter.ItemView mHeader;

    public static RecFragment newInstanse(String title) {
        RecFragment fragment = new RecFragment();
        fragment.setDefaultFragmentTitle(title);
        return fragment;
    }

    @Override
    protected void initData() {
        AMovieService.builder().getApiService().getRecVideos("3.0.3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RecBean>() {
                    @Override
                    public void call(RecBean recBean) {
                        fillData(recBean);
                        setupViews();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mRecyclerView.showError();
                    }
                });
    }

    private void fillData(RecBean recBean) {
        mData = recBean.getData();
        mVideoList.clear();
        mHotList.clear();
        mRecList.clear();
        mVideos.clear();
        mVideoList = recBean.getData().getVideo();
        mHotList = recBean.getData().getHotVideo();
        mRecList = recBean.getData().getRecDrama();

        int count = 0;
        for (int i = 0; i < mVideoList.size(); i++) {
            mVideos.addAll(mVideoList.get(i).getVideos());
            mCateMap.put(count, mVideoList.get(i).getTitle());
            count += mVideoList.get(i).getVideos().size();
        }
    }

    private void setupViews() {
        mAdapter.clear();
        mAdapter.removeAllHeader();
        ((RecAdapter) mAdapter).setCateMap(mCateMap);
        ((RecAdapter) mAdapter).setClickVideo(getClickVideo());

        mHeader = new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_hot_header,
                        parent, false);
                return mHeaderView;
            }

            @Override
            public void onBindView(View headerView) {
                VideoGridAdapter hotAdapter = new VideoGridAdapter(mHotList, getContext());
                DramaGridAdapter dramaAdapter = new DramaGridAdapter(mRecList, getContext());
                TextView hotCategory = (TextView) headerView.findViewById(R.id.category);
                FixedGridView hotGrid = (FixedGridView) headerView.findViewById(R.id.hot_grid);
                TextView dramaCategory = (TextView) headerView.findViewById(R.id.drama_category);
                FixedGridView dramaGrid = (FixedGridView) headerView.findViewById(R.id.drama_grid);
                hotCategory.setText("热门");
                dramaCategory.setText("英美剧");
                hotGrid.setAdapter(hotAdapter);
                dramaGrid.setAdapter(dramaAdapter);
                hotAdapter.setClickVideo(getClickVideo());
                dramaAdapter.setClickSeason(getClickSeason());
            }
        };
        mAdapter.addHeader(mHeader);
        mAdapter.addAll(mVideos);
    }

    @Override
    protected void doOnRefresh() {
        super.doOnRefresh();
        initData();
    }

    private ClickVideo getClickVideo() {
        return new ClickVideo() {
            @Override
            public void onVideoClicked(ImageView image, RecBean.HotVideoItem video) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video),
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), image,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(VideoDetailActivity.newIntent(getContext(), video));
                }
            }
        };
    }

    private ClickSeason getClickSeason() {
        return new ClickSeason() {
            @Override
            public void onClickSeason(ImageView imageView, RecBean.RecDramaItem dramaItem) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(SeasonDetailActivity.newIntent(getContext(), dramaItem),
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(SeasonDetailActivity.newIntent(getContext(), dramaItem));
                }
            }
        };
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
