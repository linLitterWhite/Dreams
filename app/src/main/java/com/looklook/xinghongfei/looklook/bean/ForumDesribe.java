package com.looklook.xinghongfei.looklook.bean;

import java.util.List;

/**
 * Created by lin on 2017/2/26.
 */

public class ForumDesribe {

    private String id;
    String tieba_id;
    String user_touxiang;
    private String comment_context;
    private String comment_time;
    private String comment_user_name;
    private int comment_ranking;
    private List<CommentList> comment_list;
    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}
    public String getTieba_id(){
        return tieba_id;
    }
    public void setTieba_id(String tieba_id){
        this.tieba_id = tieba_id;
    }
    public String getUser_touxiang(){
        return user_touxiang;
    }
    public void setUser_touxiang(String user_touxiang){
        this.user_touxiang = user_touxiang;
    }
    public String getComment_context(){ return comment_context;}
    public void setComment_context(String comment_context){ this.comment_context = comment_context;}
    public String getComment_time(){ return comment_time;}
    public void setComment_time(String comment_time){ this.comment_time = comment_time;}
    public String getComment_user_name(){ return comment_user_name;}
    public void setComment_user_name(String comment_user_name){ this.comment_user_name = comment_user_name;}
    public int getComment_ranking(){ return comment_ranking;}
    public void setComment_ranking(int comment_ranking){ this.comment_ranking = comment_ranking;}
    public List<CommentList> getComment_list(){ return comment_list;}
    public void setComment_list(List<CommentList> comment_list){ this.comment_list = comment_list;}
    public static class CommentList{

        private String id;
        private String It_ct_item_context;
        private String It_ct_item_user;
        public String getId(){ return id;}
        public void setId(String id){ this.id = id;}
        public String getIt_ct_item_context(){ return It_ct_item_context;}
        public void setIt_ct_item_context(String It_ct_item_context){ this.It_ct_item_context = It_ct_item_context;}
        public String getIt_ct_item_user(){ return It_ct_item_user;}
        public void setIt_ct_item_user(String It_ct_item_user){ this.It_ct_item_user = It_ct_item_user;}
        @Override
        public String toString(){
            return "\nCommentList{\n" +
                       "\tid='" + id + "',\n" +
                       "\tIt_ct_item_context='" + It_ct_item_context + "',\n" +
                       "\tIt_ct_item_user='" + It_ct_item_user + "',\n" +
                       '}';
        }
    }
}
