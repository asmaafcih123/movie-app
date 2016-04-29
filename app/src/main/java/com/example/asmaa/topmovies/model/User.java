package com.example.asmaa.topmovies.model;

/**
 * Created by asmaa on 4/27/2016.
 */
public class User
{
        private int User_id;
        private String User_name;
        private String e_mail;
        private String password;

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }



    public User(String User_name, String e_mail, String password) {
            this.User_name = User_name;
            this.e_mail = e_mail;
            this.password = password;
        }
        public User() {
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getE_mail() {
            return e_mail;
        }

        public void setE_mail(String e_mail) {
            this.e_mail = e_mail;
        }


}
