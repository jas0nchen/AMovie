package cn.jas0n.amovie.ui.activity;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.ConstellationAdapter;
import cn.jas0n.amovie.adapter.DramaAdapter;
import cn.jas0n.amovie.adapter.ListDropDownAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.SeasonQueryBean;
import cn.jas0n.amovie.interfaces.ClickSeason;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.ui.swipebacklayout.SwipeBackActivity;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/7/11
 * E-mail:chendong90x@gmail.com
 */
public class SeasonQueryActivity extends SwipeBackActivity implements SwipeRefreshLayout
        .OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    private static final int ROWS = 21;
    @BindView(R.id.fake_status)
    View mFakeStatus;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drop_menu)
    DropDownMenu mMenu;

    private EasyRecyclerView mRecycler;

    private String headers[] = {"筛选", "类型", "状态"};
    private String filters[] = {"最热", "评分", "时间"};
    private String cates[] = {"全部", "喜剧", "科幻", "恐怖", "剧情", "魔幻", "罪案",
            "冒险", "动作", "悬疑"};
    private String status[] = {"全部", "连载中", "已完结"};

    private List<View> mPopViews = new ArrayList<>();
    private ListDropDownAdapter mFilterAdapter;
    private ListDropDownAdapter mStatusAdapter;
    private ConstellationAdapter mCateAdapter;
    private DramaAdapter mAdapter;

    private int page = 1;
    private int mCatePosition = 0;
    private String mCurrentFilter = "最热";
    private String mCurrentCate = "";
    private String mCurrentStatus = "";
    private List mData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_query);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        if (mToolbar != null)
            setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(getString(R.string.season_category));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mFakeStatus.getLayoutParams().height = Utils.getStatusBarHeight(this);
        }

        initFilterMenus();
    }

    private void initFilterMenus() {
        final ListView filterView = new ListView(this);
        filterView.setDividerHeight(0);
        mFilterAdapter = new ListDropDownAdapter(this, Arrays.asList(filters));
        filterView.setAdapter(mFilterAdapter);

        final View constellationView = getLayoutInflater().inflate(R.layout.layout_season_query_cate, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        mCateAdapter = new ConstellationAdapter(this, Arrays.asList(cates));
        constellation.setAdapter(mCateAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.closeMenu();
                if(mCatePosition == 0){
                    mCurrentCate = "";
                }else{
                    mCurrentCate = cates[mCatePosition];
                }
                mRecycler.setRefreshing(true, true);
            }
        });

        final ListView statusView = new ListView(this);
        statusView.setDividerHeight(0);
        mStatusAdapter = new ListDropDownAdapter(this, Arrays.asList(status));
        statusView.setAdapter(mStatusAdapter);

        mPopViews.add(filterView);
        mPopViews.add(constellationView);
        mPopViews.add(statusView);

        filterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentFilter = filters[i];
                mFilterAdapter.setCheckItem(i);
                mMenu.setTabText(filters[i]);
                mMenu.closeMenu();
                mRecycler.setRefreshing(true, true);
            }
        });

        statusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    mCurrentStatus = "";
                }else if(i == 1) {
                    mCurrentStatus = "false";
                }else if(i == 2) {
                    mCurrentStatus = "true";
                }
                mStatusAdapter.setCheckItem(i);
                mMenu.setTabText(status[i]);
                mMenu.closeMenu();
                mRecycler.setRefreshing(true, true);
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCateAdapter.setCheckItem(position);
                mMenu.setTabText(cates[position]);
                mCatePosition = position;
            }
        });

        initRecycler();
        mMenu.setDropDownMenu(Arrays.asList(headers), mPopViews, mRecycler);
    }

    private void initRecycler() {
        mRecycler = (EasyRecyclerView) getLayoutInflater().inflate(R.layout.layout_recycler, null);
        mRecycler.setRefreshingColorResources(R.color.colorAccent);
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecycler.setRefreshListener(this);
        mRecycler.getRecyclerView().setHasFixedSize(true);
        mAdapter = new DramaAdapter(this);
        mAdapter.setClickSeason(getClickSeason());
        mAdapter.setError(R.layout.view_error);
        mAdapter.setNoMore(R.layout.view_nomore);
        mAdapter.setMore(R.layout.view_more, this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setRefreshing(true, true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mData.clear();
        mAdapter.clear();
        AMovieService.builder().getApiService().getSeasonQuerys(page, ROWS, mCurrentFilter,
                mCurrentCate, mCurrentStatus).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SeasonQueryBean>() {
                    @Override
                    public void call(SeasonQueryBean seasonQueryBean) {
                        Logger.d(seasonQueryBean.toString());
                        page++;
                        mData = seasonQueryBean.getData().getResults();
                        mAdapter.addAll(mData);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    @Override
    public void onLoadMore() {
        AMovieService.builder().getApiService().getSeasonQuerys(page, ROWS, mCurrentFilter,
                mCurrentCate, mCurrentStatus).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SeasonQueryBean>() {
                    @Override
                    public void call(SeasonQueryBean seasonQueryBean) {
                        Logger.d(seasonQueryBean.toString());
                        page++;
                        mData.addAll(seasonQueryBean.getData().getResults());
                        mAdapter.addAll(seasonQueryBean.getData().getResults());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    protected ClickSeason getClickSeason() {
        return new ClickSeason() {
            @Override
            public void onClickSeason(ImageView imageView, RecBean.RecDramaItem dramaItem) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(SeasonDetailActivity.newIntent(SeasonQueryActivity.this, dramaItem),
                            ActivityOptions.makeSceneTransitionAnimation(SeasonQueryActivity.this, imageView,
                                    "transitionCover").toBundle());
                } else {
                    startActivity(SeasonDetailActivity.newIntent(SeasonQueryActivity.this, dramaItem));
                }
            }
        };
    }

}
