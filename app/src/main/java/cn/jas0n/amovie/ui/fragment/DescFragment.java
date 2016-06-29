package cn.jas0n.amovie.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.VideoDetail;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/6/28
 * E-mail:chendong90x@gmail.com
 */
public class DescFragment extends LazyFragment implements View.OnClickListener {

    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.view_count)
    TextView mViewCount;
    @BindView(R.id.damnu_count)
    TextView mDanmuCount;
    @BindView(R.id.brief)
    TextView mBrief;
    @BindView(R.id.username)
    TextView mName;
    @BindView(R.id.create_time)
    TextView mCreateTime;
    @BindView(R.id.follow)
    TextView mFollow;

    private VideoDetail mDetail;

    public static DescFragment newInstance(VideoDetail detail){
        DescFragment fragment = new DescFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desc, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        mDetail = (VideoDetail) getArguments().getSerializable("detail");

        fillDetail();
        mFollow.setOnClickListener(this);
    }

    private void fillDetail() {
        Glide.with(this).load(mDetail.getData().getUserVideoView().getZimuzuView().getHeadImgUrl
                ()).centerCrop().crossFade().into(mAvatar);
        mTitle.setText(mDetail.getData().getUserVideoView().getTitle());
        mViewCount.setText(String.format(getString(R.string.play_count), mDetail.getData()
                .getUserVideoView().getPlayCount()));
        mDanmuCount.setText(String.format(getString(R.string.danmu_count), mDetail.getData()
                .getUserVideoView().getDanmuCount()));
        mBrief.setText(mDetail.getData().getUserVideoView().getBrief());
        mName.setText(mDetail.getData().getUserVideoView().getZimuzuView().getNickName());
        mCreateTime.setText(String.format(getString(R.string.created_at), mDetail.getData()
                .getUserVideoView().getCreateTimeStr()));
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    @Override
    public void onClick(View view) {
        if(view == mFollow){
            doFollow();
        }
    }

    private void doFollow() {

    }
}
