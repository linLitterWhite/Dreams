package com.looklook.xinghongfei.looklook.api;


import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.bean.School.School;
import com.looklook.xinghongfei.looklook.bean.zhihu.ZhihuDaily;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public abstract interface SchoolApi
{
    @GET("/api/4/news/latest")
    public abstract Observable<ZhihuDaily> getLastDaily();

    @GET("/mybishe/getSchool/getAllSchool")
    public abstract Observable<List<School>> getSchool();

    @GET("/Myproject/SchoolNew/Andr_school_show_view?page=0")
    public abstract Observable<List<DSchool>> getDSchool();

//    @GET("/Myproject/school_collection/Andr_collection_school_select?{userid}")
//    public abstract Observable<List<DSchool>> getCollectSchool(@Query("userid") String id);

    @FormUrlEncoded
    //urlencode
    @POST("/Myproject/school_collection/Andr_collection_school_select")
        //post提交
    public abstract Observable<List<DSchool>> getCollectSchool(@Field("userid") String id);
}
