package com.looklook.xinghongfei.looklook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.ForumDescribleAdapter;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.ForumDesribe;
import com.looklook.xinghongfei.looklook.bean.Lt_comment_item;
import com.looklook.xinghongfei.looklook.config.Config;
import com.looklook.xinghongfei.looklook.fragment.HomeFragmen;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.looklook.xinghongfei.looklook.view.GridItemDividerDecoration;
import com.looklook.xinghongfei.looklook.widget.InputfroumPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LuntanDescribeActivity  extends BaseActivity

{

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.backdrop)
    ImageView toolbarimage;
    private List<ForumDesribe> ltDescrible_item_list = new ArrayList();
    String luntanID;
    String luntanImage;
    String luntanTitle;

    @BindView(R.id.swiper)
    SwipeRefreshLayout schoolSwiper;
    @BindView(R.id.recycle_luntndescribe)
    RecyclerView recycler_lt_describle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collect_forum)
    ImageView collectforum;

    ForumDescribleAdapter localLuntanDescribleAdapter;

    private PopupWindow popupWindow;


    InputfroumPopupWindow inputfroumPopupWindow;


    private void expandImageAndFinish()
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            finishAfterTransition();
            return;
        }
        finish();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_luntan_describe);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        luntanID = intent.getStringExtra("forumID");
        luntanImage = intent.getStringExtra("forumImageUrl");
        luntanTitle = intent.getStringExtra("forumTitle");
        Log.e("luntanImage", "onCreate: "+luntanImage);

        initView();
        loadData();
    }


    public void loadData()
    {

        String url = MyApplication.getURL()+"/tiezione_collection/Andr_tiezi_show_view";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("forumdes",result);
                Gson gson = new Gson();
                ltDescrible_item_list = new Gson().fromJson(result, new TypeToken<ArrayList<ForumDesribe>>(){}.getType());
                 localLuntanDescribleAdapter.update(ltDescrible_item_list);


            }
            @Override
            public void error(VolleyError volleyError){

            }
        });

        ArrayMap<String,String> map = new ArrayMap<>();
        map.put("tieba_id",luntanID);
        volleyNet.postNet(url,map);

    }

    private void initView()
    {
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleMargin(20, 20, 0, 10);
        this.toolbar.setNavigationIcon(R.mipmap.dream1);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                LuntanDescribeActivity.this.expandImageAndFinish();
            }
        });
        this.collapsingToolbar.setTitle(this.luntanTitle);
        if(luntanImage != null || !luntanImage.equals("meiyou")){
            toolbarimage.setImageResource(R.mipmap.first);
        }else {
            Glide.with(LuntanDescribeActivity.this).load(MyApplication.getURL()+luntanImage).into(toolbarimage);
        }

        this.schoolSwiper.setColorSchemeColors(new int[]{Color.RED,Color.BLUE,Color.GREEN});
        this.schoolSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url = MyApplication.getURL()+"/tiezione_collection/Andr_tiezi_show_view";
                MyVolleyNet volleyNet = new MyVolleyNet();
                volleyNet.setCallback(new MyVolleyNet.Callback(){
                    @Override
                    public void respond(String result){
                        Log.e("forumdes",result);
                        Gson gson = new Gson();
                        ltDescrible_item_list = new Gson().fromJson(result, new TypeToken<ArrayList<ForumDesribe>>(){}.getType());
                        localLuntanDescribleAdapter.refresh(ltDescrible_item_list);


                    }
                    @Override
                    public void error(VolleyError volleyError){

                    }
                });

                ArrayMap<String,String> map = new ArrayMap<>();
                map.put("tieba_id",luntanID);

                volleyNet.postNet(url,map);

                schoolSwiper.setRefreshing(false);
            }
        });

        collectforum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String url = MyApplication.getURL()+"/Tieba_collection/Andr_tieba_collect";
                MyVolleyNet volleyNet = new MyVolleyNet();
                volleyNet.setCallback(new MyVolleyNet.Callback(){
                    @Override
                    public void respond(String result){
                        Log.e("colletforum",result);
                        Toast.makeText(LuntanDescribeActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                        collectforum.setImageResource(R.mipmap.collect_select);
                        collectforum.setClickable(false);
                    }
                    @Override
                    public void error(VolleyError volleyError){

                    }
                });
                ArrayMap<String,String> map = new ArrayMap<>();
                map.put("tiebaid",luntanID);
                map.put("userid", SharePreferenceUtil.getUserInfo(LuntanDescribeActivity.this,"id"));
                volleyNet.postNet(url,map);


            }
        });

        localLuntanDescribleAdapter = new ForumDescribleAdapter(this, this.ltDescrible_item_list);
        this.recycler_lt_describle.addItemDecoration(new GridItemDividerDecoration(this,R.dimen.divider_height, R.color.divider));
        this.recycler_lt_describle.setHasFixedSize(true);
        this.recycler_lt_describle.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_lt_describle.setAdapter(localLuntanDescribleAdapter);
    }

    public void hidProgressDialog()
    {
    }



    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.lt_describle_menu, paramMenu);
        return true;
    }



    public Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.edit_comment:
                    getPopupWindow();
                    popupWindow.showAsDropDown(toolbar);

                    Toast.makeText(LuntanDescribeActivity.this,"回复楼主",Toast.LENGTH_SHORT).show();



                    break;
            }
            return true;
        }};





//    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
//    {
//        switch (paramMenuItem.getItemId())
//        {
//            default:
//            case 2131689896:
//            case 2131689895:
//        }
//        while (true)
//        {
//            return super.onOptionsItemSelected(paramMenuItem);
//            Toast.makeText(this, "edit_comment", Toast.LENGTH_SHORT).show();
//            continue;
//        }
//    }

    public void showError(String paramString)
    {
    }

    public void showProgressDialog()
    {
    }

    public void upListItem(ForumDesribe paramLuntanDesribe)
    {
    }

    private void getPopupWindow()
    {

        if (this.popupWindow == null){
            inputfroumPopupWindow = new InputfroumPopupWindow(LuntanDescribeActivity.this);
            popupWindow = inputfroumPopupWindow .getPopupWindow();
            inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                @Override
                public void comment(String commentcontent){
                    String url = MyApplication.getURL()+"/tiezione_collection/Andr_tiezi_add";
                    MyVolleyNet volleyNet = new MyVolleyNet();
                    volleyNet.setCallback(new MyVolleyNet.Callback(){
                        @Override
                        public void respond(String result){
                            Log.e("comment",result);
                            Toast.makeText(LuntanDescribeActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                        @Override
                        public void error(VolleyError volleyError){

                        }
                    });
                    ArrayMap<String,String> map = new ArrayMap<>();
                    map.put("id",luntanID);
                    map.put("user_id", SharePreferenceUtil.getUserInfo(LuntanDescribeActivity.this,"id"));
                    map.put("neirong",commentcontent);
                    volleyNet.postNet(url,map);

                }
            });
        } else if(this.popupWindow.isShowing()){
            this.popupWindow.dismiss();}
    }
}
