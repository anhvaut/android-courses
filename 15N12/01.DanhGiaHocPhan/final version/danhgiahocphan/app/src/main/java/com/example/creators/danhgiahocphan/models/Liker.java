package com.example.creators.danhgiahocphan.models;

/**
 * Created by Hoàng Thạch on 27/10/2017.
 */

public class Liker {
    String idLiker;
    String mailLiker;

    public Liker(String idLiker, String mailLiker) {
        this.idLiker = idLiker;
        this.mailLiker = mailLiker;
    }

    public Liker() {
    }

    public String getIdLiker() {
        return idLiker;
    }

    public void setIdLiker(String idLiker) {
        this.idLiker = idLiker;
    }

    public String getMailLiker() {
        return mailLiker;
    }

    public void setMailLiker(String mailLiker) {
        this.mailLiker = mailLiker;
    }
}
