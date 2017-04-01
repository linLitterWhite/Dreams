package com.looklook.xinghongfei.looklook.presenter.implPresenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.config.Config;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.presenter.implView.IForumFragment;
import com.looklook.xinghongfei.looklook.util.CacheUtil;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 蔡小木 on 2016/4/23 0023.
 */
public class ForumPresenterImpl extends BasePresenterImpl  {

    private IForumFragment mForumFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();
    private Context mContext;

    public ForumPresenterImpl(Context context, IForumFragment mForumFragment) {

        this.mForumFragment = mForumFragment;
        mCacheUtil = CacheUtil.get(context);
        mContext = context;
    }

    public void getForumData(int t){
        String URL = MyApplication.getURL()+"tieba_collection/Andr_tieba_show_view";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("collectschool",result);
                Gson gson = new Gson();
                ArrayList<Forum> forums = new Gson().fromJson(result, new TypeToken<ArrayList<Forum>>(){}.getType());
                mCacheUtil.put(Config.ZHIHU,gson.toJson(forums));
                mForumFragment.updateForumData(forums);
            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        //Log.e("collectschool",SharePreferenceUtil.getUserInfo(mContext, "id"));
        volleyNet.getNet(URL);

    }

    public void getCollectForumData(){
        String colletURL = MyApplication.getURL() + "/SchoolNew/Andr_school_shoucang_show";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("collectschool",result);
                Gson gson = new Gson();
                ArrayList<Forum> forums = new Gson().fromJson(result, new TypeToken<ArrayList<Forum>>(){}.getType());
                mCacheUtil.put(Config.ZHIHU,gson.toJson(forums));
                mForumFragment.updateForumData(forums);
            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        Map<String,String> map = new ArrayMap<String,String>();
        map.put("user_id", SharePreferenceUtil.getUserInfo(mContext, "id"));
        Log.e("collectschool",SharePreferenceUtil.getUserInfo(mContext, "id"));
        volleyNet.postNet(colletURL, map);
    }

}
