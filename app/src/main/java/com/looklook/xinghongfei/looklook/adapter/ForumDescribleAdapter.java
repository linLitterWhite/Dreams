package com.looklook.xinghongfei.looklook.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.LuntanDescribeActivity;
import com.looklook.xinghongfei.looklook.bean.Forum;
import com.looklook.xinghongfei.looklook.bean.ForumDesribe;
import com.looklook.xinghongfei.looklook.bean.Lt_comment_item;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;
import com.looklook.xinghongfei.looklook.view.Pop;
import com.looklook.xinghongfei.looklook.widget.InputfroumPopupWindow;
import com.looklook.xinghongfei.looklook.widget.MyDialog;
import com.looklook.xinghongfei.looklook.widget.SchoolSiftPopupWindow;

import java.util.List;

/**
 * Created by lin on 2017/2/26.
 */

public class ForumDescribleAdapter extends RecyclerView.Adapter<ForumDescribleAdapter.LtDescribeView> {

    private static final int NOMAL_ITEM = 1;
    private static final int TYPE_LOADING_MORE = -1;
    private List<ForumDesribe> ltDescrible_item_list;
    private Context mContext;
    boolean showLoadingMore;

    PopupWindow popupWindow;
    InputfroumPopupWindow inputfroumPopupWindow;


    public ForumDescribleAdapter(Context paramContext, List<ForumDesribe> paramList)
    {
        this.mContext = paramContext;
        ltDescrible_item_list = paramList;
    }



    private void getPopupWindow( final String forumid)
    {

        if (popupWindow == null){
            inputfroumPopupWindow = new InputfroumPopupWindow(mContext);
             popupWindow = inputfroumPopupWindow.getPopupWindow();

        } else if(popupWindow.isShowing()){
            popupWindow.dismiss();}


    }



    public int getItemCount()
    {
        return this.ltDescrible_item_list.size();
    }


    public int getLtcommentCount(ForumDesribe paramForumDesribe)
    {
        int size = paramForumDesribe.getComment_list().size();

        Log.e("duoshao", "getLtcommentCount: "+size );
        if(size == 1){
            ForumDesribe.CommentList commentList = paramForumDesribe.getComment_list().get(0);
            Log.e("fanhui", "getLtcommentCount: "+ commentList);
            if(commentList != null && commentList instanceof ForumDesribe.CommentList && !commentList.equals("") ){
                commentList.toString();
                if(commentList.getId()!=null && !commentList.equals(""))
                return 1;
                else return 0;
            }else {
                Log.e("fanhui", "getLtcommentCount: "+"fanghui 1" );
                return 0;
            }

        }
        if( size == 2){
            return size;
        }
        if(size > 2){
            return 3;
        }
        return 0;
    }

    public void onBindViewHolder(final ForumDescribleAdapter.LtDescribeView paramLtDescribeView, int paramInt)
    {

        final ForumDesribe localForumDesribe = ltDescrible_item_list.get(paramInt);
        Log.e("forumdid", "onClick: "+getLtcommentCount(localForumDesribe));
        Glide.with(mContext).load(MyApplication.getURL()+localForumDesribe.getUser_touxiang()).into(paramLtDescribeView.comment_item_userimage);
        //paramLtDescribeView.comment_item_userimage.setImageResource(R.mipmap.huaruan);
        paramLtDescribeView.comment_item_name.setText(localForumDesribe.getComment_user_name());
        paramLtDescribeView.comment_item_context.setText(localForumDesribe.getComment_context());
        paramLtDescribeView.comment_item_num.setText("第"+(paramInt+1)+"楼");
        paramLtDescribeView.comment_item_time.setText(localForumDesribe.getComment_time());
        paramLtDescribeView.sjx.setVisibility(View.GONE);
        paramLtDescribeView.comment.setVisibility(View.GONE);
        switch (getLtcommentCount(localForumDesribe))
        {

            case 0:
                paramLtDescribeView.sjx.setVisibility(View.GONE);
                paramLtDescribeView.comment.setVisibility(View.GONE);
                paramLtDescribeView.flag1.setVisibility(View.GONE);
                paramLtDescribeView.flag2.setVisibility(View.GONE);
                break;
            case 1:
                paramLtDescribeView.flag1.setVisibility(View.VISIBLE);
                paramLtDescribeView.sjx.setVisibility(View.GONE);
                paramLtDescribeView.comment.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_context.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_user());
                paramLtDescribeView.comment1_context.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_context());
                paramLtDescribeView.comment1_name.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getPopupWindow(localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                            @Override
                            public void comment(String commentcontent){
                                String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                                MyVolleyNet volleyNet = new MyVolleyNet();
                                volleyNet.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("comment",result);
                                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                        //loadData();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                    }
                                });
                                ArrayMap<String,String> map = new ArrayMap<>();
                                map.put("tiezione_id",localForumDesribe.getId());
                                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                                map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                                map.put("tb_comments_two_context","回复"+localForumDesribe.getComment_list().get(0).getIt_ct_item_user()+":"+commentcontent);
                                volleyNet.postNet(url,map);
                            }
                        });
                        popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

                    }

                });
                break;
            case 2:
                paramLtDescribeView.flag1.setVisibility(View.VISIBLE);
                paramLtDescribeView.flag2.setVisibility(View.VISIBLE);
                paramLtDescribeView.sjx.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_context.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_user());
                paramLtDescribeView.comment1_context.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_context());
                paramLtDescribeView.comment1_name.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getPopupWindow(localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                            @Override
                            public void comment(String commentcontent){
                                String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                                MyVolleyNet volleyNet = new MyVolleyNet();
                                volleyNet.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("comment",result);
                                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                        //loadData();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                    }
                                });
                                ArrayMap<String,String> map = new ArrayMap<>();
                                map.put("tiezione_id",localForumDesribe.getId());
                                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                                map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                                map.put("tb_comments_two_context","回复"+localForumDesribe.getComment_list().get(0).getIt_ct_item_user()+":"+commentcontent);
                                volleyNet.postNet(url,map);
                            }
                        });
                        popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

                    }

                });
                paramLtDescribeView.comment2_name.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment2_context.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment2_name.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(1)).getIt_ct_item_user());
                paramLtDescribeView.comment2_context.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(1)).getIt_ct_item_context());
                paramLtDescribeView.comment2_name.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getPopupWindow(localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                            @Override
                            public void comment(String commentcontent){
                                String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                                MyVolleyNet volleyNet = new MyVolleyNet();
                                volleyNet.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("comment",result);
                                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                        //loadData();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                    }
                                });
                                ArrayMap<String,String> map = new ArrayMap<>();
                                map.put("tiezione_id",localForumDesribe.getId());
                                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                                map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                                map.put("tb_comments_two_context","回复"+localForumDesribe.getComment_list().get(0).getIt_ct_item_user()+":"+commentcontent);
                                volleyNet.postNet(url,map);
                            }
                        });
                        popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

                    }

                });
                break;
            case 3:
                paramLtDescribeView.flag1.setVisibility(View.VISIBLE);
                paramLtDescribeView.flag2.setVisibility(View.VISIBLE);
                paramLtDescribeView.sjx.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_context.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment1_name.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_user());
                paramLtDescribeView.comment1_context.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(0)).getIt_ct_item_context());
                paramLtDescribeView.comment1_name.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getPopupWindow(localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                            @Override
                            public void comment(String commentcontent){
                                String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                                MyVolleyNet volleyNet = new MyVolleyNet();
                                volleyNet.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("comment",result);
                                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                        //loadData();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                    }
                                });
                                ArrayMap<String,String> map = new ArrayMap<>();
                                map.put("tiezione_id",localForumDesribe.getId());
                                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                                map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                                map.put("tb_comments_two_context","回复"+localForumDesribe.getComment_list().get(0).getIt_ct_item_user()+":"+commentcontent);
                                volleyNet.postNet(url,map);
                            }
                        });
                        popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

                    }

                });
                paramLtDescribeView.comment2_name.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment2_context.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment2_name.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(1)).getIt_ct_item_user());
                paramLtDescribeView.comment2_context.setText(((ForumDesribe.CommentList)localForumDesribe.getComment_list().get(1)).getIt_ct_item_context());
                paramLtDescribeView.more_comment.setVisibility(View.VISIBLE);
                paramLtDescribeView.comment2_name.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getPopupWindow(localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                            @Override
                            public void comment(String commentcontent){
                                String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                                MyVolleyNet volleyNet = new MyVolleyNet();
                                volleyNet.setCallback(new MyVolleyNet.Callback(){
                                    @Override
                                    public void respond(String result){
                                        Log.e("comment",result);
                                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                        //loadData();
                                    }
                                    @Override
                                    public void error(VolleyError volleyError){
                                    }
                                });
                                ArrayMap<String,String> map = new ArrayMap<>();
                                map.put("tiezione_id",localForumDesribe.getId());
                                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                                map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                                map.put("tb_comments_two_context","回复"+localForumDesribe.getComment_list().get(0).getIt_ct_item_user()+":"+commentcontent);
                                volleyNet.postNet(url,map);
                            }
                        });
                        popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

                    }

                });
                break;
        }
        paramLtDescribeView.comment_control.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                //Toast.makeText(ForumDescribleAdapter.this.mContext, "comment_control", Toast.LENGTH_SHORT).show();
                getPopupWindow(localForumDesribe.getId());
                Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
                    @Override
                    public void comment(String commentcontent){
                        String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
                        MyVolleyNet volleyNet = new MyVolleyNet();
                        volleyNet.setCallback(new MyVolleyNet.Callback(){
                            @Override
                            public void respond(String result){
                                Log.e("comment",result);
                                Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                                //loadData();
                            }
                            @Override
                            public void error(VolleyError volleyError){
                            }
                        });
                        ArrayMap<String,String> map = new ArrayMap<>();
                        map.put("tiezione_id",localForumDesribe.getId());
                        Log.e("forumdid", "onClick: "+localForumDesribe.getId() );
                        map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
                        map.put("tb_comments_two_context",commentcontent);
                        volleyNet.postNet(url,map);
                    }
                });
                popupWindow.showAsDropDown(paramLtDescribeView.comment_control);

            }
        });
        paramLtDescribeView.more_comment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Toast.makeText(ForumDescribleAdapter.this.mContext, "more", Toast.LENGTH_SHORT).show();
                MyDialog dialog = new MyDialog(mContext,localForumDesribe.getComment_list());
                dialog.setTitle("更多评论");
                dialog.show();

            }
        });

    }

    public ForumDescribleAdapter.LtDescribeView onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
                return new LtDescribeView(LayoutInflater.from(this.mContext).inflate(R.layout.luntan_describe, paramViewGroup, false));

    }


    public void update(List<ForumDesribe> list){
        ltDescrible_item_list.clear();
        ltDescrible_item_list.addAll(list);
        notifyDataSetChanged();
    }
    public void refresh(List<ForumDesribe> list){
        ltDescrible_item_list.clear();
        ltDescrible_item_list.addAll(list);
        notifyDataSetChanged();
    }


    public class LtDescribeView extends RecyclerView.ViewHolder
    {
        private LinearLayout comment;
        private LinearLayout comment1;
        private TextView comment1_context;
        private TextView comment1_name;
        private LinearLayout comment2;
        private TextView comment2_context;
        private TextView comment2_name;
        private ImageView comment_control;
        private TextView comment_item_context;
        private TextView comment_item_name;
        private TextView comment_item_num;
        private TextView comment_item_time;
        private ImageView comment_item_userimage;
        private TextView more_comment;
        private ImageView sjx;
        private TextView flag1,flag2;

        public LtDescribeView(View localView)
        {
            super(localView);
            this.comment_item_userimage = ((ImageView)localView.findViewById(R.id.comment_item_userimage));
            this.comment_item_name = ((TextView)localView.findViewById(R.id.comment_item_name));
            this.comment_item_context = ((TextView)localView.findViewById(R.id.comment_item_context));
            this.comment_item_num = ((TextView)localView.findViewById(R.id.comment_item_num));
            this.comment_item_time = ((TextView)localView.findViewById(R.id.comment_item_time));
            this.comment_control = ((ImageView)localView.findViewById(R.id.commet_control));
            this.sjx = ((ImageView)localView.findViewById(R.id.sjx));
            this.comment = ((LinearLayout)localView.findViewById(R.id.comment));
            this.comment1 = ((LinearLayout)localView.findViewById(R.id.comment1));
            this.comment2 = ((LinearLayout)localView.findViewById(R.id.comment2));
            this.comment1_name = ((TextView)localView.findViewById(R.id.comment1_username));
            this.comment1_context = ((TextView)localView.findViewById(R.id.comment1_context));
            this.comment2_name = ((TextView)localView.findViewById(R.id.comment2_username));
            this.comment2_context = ((TextView)localView.findViewById(R.id.comment2_context));
            this.more_comment = ((TextView)localView.findViewById(R.id.more_comment));
            this.flag1 = ((TextView)localView.findViewById(R.id.flag1));
            this.flag2 = ((TextView)localView.findViewById(R.id.flag2));

        }
    }
}
