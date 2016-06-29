package cn.jas0n.amovie.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.Author;
import cn.jas0n.amovie.bean.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class CommentListAdapter extends BaseAdapter {

    public CommentListAdapter(List mData, Context mContext) {
        super(mData, mContext);
    }

    public void setData(List data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(mContext).inflate(R.layout
                .layout_comment_item, parent, false));
    }

    @Override
    public void bindHolder(RecyclerView.ViewHolder holder, final int position) {
        CommentHolder commentHolder = (CommentHolder) holder;
        Comment.Result result = (Comment.Result) mData.get(position);
        Author author = result.getAuthor();
        if (author != null) {
            Glide.with(mContext).load(author.getHeadImgUrl()).crossFade().centerCrop()
                    .into(commentHolder.mAvatar);
            if (!TextUtils.isEmpty(author.getNickName()) && !TextUtils.equals("null", author
                    .getNickName()))
                commentHolder.mName.setText(author.getNickName());
            else
                commentHolder.mName.setText(mContext.getString(R.string.fake_name));
            if (!TextUtils.isEmpty(author.getLevel()) && !TextUtils.equals("null", author.getLevel()))
                commentHolder.mLevel.setText(String.format(mContext.getString(R.string.level), author
                        .getLevel()));
        }
        commentHolder.mContent.setText(result.getContent());
        commentHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Click " + position, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    class CommentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout mLayout;
        @BindView(R.id.avatar)
        CircleImageView mAvatar;
        @BindView(R.id.username)
        TextView mName;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.level)
        TextView mLevel;
        @BindView(R.id.reply)
        ImageView mReply;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
