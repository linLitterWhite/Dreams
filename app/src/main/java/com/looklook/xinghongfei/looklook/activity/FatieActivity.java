package com.looklook.xinghongfei.looklook.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.ForumDesribe;
import com.looklook.xinghongfei.looklook.bean.School.AllShool;
import com.looklook.xinghongfei.looklook.imagecut.Crop;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.net.UpImage;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.MainPresenterImpl;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.looklook.xinghongfei.looklook.R.id.user;

public class FatieActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1458;
    private String mCurrentPhotoPath;

    private File mTempDir;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.biaoti_text)
    TextView biaoti;
    @BindView(R.id.context_text)
    EditText context;
    @BindView(R.id.addimage)
    Button addimage;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.forum_mSpinner)
    Spinner mSpinner;

    String tiebaimage = "";


    List<String> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatie);
        ButterKnife.bind(this);
        if(!MyApplication.IsLogin()){
            Toast.makeText(FatieActivity.this,"您尚未登录，请登录！",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(FatieActivity.this,LoginActivity.class));
            this.finish();
        }
        mItems.add("请选择学校！");
        initDate();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, mItems);

        //绑定 Adapter到控件
        mSpinner .setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                Toast.makeText(FatieActivity.this, "你点击的是:"+mItems.get(pos), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        mTempDir = new File( Environment.getExternalStorageDirectory(),"Temp");
        addimage.setOnClickListener(this);
        ok.setOnClickListener(this);

    }
    private void initDate(){
        String url = MyApplication.getURL()+"/SchoolNew/Andr_school_show_onely";
        MyVolleyNet volleynet = new MyVolleyNet();
        volleynet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Gson gson = new Gson();
                List<AllShool> allschool = new Gson().fromJson(result, new TypeToken<ArrayList<AllShool>>(){}.getType());
                addschool(allschool);

            }
            @Override
            public void error(VolleyError volleyError){

            }
        });
        volleynet.getNet(url);
    }
    private void addschool(List<AllShool> list){
        for(int i = 0; i < list.size(); i++){
            mItems.add(list.get(i).getSchool_name());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addimage:
                new AlertDialog.Builder(FatieActivity.this)
                        .setTitle("选择图片")
                        .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(FatieActivity.this,"ImageVIewshure",Toast.LENGTH_SHORT).show();
                                Crop.pickImage(FatieActivity.this);
                            }
                        })
                        .setNegativeButton("拍摄", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(FatieActivity.this,"ImageVIewno",Toast.LENGTH_SHORT).show();
                                getImageFromCamera();
                            }
                        }).show();
                break;
            case R.id.ok:
                String url = MyApplication.getURL()+"/tieba_collection/Andr_addTieba";
                MyVolleyNet volleyNet = new MyVolleyNet();
                volleyNet.setCallback(new MyVolleyNet.Callback(){
                    @Override
                    public void respond(String result){
                        Log.e("fatie", "respond: "+result );
                        Toast.makeText(FatieActivity.this,"发帖成功！",Toast.LENGTH_SHORT).show();
                        FatieActivity.this.finish();

                    }
                    @Override
                    public void error(VolleyError volleyError){

                    }
                });
                ArrayMap<String,String> map = new ArrayMap<>();
                map.put("user_id",SharePreferenceUtil.getUserInfo(FatieActivity.this,"id"));
                map.put("tieba_title",biaoti.getText().toString().trim());
                map.put("tieba_content",context.getText().toString().trim());
                map.put("tieba_content_pictureurl",tiebaimage);
                volleyNet.postNet(url,map);


                break;

        }

    }

    protected void getImageFromCamera() {

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
            image.setImageURI( Crop.getOutput(result));
            File file = new File(Crop.getOutput(result).getPath());
            UpImage upImage = new UpImage();
            upImage.setCallback(new UpImage.Callbackz(){
                @Override
                public void callBack(String result){
                    updatauserimag(result);
                    //SharePreferenceUtil.putUserIsimage(userimagepath,MainActivity.this);
                }
            });
            UpImage.sendMultipart(file);
            //            mCircleView.setImageBitmap( getCircleBitmap(Crop.getOutput(result)));
        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(getActivity(), Crop.getError(result).getMessage(),
//                    Toast.LENGTH_SHORT).show();
        }
    }
    private void updatauserimag(String result){
        tiebaimage = result;
    }


    private void beginCrop(Uri source) {
        boolean isCircleCrop = false;
        String fileName = "Temp_" + String.valueOf( System.currentTimeMillis());
        File cropFile = new File( mTempDir, fileName);
        Uri outputUri = Uri.fromFile( cropFile);
        new Crop( source).output( outputUri).setCropType( isCircleCrop).start( this);
    }
}
