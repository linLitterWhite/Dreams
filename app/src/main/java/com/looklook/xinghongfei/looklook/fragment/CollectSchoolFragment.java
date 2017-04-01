package com.looklook.xinghongfei.looklook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.SchoolAdapter;
import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.bean.meizi.GankData;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.SchoolPresenterImpl;
import com.looklook.xinghongfei.looklook.presenter.implView.ISchoolFragment;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.looklook.xinghongfei.looklook.widget.SchoolSiftPopupWindow;
import com.looklook.xinghongfei.looklook.widget.WrapContentLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lin on 2017/3/9.
 */

public class CollectSchoolFragment extends BaseFragment implements ISchoolFragment {

    @BindView(R.id.school_swiper)
    SwipeRefreshLayout schoolSwiper;
    @BindView(R.id.school_recyclerview)
    RecyclerView schoolRecycler;

    private String userId ;


    SchoolPresenterImpl schoolPresenterImpl;
    int pager = 1;
    WrapContentLinearLayoutManager linearLayoutManager;
    SchoolAdapter schoolAdapter;
    RecyclerView.OnScrollListener recyclerScrollListener;

    private boolean loading;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this, view);
        userId = SharePreferenceUtil.getUserInfo(getContext(),"id");

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDate();
        initView();

    }

    private void initDate() {
        schoolPresenterImpl = new SchoolPresenterImpl(getContext(),this);
    }

    private void initView() {

        this.schoolSwiper.setColorSchemeColors(new int[]{Color.RED,Color.BLUE,Color.GREEN});
        this.schoolSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDate();
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                schoolSwiper.setRefreshing(false);
            }
        });




        schoolAdapter = new SchoolAdapter(getContext(),null);
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        intialListener();
        schoolRecycler.setLayoutManager(linearLayoutManager);
        schoolRecycler.setAdapter(schoolAdapter);
        schoolRecycler.setItemAnimator(new DefaultItemAnimator());
        schoolRecycler.addOnScrollListener(recyclerScrollListener);
        loadDate();
    }

    private void loadDate() {
        if(schoolAdapter.getItemCount()>0){
            schoolAdapter.clearDate();
        }
        schoolPresenterImpl.getCollectSchoolData();
    }

    private void intialListener() {
        recyclerScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0)//向下滚动
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    if(!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount ){
                        loading = true;
                        ++pager;
                        //loadMoreDate();
                    }
                }
            }

        };
    }

    private void loadMoreDate() {
        schoolAdapter.loadingStart();
        schoolPresenterImpl.getCollectSchoolData();

    }

    @Override
    public void updateSchoolData(List<DSchool> schools) {
        schoolAdapter.loadingfinish();
        loading = false;
        schoolAdapter.addItem(schools);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        schoolPresenterImpl.unsubcrible();
    }
    @Override
    public void showProgressDialog(){

    }
    @Override
    public void hidProgressDialog(){

    }
    @Override
    public void showError(String error){

    }
}
