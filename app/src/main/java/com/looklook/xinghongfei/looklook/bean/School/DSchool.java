package com.looklook.xinghongfei.looklook.bean.School;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lin on 2017/3/19.
 */

public class DSchool implements Serializable{

    private String school_type;
    private String school_phone;
    private String id;
    private String school_name;
    private String school_introduce;
    private int school_collect_num;
    private String score;
    private String school_location;
    private List<SchoolPicture> school_picture;
    private List<SchoolLocationCoordinate> school_location_coordinate;
    public String getSchool_type(){ return school_type;}
    public void setSchool_type(String school_type){ this.school_type = school_type;}
    public String getSchool_phone(){ return school_phone;}
    public void setSchool_phone(String school_phone){ this.school_phone = school_phone;}
    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}
    public String getSchool_name(){ return school_name;}
    public void setSchool_name(String school_name){ this.school_name = school_name;}
    public String getSchool_introduce(){ return school_introduce;}
    public void setSchool_introduce(String school_introduce){ this.school_introduce = school_introduce;}
    public int getSchool_collect_num(){ return school_collect_num;}
    public void setSchool_collect_num(int school_collect_num){ this.school_collect_num = school_collect_num;}
    public String getScore(){ return score;}
    public void setScore(String score){ this.score = score;}
    public String getSchool_location(){ return school_location;}
    public void setSchool_location(String school_location){ this.school_location = school_location;}
    public List<SchoolPicture> getSchool_picture(){ return school_picture;}
    public void setSchool_picture(List<SchoolPicture> school_picture){ this.school_picture = school_picture;}
    public List<SchoolLocationCoordinate> getSchool_location_coordinate(){ return school_location_coordinate;}
    public void setSchool_location_coordinate(List<SchoolLocationCoordinate> school_location_coordinate){ this.school_location_coordinate = school_location_coordinate;}
    public static class SchoolPicture{

        private String school_picture;
        public String getSchool_picture(){ return school_picture;}
        public void setSchool_picture(String school_picture){ this.school_picture = school_picture;}
    }

    public static class SchoolLocationCoordinate{

        private String weidu;
        private String jingdu;
        public String getWeidu(){ return weidu;}
        public void setWeidu(String weidu){ this.weidu = weidu;}
        public String getJingdu(){ return jingdu;}
        public void setJingdu(String jingdu){ this.jingdu = jingdu;}
    }
}
