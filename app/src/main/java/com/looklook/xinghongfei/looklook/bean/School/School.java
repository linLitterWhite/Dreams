package com.looklook.xinghongfei.looklook.bean.School;

/**
 * Created by lin on 2017/2/12.
 */

public class School {

    private int id;
    private String school_introduce;
    private String school_latitude;
    private String school_address;
    private String school_name;
    private String school_phone;
    private String school_picture;
    private String school_type;
    private String school_web_url;
    private String shcool_longitude;

    public School(int id, String school_introduce, String school_latitude, String school_address,
                  String school_name, String school_phone, String school_picture, String school_type,
                  String school_web_url, String shcool_longitude) {
        this.id = id;
        this.school_introduce = school_introduce;
        this.school_latitude = school_latitude;
        this.school_address = school_address;
        this.school_name = school_name;
        this.school_phone = school_phone;
        this.school_picture = school_picture;
        this.school_type = school_type;
        this.school_web_url = school_web_url;
        this.shcool_longitude = shcool_longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchool_introduce() {
        return school_introduce;
    }

    public void setSchool_introduce(String school_introduce) {
        this.school_introduce = school_introduce;
    }

    public String getSchool_latitude() {
        return school_latitude;
    }

    public void setSchool_latitude(String school_latitude) {
        this.school_latitude = school_latitude;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_phone() {
        return school_phone;
    }

    public void setSchool_phone(String school_phone) {
        this.school_phone = school_phone;
    }

    public String getSchool_picture() {
        return school_picture;
    }

    public void setSchool_picture(String school_picture) {
        this.school_picture = school_picture;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getSchool_web_url() {
        return school_web_url;
    }

    public void setSchool_web_url(String school_web_url) {
        this.school_web_url = school_web_url;
    }

    public String getShcool_longitude() {
        return shcool_longitude;
    }

    public void setShcool_longitude(String shcool_longitude) {
        this.shcool_longitude = shcool_longitude;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", school_introduce='" + school_introduce + '\'' +
                ", school_latitude='" + school_latitude + '\'' +
                ", school_location='" + school_address + '\'' +
                ", school_name='" + school_name + '\'' +
                ", school_phone='" + school_phone + '\'' +
                ", school_picture='" + school_picture + '\'' +
                ", school_type='" + school_type + '\'' +
                ", school_web_url='" + school_web_url + '\'' +
                ", shcool_longitude='" + shcool_longitude + '\'' +
                '}';
    }
}