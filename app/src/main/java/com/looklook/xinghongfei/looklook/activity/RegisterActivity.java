package com.looklook.xinghongfei.looklook.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.bean.userinfo.Userinfo;
import com.looklook.xinghongfei.looklook.imagecut.Crop;
import com.looklook.xinghongfei.looklook.imagecut.FileUtils;
import com.looklook.xinghongfei.looklook.net.UpImage;
import com.looklook.xinghongfei.looklook.util.Fileupdata;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import static com.looklook.xinghongfei.looklook.R.id.login;

/**
 * Created by lin on 2017/3/12.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1458;

    private static final String URL = MyApplication.getURL()+"/users/Andr_user_add?";

    @BindView(R.id.userimage_register)
    ImageView userimage_register;
    @BindView(R.id.username_register)
    AutoCompleteTextView username_register;
    @BindView(R.id.password_register)
    EditText userpwd_register;
    @BindView(R.id.password2)
    EditText password2;
    @BindView(R.id.register_button)
    Button register_button;

    private File mTempDir;
    private String mCurrentPhotoPath;

    private String userPhone;
    private String userName;
    private String userPwd;
    private String userimage = "";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        check_num();

        register_button.setOnClickListener(this);

        mTempDir = new File( Environment.getExternalStorageDirectory(),"Temp");
        if(!mTempDir.exists()){
            mTempDir.mkdirs();
        }
    }

    private void check_num() {
        //初始化sdk
        //SMSSDK.initSDK(this, "您的appkey", "您的appsecret");
        SMSSDK.initSDK(this, "1c05805726716", "8f93c4f54a928b2ce80ba52cbd0fabf7");

        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息, 提交的资料将当作“通信录好友”功能的建议资料。
                    registerUser(country, phone);
                    //在验证过后可以处理自己想要的操作
                    Log.v("TAG", "success...");
                }
            }
        });
        registerPage.show(RegisterActivity.this);

    }


    private void registerUser(String country, String phone) {
        userPhone = phone;
    }

    public void registerUser(){
        userName = username_register.getText().toString().trim();
        userPwd = userpwd_register.getText().toString().trim();
        final String userPwd2 = password2.getText().toString().trim();
        if(userName==null || userName.equals("")){
            Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPwd==null || userPwd.equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!userPwd.equals(userPwd2)){
            Toast.makeText(RegisterActivity.this,"两次输入密码不同！",Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
                String s = obj.get("result").getAsString();
                Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
                if(s.equals("注册成功")){

                    creatEMClient();
                    login(userPhone,userPwd);

                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    RegisterActivity.this.finish();
                }else {
                    Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("user_phone",userPhone);
                map.put("user_pasword",userPwd);
                map.put("user_name",userName);
                map.put("user_picture",userimage);
                return map;
            }
        };

        MyApplication.getmQueue().add(request);

    }

    private void creatEMClient() {
        //注册失败会抛出HyphenateException
        try {
            EMClient.getInstance().createAccount(userPhone, userPwd);//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.userimage_register)
    public void getUserImage(){
        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("选择图片")
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this,"ImageVIewshure",Toast.LENGTH_SHORT).show();
                        Crop.pickImage(RegisterActivity.this);
                    }
                })
                .setNegativeButton("拍摄", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this,"ImageVIewno",Toast.LENGTH_SHORT).show();
                        getImageFromCamera();
                    }
                }).show();

    }

    protected void getImageFromCamera() {
        // create Intent to take a picture and return control to the calling
        // application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = "Temp_camera" + String.valueOf( System.currentTimeMillis());
        File cropFile = new File( mTempDir, fileName);
        Uri fileUri = Uri.fromFile( cropFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        // name
        mCurrentPhotoPath = fileUri.getPath();
        // start the image capture Intent
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult( requestCode, resultCode, result);
        System.out.println( " onActivityResult result.getData() " + ((result ==null)?"null":result.getData()));
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == Crop.REQUEST_PICK) {
                beginCrop( result.getData());
            }
            else if(requestCode == Crop.REQUEST_CROP) {
                handleCrop( resultCode, result);
            }
            else if(requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                System.out.println( " REQUEST_CODE_CAPTURE_CAMEIA " + mCurrentPhotoPath);
                if(mCurrentPhotoPath != null) {
                    beginCrop( Uri.fromFile( new File( mCurrentPhotoPath)));
                }
            }
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            System.out.println(" handleCrop: Crop.getOutput(result) "+Crop.getOutput(result));
            userimage_register.setImageURI( Crop.getOutput(result));
            File file = new File(Crop.getOutput(result).getPath());
            UpImage upImage = new UpImage();
            upImage.setCallback(new UpImage.Callbackz(){
                @Override
                public void callBack(String result){
                    setUserImage(result);
                    Log.e("imagePath",result);
                }
            });
            UpImage.sendMultipart(file);

        } else if (resultCode == Crop.RESULT_ERROR) {

        }
    }


    private void beginCrop(Uri source) {
        boolean isCircleCrop = true;
        String fileName = "Temp_" + String.valueOf( System.currentTimeMillis());
         File cropFile = new File( mTempDir, fileName);
//        Fileupdata.submitUploadFile("http://vkako.com/Myproject/users/img_upload?apkFile",cropFile);
        Uri outputUri = Uri.fromFile( cropFile);
        new Crop( source).output( outputUri).setCropType( isCircleCrop).start( this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTempDir.exists()){
            FileUtils.deleteFile(mTempDir);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    public void setUserImage(String path){
        userimage = path;
    }


    private void login(final String userphone, final String userpwd) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Log.e("denglu", "onResponse: "+result );
                Gson gson = new Gson();
                Userinfo userInfo = gson.fromJson(result, Userinfo.class);
                if(userInfo.getResult().equals("SUCCESS")){
                    SharePreferenceUtil.putUserInfo(userInfo, RegisterActivity.this);
                    Log.e("Login",userInfo.getUser().toString());
                    Intent localIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    RegisterActivity.this.startActivity(localIntent);
                    RegisterActivity.this.finish();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> map = new HashMap<>();
                map.put("user_phone",userphone);
                map.put("user_pasword",userpwd);
                return map;
            }
        };

        MyApplication.getmQueue().add(request);
    }
}
