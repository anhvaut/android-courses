package com.example.admin.my_nha_tro;

public class Nhatro {
    private int ID;
    private String TieuDe;
    private int Gia;
    private String DiaChi;
    private  String Mota;

    public Nhatro(int ID, String tieuDe, int gia, String diaChi, String mota) {
        this.ID = ID;
        TieuDe = tieuDe;
        Gia = gia;
        DiaChi = diaChi;
        Mota = mota;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
}
