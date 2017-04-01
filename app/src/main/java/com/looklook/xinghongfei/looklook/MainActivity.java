package com.looklook.xinghongfei.looklook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.looklook.xinghongfei.looklook.activity.AboutActivity;
import com.looklook.xinghongfei.looklook.activity.BaseActivity;
import com.looklook.xinghongfei.looklook.activity.ChatActivity;
import com.looklook.xinghongfei.looklook.activity.FatieActivity;
import com.looklook.xinghongfei.looklook.activity.LoginActivity;
import com.looklook.xinghongfei.looklook.bean.userinfo.Userinfo;
import com.looklook.xinghongfei.looklook.fragment.CollectForumFragment;
import com.looklook.xinghongfei.looklook.fragment.CollectSchoolFragment;
import com.looklook.xinghongfei.looklook.fragment.ForumFragment;
import com.looklook.xinghongfei.looklook.fragment.HomeFragmen;
import com.looklook.xinghongfei.looklook.fragment.MeForumFragment;
import com.looklook.xinghongfei.looklook.huanxin.EaseConstant;
import com.looklook.xinghongfei.looklook.huanxin.EaseConversationListFragment;
import com.looklook.xinghongfei.looklook.imagecut.Crop;
import com.looklook.xinghongfei.looklook.imagecut.FileUtils;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.net.UpImage;
import com.looklook.xinghongfei.looklook.presenter.implPresenter.MainPresenterImpl;
import com.looklook.xinghongfei.looklook.presenter.implView.IMain;
import com.looklook.xinghongfei.looklook.util.AnimUtils;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

import static android.R.attr.path;
import static android.os.Build.VERSION_CODES.M;
import static com.looklook.xinghongfei.looklook.R.id.login;
import static com.looklook.xinghongfei.looklook.R.id.up;
import static com.looklook.xinghongfei.looklook.R.id.username;
import static com.looklook.xinghongfei.looklook.R.string.file;

public class MainActivity extends BaseActivity implements IMain {

    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1458;

    private String mCurrentPhotoPath;

    private File mTempDir;

    Fragment home,forum,collectSchool,collectForum,myforum;
    EaseConversationListFragment conversationListFragment;
    private SwitchCompat mThemeSwitch;
    MenuItem currentMenuItem;
    Fragment currentFragment;
    private MainPresenterImpl IMainPresenter;

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.home)
    RelativeLayout home_fragment;
    @BindView(R.id.forum)
    RelativeLayout forum_fragment;
    @BindView(R.id.message)
    RelativeLayout message_fragment;
    @BindView(R.id.toolbartext)
    TextView toolbartext;

    private ImageView userimage;



    int nevigationId;


    int mainColor;
    long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        IMainPresenter = new MainPresenterImpl(this,this);
        IMainPresenter.getBackground();
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        mTempDir = new File( Environment.getExternalStorageDirectory(),"Temp");
        if(!mTempDir.exists()){
            mTempDir.mkdirs();
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            animateToolbar();
        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                login();

            }
        }).start();

        initView();

//        setStatusColor();
        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);




        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switchFragment(item.getItemId());
                drawer.closeDrawer(GravityCompat.END, true);
                return true;
            }
        });


        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}  // pressed
        };

        int[] color = new int[]{
                Color.BLACK,Color.BLACK};
        int[] iconcolor = new int[]{
                Color.GRAY,Color.BLACK};
        navView.setItemTextColor(new ColorStateList(state, color));
        navView.setItemIconTintList(new ColorStateList(state, iconcolor));


        RelativeLayout relativeLayout = (RelativeLayout) navView.getHeaderView(0);
        userimage = (ImageView) relativeLayout.findViewById(R.id.nave_userimage);
        if(SharePreferenceUtil.getUserIsLogin(this)){
            Glide.with(this).load(MyApplication.getURL()+SharePreferenceUtil.getUserInfo(MainActivity.this,"user_picture")).into(userimage);
            Log.e("userimage", "onCreate: "+SharePreferenceUtil.getUserInfo(MainActivity.this,"user_picture") );
        }

        final TextView username = (TextView) relativeLayout.findViewById(R.id.username);
        username.setText(SharePreferenceUtil.getUserInfo(this,"user_name"));
        TextView userphone = (TextView) relativeLayout.findViewById(R.id.userphone);
        username.setText(SharePreferenceUtil.getUserInfo(this,"user_name"));
        userphone.setText(SharePreferenceUtil.getUserInfo(this,"user_phone"));
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("修改用户名")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                username.setText(input);
                                ArrayMap<String,String> map = new ArrayMap<String,String>();
                                map.put("user_id",SharePreferenceUtil.getUserInfo(MainActivity.this,"id"));
                                map.put("user_name",input);
                                String URL = MyApplication.getURL()+"/users/Andr_user_xiugai_name";
                                MyVolleyNet volley = new MyVolleyNet();
                                volley.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("resultname", "respond: "+result );
                                        Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                        Toast.makeText(MainActivity.this,"修改",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                volley.postNet(URL,map);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        //设置navview的图片监听
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"ImageVIew",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("选择图片")
                        .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"ImageVIewshure",Toast.LENGTH_SHORT).show();
                                Crop.pickImage(MainActivity.this);
                            }
                        })
                        .setNegativeButton("拍摄", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"ImageVIewno",Toast.LENGTH_SHORT).show();
                                getImageFromCamera();
                            }
                        }).show();
            }
        });



        //主题变色
        MenuItem item = navView.getMenu().findItem(R.id.nav_theme);
        mThemeSwitch = (SwitchCompat) MenuItemCompat.getActionView(item).findViewById(R.id.view_switch);
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mThemeSwitch.setChecked(isChecked);
                if (isChecked) {
                    setThemeColor(Color.GREEN);
                } else {
                    setThemeColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
    }

    private void initView() {



        home = new HomeFragmen();
        forum = new ForumFragment();
        collectForum = new CollectForumFragment();
        collectSchool = new CollectSchoolFragment();
        myforum = new MeForumFragment();

        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.home,
                getResources().getString(R.string.home)).add(R.id.fragment_container, this.forum,
                getResources().getString(R.string.forum)).add(R.id.fragment_container, this.conversationListFragment,
                getResources().getString(R.string.message)).add(R.id.fragment_container, this.collectForum,
                getResources().getString(R.string.collectforum)).add(R.id.fragment_container, this.collectSchool,
                getResources().getString(R.string.collectschol)).add(R.id.fragment_container, this.myforum,
                getResources().getString(R.string.me)).commit();
        getSupportFragmentManager().beginTransaction().hide(this.forum).hide(this.conversationListFragment)
               .hide(this.collectForum)
                .hide(this.collectSchool).hide(this.myforum).commit();

        home_fragment.setOnClickListener(fragmetnClick);
        forum_fragment.setOnClickListener(fragmetnClick);
        message_fragment.setOnClickListener(fragmetnClick);

    }

    private View.OnClickListener fragmetnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchFragment(view.getId());
        }
    };

    private void setThemeColor(int color){
        getWindow().setStatusBarColor(color);
        toolbar.setBackgroundColor(color);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if((System.currentTimeMillis()- exitTime)>2000){
                Toast.makeText(MainActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                super.onBackPressed();
            }
        }
    }

    private void switchFragment(int id) {
        Log.d("id","text"+id);

        hideAllFragment();
        switch (id){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().show(home).commit();
                break;
            case R.id.forum:
                getSupportFragmentManager().beginTransaction().show(forum).commit();
                break;
            case R.id.message:
                getSupportFragmentManager().beginTransaction().show(conversationListFragment).commit();
                break;
            case R.id.likeschool:
                toolbartext.setText("感兴趣的学校");
                toolbartext.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().show(collectSchool).commit();
                break;
            case R.id.likefroum:
                toolbartext.setText("感兴趣的帖子");
                toolbartext.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().show(collectForum).commit();
                break;
            case R.id.myforum:
                toolbartext.setText("我发起的帖子");
                toolbartext.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().show(myforum).commit();
                break;
            case R.id.loginout:
                SharePreferenceUtil.putUserPwd("",this);
                SharePreferenceUtil.putUserIsLogin(false,MainActivity.this);
                EMClient.getInstance().logout(true);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                this.finish();
                break;
        }




    }

    private void hideAllFragment() {
        toolbartext.setVisibility(View.GONE);
        if (this.conversationListFragment != null || conversationListFragment.isVisible())
            getSupportFragmentManager().beginTransaction().hide(this.conversationListFragment).commit();
        if (this.home != null || home.isVisible())
            getSupportFragmentManager().beginTransaction().hide(this.home).commit();
        if (this.forum != null || forum.isVisible())
            getSupportFragmentManager().beginTransaction().hide(this.forum).commit();
        if (this.collectForum != null || forum.isVisible())
            getSupportFragmentManager().beginTransaction().hide(this.collectForum).commit();
        if (this.collectSchool != null || collectSchool.isVisible())
            getSupportFragmentManager().beginTransaction().hide(this.collectSchool).commit();
        if (myforum !=null || myforum.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(myforum).commit();
        }

    }

    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(500)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this)).start();
        }
        View amv = toolbar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.overshoot)).start();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_open:
                    drawer.openDrawer(GravityCompat.END);
                    break;
                case R.id.menu_about:
                    goAboutActivity();
                    break;
                case R.id.fatie:
                    startActivity(new Intent(MainActivity.this,FatieActivity.class));
                    break;
            }
            return true;
        }};

    private  void goAboutActivity(){
        Intent intent=new Intent(this, AboutActivity.class);
                this.startActivity(intent);
    }

    @Override
    public void getPic() {

    }

    //    when recycle view scroll bottom,need loading more date and show the more view.
    public interface LoadingMore {

        void loadingStart();

        void loadingfinish();
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
            userimage.setImageURI( Crop.getOutput(result));
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


    private void beginCrop(Uri source) {
        boolean isCircleCrop = true;
        String fileName = "Temp_" + String.valueOf( System.currentTimeMillis());
        File cropFile = new File( mTempDir, fileName);
        Uri outputUri = Uri.fromFile( cropFile);
        new Crop( source).output( outputUri).setCropType( isCircleCrop).start( this);
    }

    public  void login(){
        String oleusername = SharePreferenceUtil.getUserInfo(this,"user_phone");
        String olepwd = SharePreferenceUtil.getUserInfo(this,"user_pasword");
        if(oleusername!=null && !oleusername.isEmpty() && !olepwd.isEmpty() && olepwd != null){
            login(oleusername,olepwd);
        }else {
            SharePreferenceUtil.putUserIsLogin(false,MainActivity.this);
            //password.setText(olepwd);
        }

    }

    private void login(final String userphone, final String userpwd) {
         final String URL = MyApplication.getURL()+"/users/Andr_user_login?" ;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                Userinfo userInfo = gson.fromJson(result, Userinfo.class);
                if(userInfo.getResult().equals("SUCCESS")){
                    gotoEClicent(userphone,userpwd);
                    SharePreferenceUtil.putUserInfo(userInfo, MainActivity.this);
                    Log.e("Login",userInfo.getUser().toString());

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

    public void gotoEClicent(String userName,String userPwd){
        EMClient.getInstance().login(userName, userPwd, new EMCallBack()
        {
            public void onError(int paramAnonymousInt, String paramAnonymousString)
            {
                Log.d("Login", "登录聊天服务器失败！");
            }

            public void onProgress(int paramAnonymousInt, String paramAnonymousString)
            {
            }

            public void onSuccess()
            {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("Login", "登录聊天服务器成功！");

            }
        });
    }

    public  void updatauserimag(final String imagePath){
        String url = MyApplication.getURL()+"/users/Andr_img_upload_only";
        MyVolleyNet volleyNet = new MyVolleyNet();
        volleyNet.setCallback(new MyVolleyNet.Callback(){
            @Override
            public void respond(String result){
                Log.e("updateuseriamge", "respond: "+result );
                SharePreferenceUtil.putUserIsimage(imagePath,MainActivity.this);
                Log.e("userimage", "onCreate: "+SharePreferenceUtil.getUserInfo(MainActivity.this,"user_picture") );
            }
            @Override
            public void error(VolleyError volleyError){

            }
        });

        ArrayMap<String,String> map = new ArrayMap<>();
        map.put("user_id",SharePreferenceUtil.getUserInfo(MainActivity.this,"id"));
        Log.e("user_id","userid"+SharePreferenceUtil.getUserInfo(MainActivity.this,"id"));
        map.put("ord_picture",SharePreferenceUtil.getUserInfo(MainActivity.this,"user_picture"));
        map.put("new_picture",imagePath);
        volleyNet.postNet(url,map);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTempDir.exists()){
            FileUtils.deleteFile(mTempDir);
        }
    }

}



