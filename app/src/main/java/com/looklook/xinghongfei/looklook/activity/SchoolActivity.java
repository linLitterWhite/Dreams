package com.looklook.xinghongfei.looklook.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.adapter.Find_tab_Adapter;
import com.looklook.xinghongfei.looklook.bean.School.DscribeSchool;
import com.looklook.xinghongfei.looklook.bean.School.School;
import com.looklook.xinghongfei.looklook.fragment.SlJianjie;
import com.looklook.xinghongfei.looklook.fragment.SlMessFragment;
import com.looklook.xinghongfei.looklook.fragment.SlZhuanye;
import com.looklook.xinghongfei.looklook.fragment.Slhuanjing;
import com.looklook.xinghongfei.looklook.huanxin.EaseConstant;
import com.looklook.xinghongfei.looklook.widget.MyStarMenu;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.ID;

public class SchoolActivity extends AppCompatActivity {

    Find_tab_Adapter fAdapter;
    ArrayList<Fragment> list_fragment;
    ArrayList<String> list_title;

    @BindView(R.id.luntan_FindFragment_pager)
    ViewPager lt_content;

    @BindView(R.id.addmenu)
    MyStarMenu starMenu;

    Slhuanjing slhuanjing;
    SlZhuanye slzhuanye;
    SlJianjie sljianjie;

    DscribeSchool school;
    String ID;

    @BindView(R.id.luntan_FindFragment_title)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ID = intent.getStringExtra("id");

        initDate();



        starMenu.setmMenuItemClickListener(new MyStarMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                if(MyApplication.IsLogin()){
                    switch (view.getId()){
                        case R.id.addmenu_addmenu_call:
                            Toast.makeText(SchoolActivity.this,"call",Toast.LENGTH_SHORT).show();
                            String number = school.getSchool().getSchool_phone();
                            //用intent启动拨打电话
                            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + number));
                            //启动
                            startActivity(phoneIntent);

                            break;
                        case R.id.addmenu_addmenu_message:
                            startActivity(new Intent(SchoolActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, school.getSchool().getSchool_name()));
                            break;
                    }
                }else {
                    Toast.makeText(SchoolActivity.this,"您尚未登录，请登录！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SchoolActivity.this,LoginActivity.class));
                }

            }
        });

    }
    private void initDate(){
        String URL = MyApplication.getURL()+"/SchoolNew/Andr_school_select";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                school = gson.fromJson(result,DscribeSchool.class);
                Log.e("School--info",result);
                initControls();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("School--info",volleyError.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                ArrayMap<String,String> map = new ArrayMap<>();
                map.put("id",ID);
                return map;
            }
        };

        MyApplication.getmQueue().add(request);

    }


    private void initControls()
    {
        slhuanjing = new Slhuanjing();
        slhuanjing.setSchool(school);
        slzhuanye = new SlZhuanye();
        slzhuanye.setSchool(school);
        sljianjie = new SlJianjie();
        sljianjie.setSchool(school);
        this.list_fragment = new ArrayList();
        this.list_fragment.add(sljianjie);
        this.list_fragment.add(slhuanjing);
        this.list_fragment.add(slzhuanye);
        this.list_title = new ArrayList();
        this.list_title.add("简介");
        this.list_title.add("环境");
        this.list_title.add("专业");
        this.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        this.tabLayout.addTab(this.tabLayout.newTab().setText((CharSequence)this.list_title.get(0)));
        this.tabLayout.addTab(this.tabLayout.newTab().setText((CharSequence)this.list_title.get(1)));
        this.tabLayout.addTab(this.tabLayout.newTab().setText((CharSequence)this.list_title.get(2)));
        this.fAdapter = new Find_tab_Adapter(getSupportFragmentManager(), this.list_fragment, this.list_title);
        this.lt_content.setAdapter(this.fAdapter);
        this.tabLayout.setupWithViewPager(this.lt_content);
    }


}
