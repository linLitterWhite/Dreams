package com.looklook.xinghongfei.looklook.adapter;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.MeiziPhotoDescribeActivity;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;
import com.looklook.xinghongfei.looklook.util.Help;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin on 2017/3/16.
 */

public class SLHuanjingAdapter extends RecyclerView.Adapter<SLHuanjingAdapter.SlHuanjingHolder> {

    public List<DscribeSchool.HuanjingPicture> mData ;
    public Context mContext;
    private List<Integer> mHeights;

    public SLHuanjingAdapter(Context context,List<DscribeSchool.HuanjingPicture> mData) {
        this.mData = mData;
        mContext = context;
        mHeights = new ArrayList<>();
    }

    @Override
    public SlHuanjingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_slhuanjing_item,parent,false);
        return new SlHuanjingHolder(view);
    }

    @Override
    public void onBindViewHolder(final SlHuanjingHolder holder, int position) {
        final DscribeSchool.HuanjingPicture hjImage = mData.get(position);

        // 随机高度, 模拟瀑布效果.
        if (mHeights.size() <= position) {
            mHeights.add((int) (250 + Math.random() * 300));
        }

        ViewGroup.LayoutParams lp = holder.slhjimage.getLayoutParams();
        lp.height = mHeights.get(position);

        holder.slhjimage.setLayoutParams(lp);

        Glide.with(mContext)
                .load(MyApplication.getURL()+hjImage.getImg())
                .into(holder.slhjimage);

        holder.slhutext.setText(hjImage.getJieshao());

        holder.slhjimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDescribeActivity(MyApplication.getURL()+hjImage.getImg(),holder);
            }
        });

    }

    private void startDescribeActivity(String url, RecyclerView.ViewHolder holder){

        Intent intent = new Intent(mContext, MeiziPhotoDescribeActivity.class);
        intent.putExtra("image",url);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            final android.support.v4.util.Pair<View, String>[] pairs = Help.createSafeTransitionParticipants
                    ((Activity) mContext, false,new android.support.v4.util.Pair<>(((SLHuanjingAdapter.SlHuanjingHolder)holder).slhjimage, mContext.getString(R.string.meizi)));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, pairs);
            mContext.startActivity(intent, options.toBundle());
        }else {
            mContext.startActivity(intent);
        }

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SlHuanjingHolder extends RecyclerView.ViewHolder{

        public ImageView slhjimage;
        public TextView slhutext;
        public SlHuanjingHolder(View itemView) {
            super(itemView);
            slhjimage = (ImageView) itemView.findViewById(R.id.slhuanjing_image);
            slhutext = (TextView) itemView.findViewById(R.id.slhuanjing_text);
        }
    }
}
