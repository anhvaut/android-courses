package com.example.quang.tabdemo;

/**
 * Created by PC on 13/09/2017.
 */

public class MonchienStruct {
    private String Ten;
    private int Hinh;

    public MonchienStruct(String ten, int hinh) {
        Ten = ten;
        Hinh = hinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}
