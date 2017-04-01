package com.looklook.xinghongfei.looklook.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.ForumForSchool;
import com.looklook.xinghongfei.looklook.activity.LoginActivity;
import com.looklook.xinghongfei.looklook.activity.SchoolActivity;
import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.map.MapActivity;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.looklook.xinghongfei.looklook.widget.ShowView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.os.Build.ID;
import static com.looklook.xinghongfei.looklook.R.id.school_forum;


/**
 * Created by lin on 2017/2/14.
 */

public class SchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

     final static String URL = "http://vkako.com/Myproject/school_collection/Andr_collection_school";
    private static final int HEADER = 0;
    private static final int NORMAL = 1;
    private static final int TYPE_LOADING_MORE = -1;

    private Context context;
    private View headView;
    private ArrayList<DSchool> schoolList = new ArrayList<DSchool>();

    private boolean showLoadingMore;

    public SchoolAdapter(Context context, View headView) {
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
            return new SchoolViewHolder(this.headView);
        if(viewType == TYPE_LOADING_MORE)
            return new LoadingMoreHolder(LayoutInflater.from(this.context).inflate(R.layout.
                    infinite_loading,parent,false));
        return new SchoolViewHolder(LayoutInflater.from(this.context).inflate(R.layout.school_item,
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
        bindViewHolderNormal((SchoolViewHolder)holder,i);


    }

    private void bindViewHolderNormal(final SchoolViewHolder holder, final int i) {
        final DSchool school = schoolList.get(i);
        holder.school_name.setText(school.getSchool_name());
        //holder.school_num.setText(String.valueOf(school.getId()));
        holder.school_type.setText(school.getSchool_type());
        holder.school_score.setText(school.getScore());  //分数线，后面更改
        holder.forum_count.setText(String.valueOf(school.getSchool_collect_num()));

        final String schoolAdd = school.getSchool_location();
        if(!schoolAdd.isEmpty()) {
            String[] add = schoolAdd.split("-");
            holder.school_address_text.setText(add[1]);
        }
        holder.school_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"论坛",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ForumForSchool.class);
                intent.putExtra("schoolid",school.getId());
                context.startActivity(intent);
            }
        });

        holder.school_collect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(MyApplication.IsLogin()){

                    //Toast.makeText(context,"收藏",Toast.LENGTH_SHORT).show();
                    String colletURL = MyApplication.getURL() + "/school_collection/Andr_collection_school";
                    MyVolleyNet volleyNet = new MyVolleyNet();
                    volleyNet.setCallback(new MyVolleyNet.Callback(){
                        @Override
                        public void respond(String result){
                            Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show();
                            holder.collect_image.setImageResource(R.mipmap.collect_select);
                            holder.school_collect.setClickable(false);
                        }
                        @Override
                        public void error(VolleyError volleyError){
                            Toast.makeText(context, "收藏失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Map<String,String> map = new ArrayMap<String,String>();
                    map.put("schoolid", school.getId());
                    map.put("userid", SharePreferenceUtil.getUserInfo(context, "id"));
                    volleyNet.postNet(colletURL, map);
                }else {
                    Toast.makeText(context,"你尚未登录，请登录！",Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });




        if(school.getSchool_picture().size()>0) {
            holder.showView.getImageUrls();
            for (int j = 0; j < school.getSchool_picture().size(); j++) {
                holder.showView.addImageUrls(MyApplication.getURL()+school.getSchool_picture().get(j).getSchool_picture());
            }
            holder.showView.initUI(context);
        }

        holder.showView.setSchoolId(school.getId());



        holder.school_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"地址",Toast.LENGTH_SHORT).show();
                Intent localIntent = new Intent(SchoolAdapter.this.context, MapActivity.class);
                localIntent.putExtra("shcool_longitude", school.getSchool_location_coordinate().get(0).getJingdu());
                localIntent.putExtra("school_latitude", school.getSchool_location_coordinate().get(0).getWeidu());
                SchoolAdapter.this.context.startActivity(localIntent);
            }
        });

        holder.school_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"分享",Toast.LENGTH_SHORT).show();
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
                share_intent.putExtra(Intent.EXTRA_TEXT, "推荐您一个好学校:" + school.getSchool_name());
                share_intent = Intent.createChooser(share_intent, "分享");
                SchoolAdapter.this.context.startActivity(share_intent);
            }
        });
        holder.showView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(context, SchoolActivity.class);
                intent.putExtra("id",school.getId());
                Log.e("tiaoxuexio", "onClick: "+school.getId() );
                SchoolAdapter.this.context.startActivity(intent);

            }
        });

        holder.school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, SchoolActivity.class);
                intent.putExtra("id",school.getId());
                Log.e("tiaoxuexio", "onClick: "+school.getId() );
                SchoolAdapter.this.context.startActivity(intent);
            }
        });

        holder.school.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //view.onInterceptTouchEvent();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        //view.dispatchTouchEvent()
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

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
            return schoolList.size()+1;
        }else {
            return schoolList.size();
        }
    }

    public void clearDate(){
        schoolList.clear();
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

    public void addItem(List<DSchool> list){
        schoolList.addAll(list);
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



    public class SchoolViewHolder extends RecyclerView.ViewHolder{

        ShowView showView;
        TextView school_name,school_num,school_type,school_score;
        TextView forum_count,school_address_text;
        LinearLayout school_forum,school_collect,school_address,school_share,school;
        ImageView collect_image;

        public SchoolViewHolder(View itemView) {
            super(itemView);
            if(itemView == SchoolAdapter.this.headView)
                return;

            showView = (ShowView) itemView.findViewById(R.id.school_image);
            school_name = (TextView) itemView.findViewById(R.id.school_name);
            //school_num = (TextView) itemView.findViewById(R.id.school_num);
            school_type = (TextView) itemView.findViewById(R.id.school_type);
            school_score = (TextView) itemView.findViewById(R.id.school_score);
            forum_count = (TextView) itemView.findViewById(R.id.forum_count);
            school_address_text = (TextView) itemView.findViewById(R.id.school_address_text);
            school_forum = (LinearLayout) itemView.findViewById(R.id.school_forum);
            school_collect = (LinearLayout) itemView.findViewById(R.id.school_collect);
            school_address = (LinearLayout) itemView.findViewById(R.id.school_address);
            school_share = (LinearLayout) itemView.findViewById(R.id.school_share);
            school = (LinearLayout) itemView.findViewById(R.id.school);
            collect_image = (ImageView) itemView.findViewById(R.id.collect_image);

        }
    }
}
