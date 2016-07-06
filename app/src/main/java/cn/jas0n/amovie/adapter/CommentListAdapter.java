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
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jas0n.amovie.R;
import cn.jas0n.amovie.bean.Author;
import cn.jas0n.amovie.bean.Comment;
import cn.jas0n.amovie.bean.RecBean;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Jas0n
 * Date: 2016/6/29
 * E-mail:chendong90x@gmail.com
 */
public class CommentListAdapter extends RecyclerArrayAdapter<Comment.Result> {

    private Context mContext;

    public CommentListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent);
    }

    class CommentHolder extends BaseViewHolder<Comment.Result> {

        LinearLayout mLayout;
        CircleImageView mAvatar;
        TextView mName;
        TextView mContent;
        TextView mLevel;
        ImageView mReply;

        public CommentHolder(ViewGroup parent) {
            super(parent, R.layout.layout_comment_item);
            mLayout = $(R.id.layout);
            mAvatar = $(R.id.avatar);
            mName = $(R.id.username);
            mContent = $(R.id.content);
            mLevel = $(R.id.level);
            mReply = $(R.id.reply);
        }

        @Override
        public void setData(Comment.Result data) {
            Author author = data.getAuthor();
            if (author != null) {
                Glide.with(mContext).load(author.getHeadImgUrl()).crossFade().centerCrop()
                        .into(mAvatar);
                if (!TextUtils.isEmpty(author.getNickName()) && !TextUtils.equals("null", author
                        .getNickName()))
                    mName.setText(author.getNickName());
                else
                    mName.setText(mContext.getString(R.string.fake_name));
                if (!TextUtils.isEmpty(author.getLevel()) && !TextUtils.equals("null", author.getLevel()))
                    mLevel.setText(String.format(mContext.getString(R.string.level), author
                            .getLevel()));
            }
            mContent.setText(data.getContent());
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Click ", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
