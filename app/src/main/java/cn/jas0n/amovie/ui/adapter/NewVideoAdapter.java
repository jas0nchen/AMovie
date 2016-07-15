package cn.jas0n.amovie.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.CateSortedBean;
import cn.jas0n.amovie.bean.Constant;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/7/15
 * E-mail:chendong90x@gmail.com
 */
public class NewVideoAdapter extends RecyclerArrayAdapter<CateSortedBean> {

    private Context mContext;

    public NewVideoAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getViewType(int position) {
        return getItem(position).getmType();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.TYPE_TITLE) {
            return new TitleHolder(Utils.getViewHolder(mContext, R.layout.layout_video_title,
                    parent, true));
        } else {
            return new VideoHolder(Utils.getViewHolder(mContext, R.layout.layout_video_item,
                    parent, false));
        }
    }

    class TitleHolder extends BaseViewHolder<CateSortedBean>{

        @BindView(R.id.category)
        TextView mTitle;
        @BindView(R.id.more)
        TextView mMore;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void setData(CateSortedBean data) {
            mTitle.setText(data.getmTitle());

        }
    }

    class VideoHolder extends BaseViewHolder<CateSortedBean> {

        @BindView(R.id.layout)
        RelativeLayout mLayout;
        @BindView(R.id.cover)
        ImageView mCover;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.view_count)
        TextView mViewCount;
        @BindView(R.id.comment_count)
        TextView mCommentCount;
        @BindView(R.id.avatar)
        CircleImageView mAvatar;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(CateSortedBean data) {
            RecBean.HotVideoItem video = data.getmVideo();
            Glide.with(mContext).load(video.getUrl()).centerCrop().crossFade().into
                    (mCover);
            mTitle.setText(video.getTitle());
            if(video.getAuthor() != null){
                mName.setText(video.getAuthor().getNickName());
                Glide.with(mContext).load(video.getAuthor().getHeadImgUrl()).centerCrop()
                        .crossFade().into(mAvatar);
            }
            mViewCount.setText(String.valueOf(video.getViewCount()));
            mCommentCount.setText(String.valueOf(video.getDanmuCount()));
        }
    }
}
