package com.example.baitaplon_android.model;


import java.io.Serializable;

public class CongViec implements Serializable {
    private int id;
    private String ten;
    private String noiDung;
    private String ngayHoanThanh;
    private String tinhTrang;
    private String congTac;
    private String key;

    public CongViec() {
    }

    public CongViec(int id, String ten, String noiDung, String ngayHoanThanh, String tinhTrang, String congTac) {
        this.id = id;
        this.ten = ten;
        this.noiDung = noiDung;
        this.ngayHoanThanh = ngayHoanThanh;
        this.tinhTrang = tinhTrang;
        this.congTac = congTac;
    }

    public CongViec(String ten, String noiDung, String ngayHoanThanh, String tinhTrang, String congTac) {
        this.ten = ten;
        this.noiDung = noiDung;
        this.ngayHoanThanh = ngayHoanThanh;
        this.tinhTrang = tinhTrang;
        this.congTac = congTac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayHoanThanh() {
        return ngayHoanThanh;
    }

    public void setNgayHoanThanh(String ngayHoanThanh) {
        this.ngayHoanThanh = ngayHoanThanh;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getCongTac() {
        return congTac;
    }

    public void setCongTac(String congTac) {
        this.congTac = congTac;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
