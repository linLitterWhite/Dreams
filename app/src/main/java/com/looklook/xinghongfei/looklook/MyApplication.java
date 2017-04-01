package com.looklook.xinghongfei.looklook;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import com.looklook.xinghongfei.looklook.huanxin.EaseUI;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


import cn.smssdk.SMSSDK;

/**
 * Created by xinghongfei on 16/8/12.
 */
public class MyApplication extends Application {

    public final static String TAG = "BaseApplication";

    private static MyApplication myApplication;


    public static RequestQueue mQueue;

    //public static final String URL = "http://172.16.142.184:8080/Myproject/";
    public static final String URL = "http://vkako.com/Myproject/";



    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static Application getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mQueue = Volley.newRequestQueue(myApplication);

        PushAgent mPushAgent = PushAgent.getInstance(myApplication);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                Log.e("umtoken", "onSuccess: "+deviceToken );
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("umtoken", "onSuccess: "+s+"-->"+s1 );

            }
        });

        SMSSDK.initSDK(this, "1c05805726716", "8f93c4f54a928b2ce80ba52cbd0fabf7");



        EMOptions localEMOptions = new EMOptions();
        localEMOptions.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, localEMOptions);
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this, localEMOptions);



    }




    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return myApplication;
    }

    public static String getURL() {
        return URL;
    }

    public static RequestQueue getmQueue(){
        return mQueue;
    }

    public static boolean IsLogin(){
        return SharePreferenceUtil.getUserIsLogin(myApplication);
    }




}
