package com.looklook.xinghongfei.looklook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.LuntanDescribeActivity;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.util.Help;

import java.util.List;

/**
 * Created by lin on 2017/2/25.
 */

public class SlEnvironmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int NOMAL_ITEM = 1;
    private static final int TYPE_LOADING_MORE = -1;
    private List<Forum> lunten_item_list;
    private Context mContext;
    boolean showLoadingMore;

    public SlEnvironmentAdapter(Context paramContext, List<Forum> paramList)
    {
        this.mContext = paramContext;
        this.lunten_item_list = paramList;
    }

    private void startTopnewsActivity(Forum paramLuntan, RecyclerView.ViewHolder paramViewHolder)
    {
        Intent localIntent = new Intent(this.mContext, LuntanDescribeActivity.class);
        localIntent.putExtra("luntanID", paramLuntan.getId());
        localIntent.putExtra("luntanTitle", paramLuntan.getTieba_title());
       // localIntent.putExtra("luntanImage", paramLuntan.getUser_pictuer_url());
        if (Build.VERSION.SDK_INT >= 21)
        {
            Activity localActivity = (Activity)this.mContext;
            Pair[] arrayOfPair1 = new Pair[2];
            arrayOfPair1[0] = new Pair(((LuntanViewHolder)paramViewHolder).lt_context_image, this.mContext.getString(R.string.transition_topnew));
            arrayOfPair1[1] = new Pair(((LuntanViewHolder)paramViewHolder).lt_item, this.mContext.getString(R.string.transition_topnew_linear));
            Pair[] arrayOfPair2 = Help.createSafeTransitionParticipants(localActivity, false, arrayOfPair1);
            ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)this.mContext, arrayOfPair2);
            this.mContext.startActivity(localIntent, localActivityOptionsCompat.toBundle());
            return;
        }
        this.mContext.startActivity(localIntent);
    }

    public void bindLoadingViewHold(LoadingMoreHolder holder, int paramInt)
    {
        holder.mProgressBar.setVisibility(showLoadingMore? View.VISIBLE : View.INVISIBLE);
    }

    public void bindViewHolderNormal(final LuntanViewHolder paramLuntanViewHolder, int paramInt)
    {
        final Forum localLuntan = (Forum)this.lunten_item_list.get(paramInt);
        paramLuntanViewHolder.user_Image.setImageResource(R.mipmap.huaruan);
        paramLuntanViewHolder.lt_context_image.setImageResource(R.mipmap.huaruan);
        paramLuntanViewHolder.user_name.setText(localLuntan.getUser_name());
        paramLuntanViewHolder.lt_item_title.setText(localLuntan.getTieba_title());
        paramLuntanViewHolder.lt_item_context.setText(localLuntan.getTieba_content());
        paramLuntanViewHolder.lt_item_time.setText(localLuntan.getTieba_time());
        //paramLuntanViewHolder.lt_item_comment_num.setText(String.valueOf(localLuntan.getLt_item_comment_num()));
        paramLuntanViewHolder.lt_item.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                SlEnvironmentAdapter.this.startTopnewsActivity(localLuntan, paramLuntanViewHolder);
            }
        });
    }

    public int getDataItemCount()
    {
        return this.lunten_item_list.size();
    }

    public int getItemCount()
    {
        return this.lunten_item_list.size();
    }

    public int getItemViewType(int paramInt)
    {
        if ((paramInt < getDataItemCount()) && (getDataItemCount() > 0))
            return 1;
        return -1;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
        switch (getItemViewType(paramInt))
        {
            case 0:
            default:
                return;
            case 1:
                bindViewHolderNormal((LuntanViewHolder)paramViewHolder, paramInt);
                return;
            case -1:
        }
        bindLoadingViewHold((LoadingMoreHolder)paramViewHolder, paramInt);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        switch (paramInt)
        {
            case 0:
            default:
                return null;
            case -1:
                return new LoadingMoreHolder(LayoutInflater.from(this.mContext).inflate(R.layout.infinite_loading, paramViewGroup, false));
            case 1:
        }
        return new LuntanViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.lt_item, paramViewGroup, false));
    }

    public class LoadingMoreHolder extends RecyclerView.ViewHolder
    {
        private ProgressBar mProgressBar;

        public LoadingMoreHolder(View localView)
        {
            super(localView);
            this.mProgressBar = ((ProgressBar)localView);
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