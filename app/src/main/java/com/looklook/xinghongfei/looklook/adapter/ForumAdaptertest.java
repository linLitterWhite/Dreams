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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.LuntanDescribeActivity;
import com.looklook.xinghongfei.looklook.bean.Forum;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by lin on 2017/2/14.
 */

public class ForumAdaptertest extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

     final static String URL = "http://vkako.com/Myproject/school_collection/Andr_collection_school";
    private static final int HEADER = 0;
    private static final int NORMAL = 1;
    private static final int TYPE_LOADING_MORE = -1;

    private Context context;
    private View headView;
    private ArrayList<Forum> forumList = new ArrayList<>();

    private boolean showLoadingMore;

    public ForumAdaptertest(Context context, View headView) {
        this.context = context;
        setHeaderView(headView);


    }

    private void setHeaderView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if((this.headView != null) && viewType == 0)
            return new LuntanViewHolder(this.headView);
        if(viewType == TYPE_LOADING_MORE)
            return new LoadingMoreHolder(LayoutInflater.from(this.context).inflate(R.layout.
                    infinite_loading,parent,false));
        return new LuntanViewHolder(LayoutInflater.from(this.context).inflate(R.layout.lt_item,
                parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HEADER:
                return;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((LoadingMoreHolder)holder,position);
                break;
            case NORMAL:
                break;
        }
        int i = getRealPosition(holder);
        bindViewHolderNormal((LuntanViewHolder)holder,i);


    }

    private void bindViewHolderNormal(final ForumAdaptertest.LuntanViewHolder holder, final int paramInt) {
        final Forum forum = forumList.get(paramInt);
        Log.e("formdsfs",forum.getUser_picture_url());
        Glide.with(context).load(MyApplication.getURL()+forum.getUser_picture_url()).into(holder.user_Image);
        if(forum.getTieba_content_pictureurl().size()>0){
            String img = forum.getTieba_content_pictureurl().get(0).getImg();
            if(img != null && !img.equals("")){
                holder.lt_context_image.setVisibility(View.VISIBLE);
                Log.e("formdsfs_you", "bindForumDateViewHolder: " + img);
                Glide.with(context).load(MyApplication.getURL() + forum.getTieba_content_pictureurl().get(0).getImg()).into(holder.lt_context_image);
            }else{
                Log.e("formdsfs_meiyou", "bindForumDateViewHolder: " + img);
                holder.lt_context_image.setVisibility(View.GONE);
            }
        }else {
            //Log.e("formdsfs_meiyou", "bindForumDateViewHolder: " + img);
            holder.lt_context_image.setVisibility(View.GONE);
        }

        holder.user_name.setText(forum.getUser_name());
        holder.lt_item_title.setText(forum.getTieba_title());
        holder.lt_item_context.setText(forum.getTieba_content());
        holder.lt_item_time.setText(forum.getTieba_time());
        holder.lt_item_comment_num.setText(String.valueOf(forum.getIt_item_comment_num()));
        holder.lt_item.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                ForumAdaptertest.this.startTopnewsActivity(forum, holder);
            }
        });

    }

    private void startTopnewsActivity(Forum paramLuntan, ForumAdaptertest.LuntanViewHolder paramViewHolder)
    {
        Intent localIntent = new Intent(this.context, LuntanDescribeActivity.class);
        localIntent.putExtra("forumID", paramLuntan.getId());
        localIntent.putExtra("forumTitle", paramLuntan.getTieba_title());
        if(paramLuntan.getTieba_content_pictureurl().size()>0){
            String img = paramLuntan.getTieba_content_pictureurl().get(0).getImg();
            if(img != null && !img.equals("")){
                localIntent.putExtra("forumImageUrl",img);
            }else{
                localIntent.putExtra("forumImageUrl","meiyou");
            }
        }else {
            localIntent.putExtra("forumImageUrl","meiyou");

        }


        this.context.startActivity(localIntent);
    }


    private void bindLoadingViewHold(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore? View.VISIBLE : View.INVISIBLE);
    }

    public int getRealPosition(RecyclerView.ViewHolder paramVIewHolder){
        int i = paramVIewHolder.getLayoutPosition();
        if(this.headView == null){
            return i;
        }
        return i-1;
    }

    @Override
    public int getItemCount() {
        if(headView != null){
            return forumList.size()+1;
        }else {
            return forumList.size();
        }
    }



    public void clearDate(){
        forumList.clear();
        notifyDataSetChanged();
    }

    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }


    @Override
    public void loadingStart() {
        if (showLoadingMore) return;
        showLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingfinish() {
        if (!showLoadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        showLoadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void addItem(List<Forum> list){
        forumList.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && headView != null){
            return HEADER;
        }else if(position < this.getItemCount() && this.getItemCount() > 0)
            return NORMAL;

        return TYPE_LOADING_MORE;
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingMoreHolder(View itemView) {
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
            if(localView == headView){
                return ;
            }
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
