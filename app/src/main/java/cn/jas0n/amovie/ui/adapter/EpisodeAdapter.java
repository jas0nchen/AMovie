package cn.jas0n.amovie.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.api.AMovieService;
import cn.jas0n.amovie.bean.Episode;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.bean.SeasonDetail;
import cn.jas0n.amovie.ui.view.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import cn.jas0n.amovie.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: Jas0n
 * Date: 2016/7/12
 * E-mail:chendong90x@gmail.com
 */
public class EpisodeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    private Context mContext;
    private SeasonDetail mDetail;
    private List<SeasonDetail.EpisodeBrief> mList;

    public EpisodeAdapter(Context context, SeasonDetail detail, List<SeasonDetail.EpisodeBrief>
            list) {
        this.mContext = context;
        this.mDetail = detail;
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = Utils.getViewHolder(mContext, R.layout.layout_season_des_header, parent,
                    true);
            return new Header(view);
        } else {
            View view = Utils.getViewHolder(mContext, R.layout.layout_episode_item, parent, false);
            return new Item(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            Header header = (Header) holder;
            if(mDetail.getData().getSeason().getAuthor() != null){
                header.mUserLayout.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mDetail.getData().getSeason().getAuthor().getHeadImgUrl
                        ()).crossFade().centerCrop().into(header.mAvatar);
                header.mName.setText(mDetail.getData().getSeason().getAuthor().getNickName());
                header.mCreateTime.setText(mDetail.getData().getSeason().getCreateTimeStr());
            }else{
                header.mUserLayout.setVisibility(View.GONE);
            }
            header.mTitle.setText(mDetail.getData().getSeason().getTitle());
            header.mTitleEn.setText(mDetail.getData().getSeason().getEnTitle());
            header.mBrief.setText(mDetail.getData().getSeason().getBrief());
            header.mViewCount.setText(String.format(mContext.getString(R.string.play_count),
                    mDetail.getData().getSeason().getViewCount()));
            header.mScore.setText(String.format(mContext.getString(R.string.score),
                    mDetail.getData().getSeason().getScore()));

            header.mFollow.setOnClickListener(this);
        } else {
            final Item item = (Item) holder;
            item.mTv.setText(mList.get(position - 1).getEpisode());
            item.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doOnClickEpisode(item.getAdapterPosition());
                }
            });
        }
    }

    private void doOnClickEpisode(final int position) {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("正在获取播放链接中...");
        dialog.show();

        AMovieService.builder().getApiService().getEpisode("high", mDetail.getData().getSeason()
                .getSid(),mList.get(position - 1).getSid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Episode>() {
                    @Override
                    public void call(Episode episode) {
                        dialog.dismiss();
                        JCFullScreenActivity.startActivity(mContext, episode.getData()
                                        .getM3u8().getUrl(), JCVideoPlayerStandardShowShareButtonAfterFullscreen.class,
                                mDetail.getData().getSeason().getTitle() + " E " + mList.get
                                        (position - 1).getEpisode());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.follow:

                break;
        }
    }

    class Header extends RecyclerView.ViewHolder {

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

        public Header(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class Item extends RecyclerView.ViewHolder {

        @BindView(R.id.card)
        CardView mCard;
        @BindView(R.id.tv)
        TextView mTv;

        public Item(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
