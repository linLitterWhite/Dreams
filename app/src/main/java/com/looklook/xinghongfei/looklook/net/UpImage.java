package com.looklook.xinghongfei.looklook.net;

import android.app.DownloadManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.bean.userinfo.UserImage;
import com.looklook.xinghongfei.looklook.bean.userinfo.Userinfo;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.path;
import static com.looklook.xinghongfei.looklook.R.id.user;

/**
 * Created by lin on 2017/3/26.
 */

public  class UpImage{

	private static OkHttpClient  mOkHttpClient = new OkHttpClient();


	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	private static Callbackz callbackz;

	public static void sendMultipart(File file){
		mOkHttpClient = new OkHttpClient();
		RequestBody requestBody = new MultipartBody.Builder()
			                          .setType(MultipartBody.FORM)
			                          .addFormDataPart("apkFile", "wangshu.jpg",
				                          RequestBody.create(MEDIA_TYPE_PNG, file))
			                          .build();

		final Request request = new Request.Builder()
			                  .header("Authorization", "Client-ID " + "...")
			                  .url(MyApplication.getURL()+"/users/img_upload")
			                  .post(requestBody)
			                  .build();

		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String string = response.body().string();
				Log.i("wangshu", string);
				UserImage userImage = new Gson().fromJson(string, UserImage.class);
				callbackz.callBack(userImage.getPath());
			}
		});
	}

	public void setCallback(Callbackz callback){
		this.callbackz = callback;
	}
	public interface  Callbackz{
		void callBack(String result);
	}


}
