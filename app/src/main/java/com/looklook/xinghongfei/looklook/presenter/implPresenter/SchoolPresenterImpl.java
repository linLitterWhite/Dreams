package com.looklook.xinghongfei.looklook.presenter.implPresenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.activity.RegisterActivity;
import com.looklook.xinghongfei.looklook.api.ApiManage;
import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.bean.School.School;
import com.looklook.xinghongfei.looklook.config.Config;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.presenter.ISchoolPresenter;
import com.looklook.xinghongfei.looklook.presenter.implView.ISchoolFragment;
import com.looklook.xinghongfei.looklook.util.CacheUtil;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.looklook.xinghongfei.looklook.R.id.context;
import static com.looklook.xinghongfei.looklook.R.id.school;
import static com.looklook.xinghongfei.looklook.util.common.UIUtils.startActivity;

/**
 * Created by 蔡小木 on 2016/4/23 0023.
 */
public class SchoolPresenterImpl extends BasePresenterImpl implements ISchoolPresenter {

    private ISchoolFragment mSchoolFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();
    private Context mContext;

    public SchoolPresenterImpl(Context context, ISchoolFragment mSchoolFragment) {

        this.mSchoolFragment = mSchoolFragment;
        mCacheUtil = CacheUtil.get(context);
        mContext = context;
    }




//    @Override
//    public void getSchoolData(int t) {
//        mSchoolFragment.showProgressDialog();
//        Subscription subscription = ApiManage.getInstence().getSchoolService().getDSchool()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<DSchool>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mSchoolFragment.hidProgressDialog();
//                        mSchoolFragment.showError(e.getMessage());
//                        Log.d("mSchoolFragment",e.getMessage()+"Error");
//                    }
//
//                    @Override
//                    public void onNext(List<DSchool> schools) {
//                        Log.d("mSchoolFragment",schools.toString()+"success");
//                        mSchoolFragment.hidProgressDialog();
//                        mCacheUtil.put(Config.ZHIHU,gson.toJson(schools));
//                        mSchoolFragment.updateSchoolData(schools);
//                    }
//                });
//
//    }

//   public void getCollectSchoolData(String id) {
//        mSchoolFragment.showProgressDialog();
//        Subscription subscription = ApiManage.getInstence().getSchoolService().getCollectSchool(id)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Observer<List<DSchool>>() {
//                                            @Override
//                                            public void onCompleted() {
//
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//                                                mSchoolFragment.hidProgressDialog();
//                                                mSchoolFragment.showError(e.getMessage());
//                                                Log.d("mSchoolFragment",e.getMessage()+"Error");
//                                            }
//
//                                            @Override
//                                            public void onNext(List<DSchool> schools) {
//                                                Log.d("mSchoolFragment",schools.toString()+"success");
//                                                mSchoolFragment.hidProgressDialog();
//                                                mCacheUtil.put(Config.ZHIHU,gson.toJson(schools));
//                                                mSchoolFragment.updateSchoolData(schools);
//                                            }
//                                        });
//
//    }
    @Override
    public void getSchoolData(int t){
        String URL = MyApplication.getURL()+"/SchoolNew/Andr_school_show_view";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                ArrayList<DSchool> schools = new Gson().fromJson(result, new TypeToken<ArrayList<DSchool>>(){}.getType());
                mSchoolFragment.hidProgressDialog();
                mCacheUtil.put(Config.ZHIHU,gson.toJson(schools));
                mSchoolFragment.updateSchoolData(schools);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        MyApplication.getmQueue().add(request);

    }

    public void getCollectSchoolData(){
        String colletURL = MyApplication.getURL() + "/SchoolNew/Andr_school_shoucang_show";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("collectschool",result);
                Gson gson = new Gson();
                ArrayList<DSchool> schools = new Gson().fromJson(result, new TypeToken<ArrayList<DSchool>>(){}.getType());
                mSchoolFragment.hidProgressDialog();
                mCacheUtil.put(Config.ZHIHU,gson.toJson(schools));
                mSchoolFragment.updateSchoolData(schools);
            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        Map<String,String> map = new ArrayMap<String,String>();
        map.put("user_id", SharePreferenceUtil.getUserInfo(mContext, "id"));
//        Log.e("collectschool",SharePreferenceUtil.getUserInfo(mContext, "id"));
        volleyNet.postNet(colletURL, map);
    }

}
