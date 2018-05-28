package com.example.creators.danhgiahocphan.models;

import java.io.Serializable;

/**
 * Created by Hoàng Thạch on 29/10/2017.
 */

public class FeedbackContent implements Serializable {
    private String idFeedBack;
    private String userMail;
    private String content;

    public String getIdFeedBack() {
        return idFeedBack;
    }

    public void setIdFeedBack(String idFeedBack) {
        this.idFeedBack = idFeedBack;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeedbackContent() {
    }

    public FeedbackContent(String idFeedBack, String userMail, String content) {
        this.idFeedBack = idFeedBack;
        this.userMail = userMail;
        this.content = content;
    }
}
