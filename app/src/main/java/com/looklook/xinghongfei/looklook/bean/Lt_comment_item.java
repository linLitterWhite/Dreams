package com.looklook.xinghongfei.looklook.bean;

/**
 * Created by lin on 2017/2/26.
 */

public class Lt_comment_item {
    private int id;
    private String lt_ct_item_context;
    private String lt_ct_item_user;

    public Lt_comment_item(int paramInt, String paramString1, String paramString2)
    {
        this.id = paramInt;
        this.lt_ct_item_user = paramString1;
        this.lt_ct_item_context = paramString2;
    }

    public int getId()
    {
        return this.id;
    }

    public String getLt_ct_item_context()
    {
        return this.lt_ct_item_context;
    }

    public String getLt_ct_item_user()
    {
        return this.lt_ct_item_user;
    }

    public void setId(int paramInt)
    {
        this.id = paramInt;
    }

    public void setLt_ct_item_context(String paramString)
    {
        this.lt_ct_item_context = paramString;
    }

    public void setLt_ct_item_user(String paramString)
    {
        this.lt_ct_item_user = paramString;
    }

    public String toString()
    {
        return "Lt_comment_item{id=" + this.id + ", lt_ct_item_user='" + this.lt_ct_item_user + '\'' + ", lt_ct_item_context='" + this.lt_ct_item_context + '\'' + '}';
    }

}
