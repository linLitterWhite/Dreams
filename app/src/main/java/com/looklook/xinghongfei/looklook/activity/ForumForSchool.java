package com.looklook.xinghongfei.looklook.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.ForumAdapter;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.config.Config;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.ForumPresenterImpl;
import com.looklook.xinghongfei.looklook.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.looklook.xinghongfei.looklook.R.id.view;

/**
 * Created by lin on 2017/3/26.
 */

public class ForumForSchool extends AppCompatActivity{

	@BindView(R.id.school_swiper)
	SwipeRefreshLayout schoolSwiper;
	@BindView(R.id.school_recyclerview)
	RecyclerView forumRecycler;




	WrapContentLinearLayoutManager linearLayoutManager;
	ForumAdapter froumAdapter;

	String SchoolId ;


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmetn_forumforschool);
		ButterKnife.bind(this);
		initDate();
		initView();
	}


	private void initDate() {
		Intent intent = getIntent();
		SchoolId = intent.getStringExtra("schoolid");

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

		froumAdapter = new ForumAdapter(this,null);
		linearLayoutManager = new WrapContentLinearLayoutManager(this);
		forumRecycler.setLayoutManager(linearLayoutManager);
		forumRecycler.setAdapter(froumAdapter);
		forumRecycler.setItemAnimator(new DefaultItemAnimator());
		loadDate();
	}



	private void loadDate() {
		String url = MyApplication.getURL()+"/tieba_collection/Andr_tieba_by_schoolid";
		MyVolleyNet volleyNet = new MyVolleyNet();
		volleyNet.setCallback(new MyVolleyNet.Callback(){
			@Override
			public void respond(String result){
				Log.e("forumforschool",result);
				Gson gson = new Gson();
				ArrayList<Forum> forums = new Gson().fromJson(result, new TypeToken<ArrayList<Forum>>(){}.getType());
				updateForumData(forums);

			}
			@Override
			public void error(VolleyError volleyError){

			}
		});
		ArrayMap<String,String> map = new ArrayMap<>();
		map.put("schoolid",SchoolId);
		volleyNet.postNet(url,map);

	}
	private void updateForumData(ArrayList<Forum> forums){
		froumAdapter.clearDate();
		froumAdapter.addItem(forums);
	}


}
