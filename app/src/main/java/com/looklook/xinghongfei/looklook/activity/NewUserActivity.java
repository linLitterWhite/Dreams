package com.looklook.xinghongfei.looklook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.looklook.xinghongfei.looklook.R;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

    }

    //这个方法要自己写
    protected void registerUser(String country, String phone) {
        //提交的资料将当作“通信录好友”功能的建议资料。
        String uid = "1223";
        String nickName = "yj";
        SMSSDK.submitUserInfo(uid, nickName , null, country, phone);
    }


}

