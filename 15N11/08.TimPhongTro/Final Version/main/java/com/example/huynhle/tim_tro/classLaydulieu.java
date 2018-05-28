package com.example.huynhle.tim_tro;

/**
 * Created by Huynh Le on 11/4/2017.
 */

public class classLaydulieu {
    private String Tennguoidung;
    private String Anhnguoidung;
    private String QuanHuyen;
    private String GiaPhong;
    private String DienTich;
    private String Giodang;
    private String LinkAnhTro;

    public classLaydulieu() {
    }

    public classLaydulieu(String tennguoidung, String anhnguoidung, String quanHuyen, String giaPhong, String dienTich, String giodang, String linkAnhTro) {
        Tennguoidung = tennguoidung;
        Anhnguoidung = anhnguoidung;
        QuanHuyen = quanHuyen;
        GiaPhong = giaPhong;
        DienTich = dienTich;
        Giodang = giodang;
        LinkAnhTro = linkAnhTro;
    }

    public String getTennguoidung() {
        return Tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        Tennguoidung = tennguoidung;
    }

    public String getAnhnguoidung() {
        return Anhnguoidung;
    }

    public void setAnhnguoidung(String anhnguoidung) {
        Anhnguoidung = anhnguoidung;
    }

    public String getQuanHuyen() {
        return QuanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        QuanHuyen = quanHuyen;
    }

    public String getGiaPhong() {
        return GiaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        GiaPhong = giaPhong;
    }

    public String getDienTich() {
        return DienTich;
    }

    public void setDienTich(String dienTich) {
        DienTich = dienTich;
    }

    public String getGiodang() {
        return Giodang;
    }

    public void setGiodang(String giodang) {
        Giodang = giodang;
    }

    public String getLinkAnhTro() {
        return LinkAnhTro;
    }

    public void setLinkAnhTro(String linkAnhTro) {
        LinkAnhTro = linkAnhTro;
    }
}

