package com.looklook.xinghongfei.looklook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.LongSerializationPolicy;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.bean.userinfo.Userinfo;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    private static final String URL = MyApplication.getURL()+"/users/Andr_user_login?" ;
    @BindView(R.id.forgotpwd)
    TextView forgotpwd;
    @BindView(R.id.email_sign_in_button)
    Button login;
    @BindView(R.id.newuser)
    TextView newuser;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.email)
    AutoCompleteTextView username;

    String userName,userPwd;
    String userimage;

    @OnClick({R.id.email_sign_in_button})
    public void login(View paramView)
    {
        userName = username.getText().toString().trim();
        userPwd = password.getText().toString().trim();
        if(userName==null && userName.equals("")){
            Toast.makeText(LoginActivity.this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
            return;

        }
        if(userPwd==null && userPwd.equals("")){
            Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        login(userName,userPwd);

    }

    private void login(final String userphone, final String userpwd) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                Userinfo userInfo = gson.fromJson(result, Userinfo.class);
                if(userInfo.getResult().equals("SUCCESS")){
                    gotoEClicent(userphone,userpwd);
                    SharePreferenceUtil.putUserInfo(userInfo, LoginActivity.this);
                    Log.e("Login",userInfo.getUser().toString());
                    Intent localIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(localIntent);
                    LoginActivity.this.finish();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("user_phone",userphone);
                map.put("user_pasword",userpwd);
                return map;
            }
        };

        MyApplication.getmQueue().add(request);
    }

    @OnClick(R.id.newuser)
    public void newuser(View paramView)
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, RegisterActivity.class);
        startActivity(localIntent);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String oleusername = SharePreferenceUtil.getUserInfo(this,"user_phone");
        String olepwd = SharePreferenceUtil.getUserInfo(this,"user_pasword");
        if(oleusername!=null && !oleusername.isEmpty() && !olepwd.isEmpty() && olepwd != null){
            login(oleusername,olepwd);
        }else {
            username.setText(oleusername);
            //password.setText(olepwd);
        }


    }

    public void gotoEClicent(String userName,String userPwd){
        EMClient.getInstance().login(userName,userPwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("gotoEClicent", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("gotoEClicent", "登录聊天服务器失败！"+message+code);
            }
        });
    }
}





