package com.example.creators.danhgiahocphan.models;

/**
 * Created by Thach on 20/10/2017.
 */

public class Teacher {
    private String idTeacher;
    private String avatar;
    private String idComment;
    private String level;
    private int like;
    private String mail;
    private String name;
    private int noComment;
    private String phone;
    private String subjectTeach;

    public Teacher() {
    }


    public Teacher(String idTeacher, String avatar, String idComment, String level, int like, String mail, String name, int noComment, String phone, String subjectTeach) {
        this.idTeacher = idTeacher;
        this.avatar = avatar;
        this.idComment = idComment;
        this.level = level;
        this.like = like;
        this.mail = mail;
        this.name = name;
        this.noComment = noComment;
        this.phone = phone;
        this.subjectTeach = subjectTeach;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoComment() {
        return noComment;
    }

    public void setNoComment(int noComment) {
        this.noComment = noComment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubjectTeach() {
        return subjectTeach;
    }

    public void setSubjectTeach(String subjectTeach) {
        this.subjectTeach = subjectTeach;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "idTeacher='" + idTeacher + '\'' +
                ", avatar='" + avatar + '\'' +
                ", idComment='" + idComment + '\'' +
                ", level='" + level + '\'' +
                ", like=" + like +
                ", mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", noComment=" + noComment +
                ", phone='" + phone + '\'' +
                ", subjectTeach='" + subjectTeach + '\'' +
                '}';
    }
}
