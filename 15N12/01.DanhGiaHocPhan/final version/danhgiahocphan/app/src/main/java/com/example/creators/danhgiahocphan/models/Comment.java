package com.example.creators.danhgiahocphan.models;

import java.io.Serializable;

/**
 * Created by Admin on 24/9/2017.
 */

public class Comment implements Serializable {
    private String idCmt;
    private String idUserCmt;
    private String user;
    private String Usermail;
    private String content;
    private String time;

    public Comment() {
    }

    public Comment(String user, String usermail, String content, String time) {
        this.user = user;
        Usermail = usermail;
        this.content = content;
        this.time = time;
    }

    public Comment(String idCmt,  String user, String usermail, String content, String time) {
        this.idCmt = idCmt;
        this.user = user;
        Usermail = usermail;
        this.content = content;
        this.time = time;
    }

    public String getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(String idCmt) {
        this.idCmt = idCmt;
    }

    public String getIdUserCmt() {
        return idUserCmt;
    }

    public void setIdUserCmt(String idUserCmt) {
        this.idUserCmt = idUserCmt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsermail() {
        return Usermail;
    }

    public void setUsermail(String usermail) {
        Usermail = usermail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "idUserCmt='" + idUserCmt + '\'' +
                ", user='" + user + '\'' +
                ", Usermail='" + Usermail + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
