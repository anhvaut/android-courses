package com.example.creators.danhgiahocphan.models;

/**
 * Created by ngohaihue on 11/4/17.
 */

public class SingletonUser {
    private  static SingletonUser instance;
    private String id;
    private String avartar;
    private String userName;
    private String mail;
    private String major;
    private int gendle;
    private int startYear;
    private String password;
    private  User user;
    public static  SingletonUser Instance(){
        if(instance==null){
            instance=new SingletonUser();
        }
        return instance;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGendle() {
        return gendle;
    }

    public void setGendle(int gendle) {
        this.gendle = gendle;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
