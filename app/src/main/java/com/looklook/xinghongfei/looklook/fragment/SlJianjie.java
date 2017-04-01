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
import android.widget.TextView;
import android.widget.Toast;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.SlEnvironmentAdapter;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;
import com.looklook.xinghongfei.looklook.view.GridItemDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lin on 2017/2/25.
 */

public class SlJianjie extends BaseFragment
{

    @BindView(R.id.school_name)
    TextView schoolname;
    @BindView(R.id.school_type)
    TextView schooltypt;
    @BindView(R.id.school_introduce)
    TextView schoolintroduce;


    private DscribeSchool school;
    public void setSchool(DscribeSchool school){
        this.school = school;
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_sljianjie, paramViewGroup, false);
        ButterKnife.bind(this,localView);
        return localView;
    }

    public void onViewCreated(View paramView, @Nullable Bundle paramBundle)
    {

        initView();
        super.onViewCreated(paramView, paramBundle);
    }



    private void initView()
    {
        DscribeSchool.School school = this.school.getSchool();
        schoolname.setText( school.getSchool_name());
        final String schoolAdd = school.getSchool_location();
        if(!schoolAdd.isEmpty()) {
            String[] add = schoolAdd.split("-");
            schooltypt.setText(school.getSchool_type()+"·"+add[1]);
        }
        schoolintroduce.setText("    "+school.getSchool_introduce());
    }

}
