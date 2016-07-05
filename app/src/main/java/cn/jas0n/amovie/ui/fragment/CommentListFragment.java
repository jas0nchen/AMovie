package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.adapter.BaseAdapter;
import cn.jas0n.amovie.adapter.CommentListAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Comment;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.VideoDetail;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class CommentListFragment extends LazyFragment implements XRecyclerView.LoadingListener {

    protected View mView;
    @BindView(R.id.recycler_view)
    XRecyclerView mRecyclerView;
    @BindView(R.id.loading_view)
    AVLoadingIndicatorView mLoadingView;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private String mId;
    private int mType;
    protected BaseAdapter mAdapter;
    private Handler mHandler;
    private int page = 1;
    private List<Comment.Result> mComments;
    private boolean isRefresh;

    public static final int VIDEO = 0;
    public static final int SEASON = 1;

    public static CommentListFragment newInstance(String id, int type) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(String id, int type) {
        this.mId = id;
        this.mType = type;
        this.isRefresh = true;
        if (!isFirstLoad) {
            page = 1;
            if (mComments != null)
                mComments.clear();
            else
                mComments = new ArrayList<Comment.Result>();
            if (mAdapter != null)
                ((CommentListAdapter) mAdapter).setData(mComments);
            else {
                mAdapter = new CommentListAdapter(mComments, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }
            fetchCommentList();
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_comment_list, container, false);
        }
        ButterKnife.bind(this, mView);

        setupRecyclerView();
        return mView;
    }

    private void setupRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.mipmap.ic_down_arrow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setEmptyView(mEmptyView);

        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(this);
    }

    @Override
    protected void initData() {
        if (!isRefresh) {
            mId = getArguments().getString("id");
            mType = getArguments().getInt("type");
        }
        fetchCommentList();
    }

    private void fetchCommentList() {
        if (mType == VIDEO) {
            AMovieService.builder().getApiService().getCommentList(page, 20, mId, "", "", "", "", "")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            Logger.d(comment.toString());
                            mComments = comment.getData().getResults();
                            mAdapter = new CommentListAdapter(comment.getData().getResults(),
                                    getContext());
                            mRecyclerView.setAdapter(mAdapter);
                            page++;
                            hideLoadingView();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            hideLoadingView();
                        }
                    });
        } else if (mType == SEASON) {
            AMovieService.builder().getApiService().getCommentList(page, 20, "", "", "", "", mId, "")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            Logger.d(comment.toString());
                            mComments = comment.getData().getResults();
                            mAdapter = new CommentListAdapter(comment.getData().getResults(),
                                    getContext());
                            mRecyclerView.setAdapter(mAdapter);
                            page++;
                            hideLoadingView();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            hideLoadingView();
                        }
                    });
        }
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    protected void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onRefresh() {
        //nothing no refresh
    }

    @Override
    public void onLoadMore() {
        if (mType == VIDEO) {
            AMovieService.builder().getApiService().getCommentList(page, 20, mId, "", "", "", "", "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            Logger.d(comment.toString());
                            if (comment.getData().getResults().size() > 0) {
                                mComments.addAll(comment.getData().getResults());
                                ((CommentListAdapter) mAdapter).setData(mComments);
                                page++;
                            } else {
                                mRecyclerView.setLoadingMoreEnabled(false);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            mRecyclerView.loadMoreComplete();
                        }
                    });
        } else if (mType == SEASON) {
            AMovieService.builder().getApiService().getCommentList(page, 20, "", "", "", "", mId, "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            Logger.d(comment.toString());
                            if (comment.getData().getResults().size() > 0) {
                                mComments.addAll(comment.getData().getResults());
                                ((CommentListAdapter) mAdapter).setData(mComments);
                                page++;
                            } else {
                                mRecyclerView.setLoadingMoreEnabled(false);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            mRecyclerView.loadMoreComplete();
                        }
                    });
        }

    }
}
