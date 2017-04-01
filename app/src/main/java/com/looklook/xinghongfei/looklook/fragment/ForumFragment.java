package com.looklook.xinghongfei.looklook.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.Find_tab_Adapter;
import com.looklook.xinghongfei.looklook.adapter.ForumAdapter;
import com.looklook.xinghongfei.looklook.adapter.ForumAdaptertest;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.config.Config;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.ForumPresenterImpl;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.SchoolPresenterImpl;
import com.looklook.xinghongfei.looklook.presenter.implView.IForumFragment;
import com.looklook.xinghongfei.looklook.presenter.implView.ISchoolFragment;
import com.looklook.xinghongfei.looklook.widget.SchoolSiftPopupWindow;
import com.looklook.xinghongfei.looklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.looklook.xinghongfei.looklook.R.id.query;
import static com.looklook.xinghongfei.looklook.R.id.view;


/**
 * Created by lin on 2017/2/10.
 */

public class ForumFragment extends BaseFragment implements IForumFragment{


    @BindView(R.id.school_swiper)
    SwipeRefreshLayout schoolSwiper;
    @BindView(R.id.school_recyclerview)
    RecyclerView forumRecycler;

    @BindView(R.id.query)
    EditText forumquery;
    @BindView(R.id.search_clear)
    ImageView forumclear;
    @BindView(R.id.headerquery)
    RelativeLayout headerquery;

    List<Forum> paramList = new ArrayList<>();

    ForumPresenterImpl forumPresenterImpl;

    int pager = 1;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    WrapContentLinearLayoutManager linearLayoutManager;
    ForumAdaptertest froumAdapter;
    RecyclerView.OnScrollListener recyclerScrollListener;


    protected InputMethodManager inputMethodManager;
    private boolean loading;

    View headerview;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        headerview = inflater.inflate(R.layout.forum_heard,container,false);
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        ButterKnife.bind(this, view);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDate();
        initView();



    }

    private void initDate() {
        forumPresenterImpl = new ForumPresenterImpl(getContext(),this);
    }

    private void initView() {

        this.schoolSwiper.setColorSchemeColors(new int[]{Color.RED,Color.BLUE,Color.GREEN});
        this.schoolSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDate();
                schoolSwiper.setRefreshing(false);
            }
        });


        froumAdapter = new ForumAdaptertest(getContext(),headerview);
        linearLayoutManager = new WrapContentLinearLayoutManager(getContext());
        intialListener();
        forumRecycler.setLayoutManager(linearLayoutManager);
        forumRecycler.setAdapter(froumAdapter);
        forumRecycler.setItemAnimator(new DefaultItemAnimator());
        forumRecycler.addOnScrollListener(recyclerScrollListener);
        loadDate();
    }



    private void loadDate() {
        if(froumAdapter.getItemCount()>0){
            froumAdapter.clearDate();
        }
        forumPresenterImpl.getForumData(pager);
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

                if(scrolledDistance > HIDE_THRESHOLD && controlsVisible){
                    pullhindanimi();
                    scrolledDistance = 0;
                    controlsVisible = false;
                }

                if(scrolledDistance < -HIDE_THRESHOLD && !controlsVisible){
                    pullshowanimi();
                    scrolledDistance = 0;
                    controlsVisible = true;
                }
                if((dy > 0 && controlsVisible) || (dy<0 && !controlsVisible))
                    scrolledDistance += dy;



            }

        };

        forumquery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    forumclear.setVisibility(View.VISIBLE);

                } else {
                    forumclear.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                quertForum(s);
            }
        });
        forumclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumquery.getText().clear();
                hideSoftKeyboard();
            }
        });

    }
    private void quertForum(CharSequence s){
        String url = MyApplication.getURL()+"tieba_collection/Andr_tieba_show_view";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("siftForum", "respond: "+result );
                Gson gson = new Gson();
                ArrayList<Forum> forums = new Gson().fromJson(result, new TypeToken<ArrayList<Forum>>(){}.getType());
                updateForumData(forums);
            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        ArrayMap<String,String> map = new ArrayMap<>();
        map.put("tiaojian",String.valueOf(s));
        volleyNet.postNet(url,map);
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void loadMoreDate() {
        froumAdapter.loadingStart();
        forumPresenterImpl.getForumData(pager);

    }
    


    
    public void updateForumData(List<Forum> forums){
        froumAdapter.clearDate();
        loading = false;
        froumAdapter.addItem(forums);
        
    }

    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        forumPresenterImpl.unsubcrible();
    }

    public void pullhindanimi(){
        AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(headerquery,"alpha",1.0f,0.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(headerquery, "translationY", 0f, 0f, 0f,-headerquery.getHeight());
        transAnimator.setDuration(500);
        animSetXY.play(alphaAnimation).with(transAnimator);
        animSetXY.start();

    }

    public void pullshowanimi(){
        AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(headerquery,"alpha",0.0f,1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(headerquery, "translationY", 0f, -headerquery.getHeight(), 0f,0f);
        transAnimator.setDuration(500);
        animSetXY.play(alphaAnimation).with(transAnimator);
        animSetXY.start();
    }

}
