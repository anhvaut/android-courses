package com.example.huynhle.tim_tro;

/**
 * Created by Huynh Le on 11/4/2017.
 */

public class up_load {


    String Tennguoidung;
    String Anhnguoidung;

    String ThanhPho;
    String QuanHuyen, DiaChi;
    String LoaiPhong;
    String TieuDe, GiaPhong, DienTich, DienThoai, ChiTietPhong;
    String Giodang;
    String LinkAnhTro;

    public up_load(String tennguoidung, String anhnguoidung, String diaChi, String giaPhong, String dienTich, String giodang, String linkAnhTro) {
        Tennguoidung = tennguoidung;
        Anhnguoidung = anhnguoidung;
        DiaChi = diaChi;
        GiaPhong = giaPhong;
        DienTich = dienTich;
        Giodang = giodang;
        LinkAnhTro = linkAnhTro;
    }

    public up_load(String thanhPho, String quanHuyen, String diaChi, String loaiPhong, String tieuDe, String giaPhong, String dienTich, String dienThoai, String chiTietPhong, String linkAnhTro,
                   String tennguoidung, String anhnguoidung, String giodang) {
        ThanhPho = thanhPho;
        QuanHuyen = quanHuyen;
        DiaChi = diaChi;
        LoaiPhong = loaiPhong;
        TieuDe = tieuDe;
        GiaPhong = giaPhong;
        DienTich = dienTich;
        DienThoai = dienThoai;
        ChiTietPhong = chiTietPhong;
        LinkAnhTro = linkAnhTro;

        Giodang = giodang;
        Tennguoidung = tennguoidung;
        Anhnguoidung = anhnguoidung;
    }
}
