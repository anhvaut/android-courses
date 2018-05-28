package com.example.creators.danhgiahocphan.models;

import java.io.Serializable;

/**
 *
 */

public class Subject_Teacher implements Serializable {

    private String idTeacher;
    private String name;
    private int noComment;
    private int noLike;
    private String idSubject;
    private String avatar;

    public Subject_Teacher(String idTeacher, String nameTeacher, int noComment, int noLike, String idSubject, String avatar) {
        this.idTeacher = idTeacher;
        this.name = nameTeacher;
        this.noComment = noComment;
        this.noLike = noLike;
        this.idSubject = idSubject;
        this.avatar = avatar;
    }

    public Subject_Teacher() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getNameTeacher() {
        return name;
    }

    public void setNameTeacher(String nameTeacher) {
        this.name = nameTeacher;
    }

    public int getNoComment() {
        return noComment;
    }

    public void setNoComment(int noComment) {
        this.noComment = noComment;
    }

    public int getNoLike() {
        return noLike;
    }

    public void setNoLike(int noLike) {
        this.noLike = noLike;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }
}