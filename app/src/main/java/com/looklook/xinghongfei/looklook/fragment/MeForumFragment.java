package com.looklook.xinghongfei.looklook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.ForumAdaptertest;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.looklook.xinghongfei.looklook.widget.SpacesItemDecoration;
import com.looklook.xinghongfei.looklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lin on 2017/3/9.
 */

public class MeForumFragment extends BaseFragment
{
    @BindView(R.id.school_swiper)
    SwipeRefreshLayout schoolSwiper;
    @BindView(R.id.school_recyclerview)
    RecyclerView forumRecycler;

    ForumAdaptertest froumAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        this.schoolSwiper.setColorSchemeColors(new int[]{Color.RED,Color.BLUE,Color.GREEN});
        this.schoolSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDate();
                schoolSwiper.setRefreshing(false);
            }
        });


        froumAdapter = new ForumAdaptertest(getContext(), null);
        WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        forumRecycler.setLayoutManager(linearLayoutManager);
        forumRecycler.setAdapter(froumAdapter);
        forumRecycler.setItemAnimator(new DefaultItemAnimator());
        loadDate();
    }
    private void loadDate(){

        String url = MyApplication.getURL()+"/tieba_collection/Andr_tieba_by_userid";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("forumforschool",result);
                Gson gson = new Gson();
                ArrayList<Forum> forums = new Gson().fromJson(result, new TypeToken<ArrayList<Forum>>(){}.getType());
                if(forums.size()<1){
                    Toast.makeText(getContext(),"您还没有发过帖子，快去发帖吧！",Toast.LENGTH_SHORT).show();
                }
                updateForumData(forums);

            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        ArrayMap<String,String> map = new ArrayMap<>();
        map.put("userid", SharePreferenceUtil.getUserInfo(getContext(),"id"));
        volleyNet.postNet(url,map);


    }
    private void updateForumData(ArrayList<Forum> forums){
        froumAdapter.clearDate();
        froumAdapter.addItem(forums);
    }

}
