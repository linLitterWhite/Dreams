package com.looklook.xinghongfei.looklook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.SLZhuanyeAdapter;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;
import com.looklook.xinghongfei.looklook.bean.Zhuangye;
import com.looklook.xinghongfei.looklook.view.GridItemDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lin on 2017/2/25.
 */

public class SlZhuanye extends BaseFragment
{
    int index = 0;

    @BindView(R.id.luntan_recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.luntan_swiperefreshlayout)
    SwipeRefreshLayout mSwiper;


    private SLZhuanyeAdapter sLZhuanyeAdapter;

    private DscribeSchool school;
    public void setSchool(DscribeSchool school){
        this.school = school;
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_sl_environment, paramViewGroup, false);
        ButterKnife.bind(this,localView);
        return localView;
    }

    public void onViewCreated(View paramView, @Nullable Bundle paramBundle)
    {
        initDate();
        initView();
        initListener();
        super.onViewCreated(paramView, paramBundle);
    }

    private void initListener() {
    }

    private void initDate()
    {


    }

    private void initView()
    {
        this.sLZhuanyeAdapter = new SLZhuanyeAdapter(getActivity(), school.getSchool_zhuanye_list());
        mRecyclerview.addItemDecoration(new GridItemDividerDecoration(getContext(),R.dimen.divider_height, R.color.divider));
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerview.setAdapter(this.sLZhuanyeAdapter);
        this.mSwiper.setColorSchemeColors(new int[] {Color.RED,Color.BLUE,Color.GREEN });
        this.mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                SlZhuanye.this.index = 0;
                SlZhuanye.this.initDate();
                Toast.makeText(SlZhuanye.this.getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                SlZhuanye.this.mSwiper.setRefreshing(false);
            }
        });
    }

    public void hidProgressDialog()
    {
    }



    public void showError(String paramString)
    {
    }

    public void showProgressDialog()
    {
    }

    public void upListItem(ArrayList<Forum> paramArrayList)
    {
    }
}
