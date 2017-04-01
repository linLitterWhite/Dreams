package com.looklook.xinghongfei.looklook.bean;

import java.util.List;

/**
 * Created by lin on 2017/2/25.
 */

public class Forum {

    private String id;
    private String user_name;
    private String tieba_time;
    private String tieba_title;
    private String tieba_content;
    private int It_item_comment_num;
    private String user_picture_url;
    private String user_id;
    private List<TiebaContentPictureurl> tieba_content_pictureurl;
    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}
    public String getUser_name(){ return user_name;}
    public void setUser_name(String user_name){ this.user_name = user_name;}
    public String getTieba_time(){ return tieba_time;}
    public void setTieba_time(String tieba_time){ this.tieba_time = tieba_time;}
    public String getTieba_title(){ return tieba_title;}
    public void setTieba_title(String tieba_title){ this.tieba_title = tieba_title;}
    public String getTieba_content(){ return tieba_content;}
    public void setTieba_content(String tieba_content){ this.tieba_content = tieba_content;}
    public int getIt_item_comment_num(){ return It_item_comment_num;}
    public void setIt_item_comment_num(int It_item_comment_num){ this.It_item_comment_num = It_item_comment_num;}
    public String getUser_picture_url(){ return user_picture_url;}
    public void setUser_picture_url(String user_picture_url){ this.user_picture_url = user_picture_url;}
    public String getUser_id(){ return user_id;}
    public void setUser_id(String user_id){ this.user_id = user_id;}
    public List<TiebaContentPictureurl> getTieba_content_pictureurl(){ return tieba_content_pictureurl;}
    public void setTieba_content_pictureurl(List<TiebaContentPictureurl> tieba_content_pictureurl){ this.tieba_content_pictureurl = tieba_content_pictureurl;}
    public static class TiebaContentPictureurl{

        private String img;
        public String getImg(){ return img;}
        public void setImg(String img){ this.img = img;}
    }
}
