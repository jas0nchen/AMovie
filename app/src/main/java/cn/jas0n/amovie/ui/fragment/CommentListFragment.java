package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.ui.adapter.CommentListAdapter;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Comment;
import cn.jas0n.amovie.util.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class CommentListFragment extends LazyFragment implements RecyclerArrayAdapter.OnLoadMoreListener {

    protected View mView;
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;

    private String mId;
    private int mType;
    protected RecyclerArrayAdapter mAdapter;
    private Handler mHandler;
    private int page = 1;
    private List<Comment.Result> mComments = new ArrayList<Comment.Result>();
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
            mComments.clear();
            mAdapter.clear();

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
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration dividerDecoration = new DividerDecoration(ContextCompat.getColor
                (getContext(), R.color.divider), Utils.dip2px(getContext(), 0.5f), Utils.dip2px
                (getContext(), 56f), Utils.dip2px(getContext(), 8f));
        dividerDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(dividerDecoration);

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new CommentListAdapter(getContext());
        mAdapter.setMore(R.layout.view_more, this);
        mAdapter.setNoMore(R.layout.view_nomore);
        mAdapter.setError(R.layout.view_error);
        mRecyclerView.setAdapter(mAdapter);
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
        mRecyclerView.setRefreshing(true);
        if (mType == VIDEO) {
            AMovieService.builder().getApiService().getCommentList(page, 20, mId, "", "", "", "", "")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            fillCommentsToAdapter(comment);
                            mRecyclerView.setRefreshing(false);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mRecyclerView.setRefreshing(false);
                            mRecyclerView.showError();
                            Logger.e(throwable.getMessage());
                        }
                    });
        } else if (mType == SEASON) {
            AMovieService.builder().getApiService().getCommentList(page, 20, "", "", "", "", mId, "")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            mRecyclerView.setRefreshing(false);
                            fillCommentsToAdapter(comment);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mRecyclerView.setRefreshing(false);
                            mRecyclerView.showError();
                            Logger.e(throwable.getMessage());
                        }
                    });
        }
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

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
                            addMoreComments(comment);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            mAdapter.pauseMore();
                        }
                    });
        } else if (mType == SEASON) {
            AMovieService.builder().getApiService().getCommentList(page, 20, "", "", "", "", mId, "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Comment>() {
                        @Override
                        public void call(Comment comment) {
                            addMoreComments(comment);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                            mAdapter.pauseMore();
                        }
                    });
        }
    }

    private void fillCommentsToAdapter(Comment comment) {
        mComments.addAll(comment.getData().getResults());
        mAdapter.addAll(mComments);
        page++;
    }

    private void addMoreComments(Comment comment) {
        mComments.addAll(comment.getData().getResults());
        mAdapter.addAll(comment.getData().getResults());
        page++;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerView != null)
            mRecyclerView.clear();
    }
}
