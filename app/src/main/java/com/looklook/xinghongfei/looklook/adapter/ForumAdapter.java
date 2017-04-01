package com.looklook.xinghongfei.looklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.LuntanDescribeActivity;
import com.looklook.xinghongfei.looklook.bean.Forum;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lin on 2017/2/25.
 */

public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<Forum> forumList = new ArrayList<>();
    private Context mContext;
    boolean showLoadingMore;

    private View HeadView;

    public ForumAdapter(Context paramContext,View headView)
    {
        this.mContext = paramContext;
        setHeaderView(headView);
    }
    private void setHeaderView(View headView) {
        this.HeadView = headView;
        notifyItemInserted(0);
    }

    private void startTopnewsActivity(Forum paramLuntan, ForumAdapter.LuntanViewHolder paramViewHolder)
    {
        Intent localIntent = new Intent(this.mContext, LuntanDescribeActivity.class);
        localIntent.putExtra("forumID", paramLuntan.getId());
        localIntent.putExtra("forumTitle", paramLuntan.getTieba_content());
        localIntent.putExtra("forumImageUrl",paramLuntan.getTieba_content_pictureurl().get(0).getImg());
        this.mContext.startActivity(localIntent);
    }



    public int getItemCount()
    {
        if(HeadView!=null){
            return this.forumList.size() + 1;
        }else {
            return this.forumList.size();
        }
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0 && HeadView != null){
            return 0;
        }
        return 1;
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int paramInt){
        final Forum forum;
        if(HeadView != null){
            forum = (Forum)this.forumList.get(paramInt - 1);

        }else {
            forum = (Forum)this.forumList.get(paramInt);
        }
        if(getItemViewType(paramInt) == 0){
            bindForumViewHeader((ForumAdapter.LuntanViewHolder)holder,forum);
        }else {
            bindForumDateViewHolder((ForumAdapter.LuntanViewHolder)holder,forum);
        }

    }

    public void bindForumViewHeader(final ForumAdapter.LuntanViewHolder holder, final Forum forum){
    }


    public void bindForumDateViewHolder(final ForumAdapter.LuntanViewHolder holder, final Forum forum){
        Log.e("formdsfs",forum.getUser_picture_url());
        Glide.with(mContext).load(MyApplication.getURL()+forum.getUser_picture_url()).into(holder.user_Image);
        Glide.with(mContext).load(MyApplication.getURL()+forum.getTieba_content_pictureurl().get(0).getImg()).into(holder.lt_context_image);
        Log.e("formdsfs", "bindForumDateViewHolder: "+forum.getTieba_content_pictureurl().get(0).getImg());
       // holder.lt_context_image.setImageResource(R.mipmap.huaruan);
        holder.user_name.setText(forum.getUser_name());
        holder.lt_item_title.setText(forum.getTieba_title());
        holder.lt_item_context.setText(forum.getTieba_content());
        holder.lt_item_time.setText(forum.getTieba_time());
        holder.lt_item_comment_num.setText(String.valueOf(forum.getIt_item_comment_num()));
        holder.lt_item.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                ForumAdapter.this.startTopnewsActivity(forum, holder);
            }
        });

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
            return new LuntanViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.lt_item, paramViewGroup, false));

    }
    public void clearDate(){
        forumList.clear();
        notifyDataSetChanged();
    }

    public void loadingStart(){
        if (showLoadingMore) return;
        showLoadingMore = true;
        notifyItemInserted(getItemCount());
    }

    public void addItem(List<Forum> forums){
        forumList.addAll(forums);
        notifyDataSetChanged();

    }


    public class ForumHeader extends RecyclerView.ViewHolder{

        public ForumHeader(View itemView){
            super(itemView);
        }
    }


    public class LuntanViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView lt_context_image;
        private LinearLayout lt_item;
        private TextView lt_item_comment_num;
        private TextView lt_item_context;
        private TextView lt_item_time;
        private TextView lt_item_title;
        private ImageView user_Image;
        private TextView user_name;

        public LuntanViewHolder(View localView)
        {
            super(localView);
                this.lt_item = ((LinearLayout)localView.findViewById(R.id.lt_item));
                this.user_Image = ((ImageView)localView.findViewById(R.id.lt_item_userimage));
                this.lt_context_image = ((ImageView)localView.findViewById(R.id.lt_item_Image));
                this.user_name = ((TextView)localView.findViewById(R.id.lt_item_username));
                this.lt_item_title = ((TextView)localView.findViewById(R.id.lt_item_title));
                this.lt_item_context = ((TextView)localView.findViewById(R.id.lt_item_context));
                this.lt_item_time = ((TextView)localView.findViewById(R.id.lt_item_time));
                this.lt_item_comment_num = ((TextView)localView.findViewById(R.id.lt_item_comment_num));

        }
    }
}