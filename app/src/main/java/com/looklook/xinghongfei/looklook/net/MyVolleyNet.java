package com.looklook.xinghongfei.looklook.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.looklook.xinghongfei.looklook.MyApplication;

import java.util.Map;

/**
 * Created by lin on 2017/3/25.
 */

public class MyVolleyNet{

	private Callback mCallback;
	public void setCallback(Callback callback){
		this.mCallback = callback;
	}
	public void getNet(String URL){
		StringRequest request = new StringRequest(URL, new Response.Listener<String>(){
			@Override
			public void onResponse(String s){
				mCallback.respond(s);

			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				mCallback.error(volleyError);

			}
		});

		MyApplication.getmQueue().add(request);

	}

	public void postNet(String URL, final Map map){
		StringRequest request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>(){
			@Override
			public void onResponse(String s){
				mCallback.respond(s);

			}
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				mCallback.error(volleyError);
			}
		}){
			@Override
			protected Map<String,String> getParams() throws AuthFailureError{
				return map;
			}
		};

		MyApplication.getmQueue().add(request);


	}

	public interface Callback{
		void respond(String result);
		void error(VolleyError volleyError);
	}
}
