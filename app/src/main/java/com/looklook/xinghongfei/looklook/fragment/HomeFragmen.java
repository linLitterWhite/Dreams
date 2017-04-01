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
import com.looklook.xinghongfei.looklook.bean.School.School;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.SchoolPresenterImpl;
import com.looklook.xinghongfei.looklook.presenter.implView.ISchoolFragment;
import com.looklook.xinghongfei.looklook.widget.SchoolSiftPopupWindow;
import com.looklook.xinghongfei.looklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.looklook.xinghongfei.looklook.R.id.school;


/**
 * Created by lin on 2017/2/10.
 */

public class HomeFragmen extends BaseFragment implements ISchoolFragment {


    @BindView(R.id.school_swiper)
    SwipeRefreshLayout schoolSwiper;
    @BindView(R.id.school_recyclerview)
    RecyclerView schoolRecycler;

    ImageView schoolSift;
//    @BindView(R.id.school_condition)
//    TextView schoolCondition;

    SchoolPresenterImpl schoolPresenterImpl;

    int pager = 1;

    WrapContentLinearLayoutManager linearLayoutManager;
    SchoolAdapter schoolAdapter;
    RecyclerView.OnScrollListener recyclerScrollListener;

    private boolean loading;

    View headView;

    private PopupWindow popupWindow;

    SchoolSiftPopupWindow schoolSiftPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        headView = inflater.inflate(R.layout.school_item_headview,container,false);
        schoolSift = (ImageView) headView.findViewById(R.id.school_sift);
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this, view);

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
                HomeFragmen.this.loadDate();
                Toast.makeText(HomeFragmen.this.getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                HomeFragmen.this.schoolSwiper.setRefreshing(false);
            }
        });

        schoolSift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"text",Toast.LENGTH_SHORT).show();
                //.setText("本科");
                getPopupWindow();

            }
        });



        schoolAdapter = new SchoolAdapter(getContext(),headView);
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        intialListener();
        schoolRecycler.setLayoutManager(linearLayoutManager);
        schoolRecycler.setAdapter(schoolAdapter);
        schoolRecycler.setItemAnimator(new DefaultItemAnimator());
        schoolRecycler.addOnScrollListener(recyclerScrollListener);
        loadDate();
    }

    private void getPopupWindow()
    {
        schoolSiftPopupWindow = new SchoolSiftPopupWindow(getActivity());
        schoolSiftPopupWindow.setCallback(new SchoolSiftPopupWindow.Callback(){
            @Override
            public void callback(ArrayList<DSchool> schools){
                schoolAdapter.clearDate();
                updateSchoolData(schools);
            }
        });

        if (this.popupWindow == null){
            this.popupWindow = schoolSiftPopupWindow.getPopupWindow();
        }


        if(this.popupWindow.isShowing())
        this.popupWindow.dismiss();

        popupWindow.showAsDropDown(schoolSift);
    }

    private void loadDate() {
        if(schoolAdapter.getItemCount()>0){
            schoolAdapter.clearDate();
        }
        schoolPresenterImpl.getSchoolData(pager);
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
                        Toast.makeText(getActivity(),"Load",Toast.LENGTH_SHORT).show();
                        //loadMoreDate();
                    }
                }
            }

        };
    }

    private void loadMoreDate() {
        schoolAdapter.loadingStart();
        schoolPresenterImpl.getSchoolData(pager);

    }

    @Override
    public void updateSchoolData(List<DSchool> schools) {
        schoolAdapter.loadingfinish();
        loading = false;
        schoolAdapter.addItem(schools);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hidProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        schoolPresenterImpl.unsubcrible();
    }
}
