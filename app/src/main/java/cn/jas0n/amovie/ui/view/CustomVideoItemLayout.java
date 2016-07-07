package cn.jas0n.amovie.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.RecBean;
import cn.jas0n.amovie.interfaces.ClickVideo;
import cn.jas0n.amovie.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/6/25
 * E-mail:chendong90x@gmail.com
 */
public class CustomVideoItemLayout extends RelativeLayout {

    private Context mContext;
    private ImageView mCover;
    private CircleImageView mAvatar;
    private TextView mTitle;
    private TextView mUserName;
    private TextView mViewCount;
    private TextView mCommentCount;

    private int style = 1;

    private ClickVideo mClickVideo;
    private RecBean.HotVideoItem mVideo;

    public void setClickVideo(ClickVideo clickVideo){
        this.mClickVideo = clickVideo;
    }

    public CustomVideoItemLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomVideoItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomVideoItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_video_item, this, true);
        this.mCover = (ImageView) this.findViewById(R.id.cover);
        this.mAvatar = (CircleImageView) this.findViewById(R.id.avatar);
        this.mTitle = (TextView) this.findViewById(R.id.title);
        this.mUserName = (TextView) this.findViewById(R.id.name);
        this.mViewCount = (TextView) this.findViewById(R.id.view_count);
        this.mCommentCount = (TextView) this.findViewById(R.id.comment_count);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomVideoItemLayout);
        style = a.getInt(R.styleable.CustomVideoItemLayout_cover_style, 1);
        a.recycle();

        if (style == 0) {
            RelativeLayout.LayoutParams params = (LayoutParams) mCover.getLayoutParams();
            params.height = Utils.dip2px(mContext, 100f);
        }


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setVideo(final RecBean.HotVideoItem video){
        this.mVideo = video;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickVideo.onVideoClicked(mCover, video);
            }
        });
        setTitle(video.getTitle());
        setCover(video.getUrl());
        setViewCount(video.getViewCount());
        setCommentCount(video.getDanmuCount());
        if(video.getAuthor() != null){
            setUserName(video.getAuthor().getNickName());
            setAvatar(video.getAuthor().getHeadImgUrl());
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setCover(String url) {
        Glide.with(mContext).load(url).centerCrop().crossFade().into(mCover);
    }

    public void setAvatar(String url) {
        Glide.with(mContext).load(url).crossFade().centerCrop().into(mAvatar);
    }

    public void setUserName(String username) {
        mUserName.setText(username);
    }

    public void setViewCount(int count) {
        mViewCount.setText(String.valueOf(count));
    }

    public void setCommentCount(int count) {
        mCommentCount.setText(String.valueOf(count));
    }

    public ImageView getCover(){
        return mCover;
    }
}
