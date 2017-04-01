package com.looklook.xinghongfei.looklook.bean.School;

import java.util.List;

/**
 * Created by lin on 2017/3/25.
 */

public class DscribeSchool{

	private School school;
	private List<HuanjingPicture> huanjing_picture;
	private List<SchoolZhuanyeList> school_zhuanye_list;
	public School getSchool(){ return school;}
	public void setSchool(School school){ this.school = school;}
	public List<HuanjingPicture> getHuanjing_picture(){ return huanjing_picture;}
	public void setHuanjing_picture(List<HuanjingPicture> huanjing_picture){ this.huanjing_picture = huanjing_picture;}
	public List<SchoolZhuanyeList> getSchool_zhuanye_list(){ return school_zhuanye_list;}
	public void setSchool_zhuanye_list(List<SchoolZhuanyeList> school_zhuanye_list){ this.school_zhuanye_list = school_zhuanye_list;}
	public static class School{

		private String id;
		private String school_name;
		private String school_picture;
		private String school_phone;
		private String school_introduce;
		private String school_location;
		private String school_location_coordinate;
		private String school_type;
		private String school_dormitory_picture;
		private String school_dininghall_picture;
		private String school_others_picture;
		public String getId(){ return id;}
		public void setId(String id){ this.id = id;}
		public String getSchool_name(){ return school_name;}
		public void setSchool_name(String school_name){ this.school_name = school_name;}
		public String getSchool_picture(){ return school_picture;}
		public void setSchool_picture(String school_picture){ this.school_picture = school_picture;}
		public String getSchool_phone(){ return school_phone;}
		public void setSchool_phone(String school_phone){ this.school_phone = school_phone;}
		public String getSchool_introduce(){ return school_introduce;}
		public void setSchool_introduce(String school_introduce){ this.school_introduce = school_introduce;}
		public String getSchool_location(){ return school_location;}
		public void setSchool_location(String school_location){ this.school_location = school_location;}
		public String getSchool_location_coordinate(){ return school_location_coordinate;}
		public void setSchool_location_coordinate(String school_location_coordinate){ this.school_location_coordinate = school_location_coordinate;}
		public String getSchool_type(){ return school_type;}
		public void setSchool_type(String school_type){ this.school_type = school_type;}
		public String getSchool_dormitory_picture(){ return school_dormitory_picture;}
		public void setSchool_dormitory_picture(String school_dormitory_picture){ this.school_dormitory_picture = school_dormitory_picture;}
		public String getSchool_dininghall_picture(){ return school_dininghall_picture;}
		public void setSchool_dininghall_picture(String school_dininghall_picture){ this.school_dininghall_picture = school_dininghall_picture;}
		public String getSchool_others_picture(){ return school_others_picture;}
		public void setSchool_others_picture(String school_others_picture){ this.school_others_picture = school_others_picture;}
	}

	public static class HuanjingPicture{

		private String img;
		private String jieshao;
		public String getImg(){ return img;}
		public void setImg(String img){ this.img = img;}
		public String getJieshao(){ return jieshao;}
		public void setJieshao(String jieshao){ this.jieshao = jieshao;}
	}

	public static class SchoolZhuanyeList{

		private String id;
		private String school_id;
		private String school_zhuanye_name;
		private String school_zhuanye_jieshao;
		public String getId(){ return id;}
		public void setId(String id){ this.id = id;}
		public String getSchool_id(){ return school_id;}
		public void setSchool_id(String school_id){ this.school_id = school_id;}
		public String getSchool_zhuanye_name(){ return school_zhuanye_name;}
		public void setSchool_zhuanye_name(String school_zhuanye_name){ this.school_zhuanye_name = school_zhuanye_name;}
		public String getSchool_zhuanye_jieshao(){ return school_zhuanye_jieshao;}
		public void setSchool_zhuanye_jieshao(String school_zhuanye_jieshao){ this.school_zhuanye_jieshao = school_zhuanye_jieshao;}
	}

	@Override
	public String toString(){
		return "\nDscribeSchool{\n" +
			       "\tschool='" + school + "',\n" +
			       "\thuanjing_picture='" + huanjing_picture + "',\n" +
			       "\tschool_zhuanye_list='" + school_zhuanye_list + "',\n" +
			       '}';
	}
}
