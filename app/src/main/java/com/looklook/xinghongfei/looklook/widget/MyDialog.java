package com.looklook.xinghongfei.looklook.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.bean.ForumDesribe;
import com.looklook.xinghongfei.looklook.bean.Lt_comment_item;
import com.looklook.xinghongfei.looklook.net.MyVolleyNet;
import com.looklook.xinghongfei.looklook.util.SharePreferenceUtil;

import java.util.List;

import static com.looklook.xinghongfei.looklook.R.id.view;

/**
 * Created by lin on 2017/3/20.
 */

public class MyDialog extends Dialog {

    private Context mContext;
    protected ListView list;
    private List<ForumDesribe.CommentList> commentList;

    PopupWindow popupWindow;
    InputfroumPopupWindow inputfroumPopupWindow;

    public MyDialog(Context context,List<ForumDesribe.CommentList> commentList) {
        super(context);
        this.commentList = commentList;
        initView(context);

    }


    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.froumlistview,null);
        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new MyAdapter(commentList,mContext));

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
//                final ForumDesribe.CommentList ll = commentList.get(i);
//                getPopupWindow(ll.getId());
//                Log.e("forumdid", "onClick: "+ll.getId() );
//                inputfroumPopupWindow.setreturninfo(new InputfroumPopupWindow.Comment(){
//                    @Override
//                    public void comment(String commentcontent){
//                        String url = MyApplication.getURL()+"/tiezitwo_collection/Andr_tiezitwo_add";
//                        MyVolleyNet volleyNet = new MyVolleyNet();
//                        volleyNet.setCallback(new MyVolleyNet.Callback(){
//                            @Override
//                            public void respond(String result){
//                                Log.e("comment",result);
//                                Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
//                                //loadData();
//                            }
//                            @Override
//                            public void error(VolleyError volleyError){
//                            }
//                        });
//                        ArrayMap<String,String> map = new ArrayMap<>();
//                        map.put("tiezione_id",ll.getId());
//                        Log.e("forumdid", "onClick: "+ll.getId() );
//                        map.put("user_id", SharePreferenceUtil.getUserInfo(mContext,"id"));
//                        map.put("tb_comments_two_context","回复"+ll.getIt_ct_item_user()+"："+commentcontent);
//                        volleyNet.postNet(url,map);
//                    }
//                });
//                popupWindow.showAsDropDown(adapterView);
//
//            }
//
//
//        });
        super.setContentView(view);
    }


    private void getPopupWindow( final String forumid)
    {

        if (popupWindow == null){
            inputfroumPopupWindow = new InputfroumPopupWindow(mContext);
            popupWindow = inputfroumPopupWindow.getPopupWindow();

        } else if(popupWindow.isShowing()){
            popupWindow.dismiss();}


    }


    public class MyAdapter extends BaseAdapter{

        private List<ForumDesribe.CommentList> mcommentList;
        private Context context;


        public MyAdapter(List<ForumDesribe.CommentList> commentList, Context context){
            this.mcommentList = commentList;
            this.context = context;
        }

        @Override
        public int getCount(){
            return mcommentList.size();
        }

        @Override
        public Object getItem(int i){
            return mcommentList.get(i);
        }

        @Override
        public long getItemId(int i){
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            final ForumDesribe.CommentList ll = mcommentList.get(i);
            final ViewHolder holder;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_list, null);
                holder = new ViewHolder();
                holder.user = (TextView) view.findViewById(R.id.user);
                holder.context = (TextView) view.findViewById(R.id.context);

                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            holder.user.setText(ll.getIt_ct_item_user());
            holder.context.setText(ll.getIt_ct_item_context());
            return view;

        }

        class ViewHolder{

            private TextView user;
            private TextView context;
        }
    }
}
