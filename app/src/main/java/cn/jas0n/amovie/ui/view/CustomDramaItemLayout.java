package cn.jas0n.amovie.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.jas0n.amovie.R;
import cn.jas0n.amovie.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/7/1
 * E-mail:chendong90x@gmail.com
 */
public class CustomDramaItemLayout extends RelativeLayout {

    private Context mContext;
    private ImageView mCover;
    private CircleImageView mAvatar;
    private TextView mName;
    private TextView mUpdate;
    private TextView mViewCount;
    private TextView mCommentCount;

    private int style = 1;

    public CustomDramaItemLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomDramaItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomDramaItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_drama_item, this, true);
        this.mCover = (ImageView) this.findViewById(R.id.cover);
        this.mName = (TextView) this.findViewById(R.id.name);
        this.mUpdate = (TextView) this.findViewById(R.id.update);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setName(String title) {
        mName.setText(title);
    }

    public void setCover(String url) {
        Glide.with(mContext).load(url).centerCrop().crossFade().into(mCover);
    }

    public void setUpdate(String update) {
        mUpdate.setText(update);
    }

    public ImageView getCover() {
        return mCover;
    }
}
