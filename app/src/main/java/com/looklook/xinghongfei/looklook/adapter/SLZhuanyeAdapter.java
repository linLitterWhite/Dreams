package com.looklook.xinghongfei.looklook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.Describezhuangye;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;

import java.util.List;

/**
 * Created by lin on 2017/3/16.
 */

public class SLZhuanyeAdapter extends RecyclerView.Adapter<SLZhuanyeAdapter.SlZhuanyegHolder> {

    public List< DscribeSchool.SchoolZhuanyeList> mData ;
    public Context mContext;

    public SLZhuanyeAdapter(Context context, List< DscribeSchool.SchoolZhuanyeList> mData) {
        this.mData = mData;
        mContext = context;
    }

    @Override
    public SlZhuanyegHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_zhuanye,parent,false);
        return new SlZhuanyegHolder(view);
    }

    @Override
    public void onBindViewHolder(SlZhuanyegHolder holder, int position) {
         DscribeSchool.SchoolZhuanyeList zhuangye = mData.get(position);
        holder.title.setText(zhuangye.getSchool_zhuanye_name());
        holder.context.setText(zhuangye.getSchool_zhuanye_jieshao());
        holder.context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, Describezhuangye.class));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SlZhuanyegHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView context;
        public SlZhuanyegHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_zhuagnye);
            context = (TextView) itemView.findViewById(R.id.zhuanye_context);
        }
    }
}
