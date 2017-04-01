package com.looklook.xinghongfei.looklook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.FatieActivity;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.userinfo.Userinfo;

import java.util.Map;

/**
 * Created by 蔡小木 on 2016/3/13 0013.
 */
public class SharePreferenceUtil{

    private SharePreferenceUtil(){}

    public static final String SHARED_PREFERENCE_NAME = "micro_reader";
    public static final String IMAGE_DESCRIPTION = "image_description";
    public static final String VIBRANT = "vibrant";
    public static final String MUTED = "muted";
    public static final String IMAGE_GET_TIME = "image_get_time";
    public static final String SAVED_CHANNEL = "saved_channel";

    public static final String USERINFO = "userinfo";


    public static boolean isRefreshOnlyWifi(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.pre_refresh_data), false);
    }

    public static boolean isChangeThemeAuto(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.pre_get_image), true);
    }

    public static boolean isImmersiveMode(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_status_bar), true);
    }

    public static boolean isChangeNavColor(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_nav_color), true);
    }

    public static boolean isUseLocalBrowser(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.pre_use_local), false);
    }

    public static int getNevigationItem(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(context.getString(R.string.nevigation_item), -1);
    }
    public static void putNevigationItem(Context context, int t){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.nevigation_item), t);
        editor.commit();
    }

    public static void putUserInfo(Userinfo user, Context context){
        Userinfo.User userinfo = user.getUser();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", userinfo.getId());
        editor.putString("user_name", userinfo.getUser_name());
        editor.putString("user_pasword", userinfo.getUser_pasword());
        editor.putString("user_phone", userinfo.getUser_phone());
        editor.putString("user_picture", userinfo.getUser_picture());
        editor.putString("user_sex", userinfo.getUser_sex());
        editor.putString("user_dengji", userinfo.getUser_dengji());
        editor.putBoolean("islogin", true);
        editor.putString("user_picture", userinfo.getUser_picture());
        editor.commit();
    }

    public static String getUserInfo(Context context, String item){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(item, null);
    }

    public static boolean getUserIsLogin(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("islogin", false);
    }
    public static void putUserIsLogin(boolean islogin, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("islogin", islogin);
        editor.commit();
    }
    public static void putUserPwd(String pwd, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_pasword", pwd);
        editor.commit();
    }

    public static void putUserIsimage(String path, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_picture", path);
        editor.commit();
    }



}
