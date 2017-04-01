package com.looklook.xinghongfei.looklook.bean.School;

/**
 * Created by lin on 2017/3/16.
 */

public class HuanjingPicture {
    public String imageUrl;
    public String imageText;

    public HuanjingPicture(String imageUrl, String imageText) {
        this.imageUrl = imageUrl;
        this.imageText = imageText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }
}
