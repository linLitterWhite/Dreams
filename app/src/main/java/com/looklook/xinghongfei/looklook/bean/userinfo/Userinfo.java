package com.looklook.xinghongfei.looklook.bean.userinfo;

/**
 * Created by lin on 2017/3/12.
 */

public class Userinfo {


    private String result;
    private User user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private String id;
        private String user_name;
        private String user_pasword;
        private String user_phone;
        private String user_picture;
        private String user_sex;
        private String user_dengji;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_pasword() {
            return user_pasword;
        }

        public void setUser_pasword(String user_pasword) {
            this.user_pasword = user_pasword;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public void setUser_picture(String user_picture) {
            this.user_picture = user_picture;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_dengji() {
            return user_dengji;
        }

        public void setUser_dengji(String user_dengji) {
            this.user_dengji = user_dengji;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_pasword='" + user_pasword + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    ", user_picture='" + user_picture + '\'' +
                    ", user_sex='" + user_sex + '\'' +
                    ", user_dengji='" + user_dengji + '\'' +
                    '}';
        }
    }
}
