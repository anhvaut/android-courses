package com.example.creators.danhgiahocphan.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String avartar;
    private String userName;
    private String mail;
    private String major;
    private int gendle;
    private int startYear;
    private String password;
    public User() {
    }
    public User(String id, String avartar,String userName, String mail, String major, int gendle, int startYear, String password) {
        this.id=id;
        this.avartar = avartar;
        this.userName = userName;
        this.mail = mail;
        this.major = major;
        this.gendle = gendle;
        this.startYear = startYear;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", avartar='" + avartar + '\'' +
                ", userName='" + userName + '\'' +
                ", mail='" + mail + '\'' +
                ", major='" + major + '\'' +
                ", gendle=" + gendle +
                ", startYear=" + startYear +
                ", password='" + password + '\'' +
                '}';
    }
}
