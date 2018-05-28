package ttn.cuongnguyen.tomato;

import android.text.Editable;

import java.io.Serializable;

/**
 * Created by Cuongnguyen on 22/11/2017.
 */

public class CongViec implements Serializable {
    String tenCongViec;
    String thoiGianGio;
    String thoiGianNgay;

    public CongViec(Editable text, String txtGioText, String txtNgayText) {
    }

    public CongViec(String tenCongViec, String thoiGianGio, String thoiGianNgay) {
        this.tenCongViec = tenCongViec;
        this.thoiGianGio = thoiGianGio;
        this.thoiGianNgay = thoiGianNgay;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public String getThoiGianGio() {
        return thoiGianGio;
    }

    public void setThoiGianGio(String thoiGianGio) {
        this.thoiGianGio = thoiGianGio;
    }

    public String getThoiGianNgay() {
        return thoiGianNgay;
    }

    public void setThoiGianNgay(String thoiGianNgay) {
        this.thoiGianNgay = thoiGianNgay;
    }



}
