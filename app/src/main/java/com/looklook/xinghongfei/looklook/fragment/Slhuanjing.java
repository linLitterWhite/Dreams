package com.looklook.xinghongfei.looklook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.SLHuanjingAdapter;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;
import com.looklook.xinghongfei.looklook.bean.School.HuanjingPicture;
import com.looklook.xinghongfei.looklook.view.GridItemDividerDecoration;
import com.looklook.xinghongfei.looklook.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lin on 2017/2/25.
 */

public class Slhuanjing extends BaseFragment
{
    int index = 0;

    @BindView(R.id.luntan_recyclerview)
    RecyclerView mRecyclerview;
    List<DscribeSchool.HuanjingPicture> lt_list = new ArrayList();

    @BindView(R.id.luntan_swiperefreshlayout)
    SwipeRefreshLayout luntan_swiper;
    private SLHuanjingAdapter mAdapter;

    private DscribeSchool school;
    public void setSchool(DscribeSchool school){
        this.school = school;
    }
    private StaggeredGridLayoutManager sLayoutManger;

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
        initLinister();
        super.onViewCreated(paramView, paramBundle);
    }

    private void initDate()
    {

    }

    private void initView()
    {
        sLayoutManger = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        this.mAdapter = new SLHuanjingAdapter(getActivity(), school.getHuanjing_picture());
        this.mRecyclerview.addItemDecoration(new GridItemDividerDecoration(getContext(),R.dimen.divider_height, R.color.divider));
        this.mRecyclerview.setLayoutManager(sLayoutManger);
        this.mRecyclerview.setAdapter(this.mAdapter);
        mRecyclerview.addItemDecoration(new SpacesItemDecoration(13));
        this.luntan_swiper.setColorSchemeColors(new int[] {Color.RED,Color.BLUE,Color.GREEN });
        this.luntan_swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                Slhuanjing.this.index = 0;
                Slhuanjing.this.initDate();
                Toast.makeText(Slhuanjing.this.getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                luntan_swiper.setRefreshing(false);
            }
        });
    }

    public void hidProgressDialog()
    {
    }



    private void initLinister() {
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        
    }

    private void loadMoreData(boolean b) {
    }

    public void showError(String paramString)
    {
    }

    public void showProgressDialog()
    {
    }

    public void upListItem(ArrayList<HuanjingPicture> paramArrayList)
    {
    }
}
