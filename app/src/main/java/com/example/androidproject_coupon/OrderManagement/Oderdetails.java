package com.example.androidproject_coupon.OrderManagement;

public class Oderdetails {
    private String Dia_Chi;
    private String Ho_Ten;
    private String ID_Hinh_Thuc_GH;
    private String ID_Khuyen_Mai;
    private String ID_Sach;
    private String ID_Tai_Khoan;
    private String ID_Trang_Thai_DH;
    private String  Ma_Don_Hang;
    private String SDT;
    private String Time;
    private String Tong_Tien;
    private String Anh;

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public Oderdetails(String anh) {
        Anh = anh;
    }

    public String getDia_Chi() {
        return Dia_Chi;
    }

    public void setDia_Chi(String dia_Chi) {
        Dia_Chi = dia_Chi;
    }

    public String getHo_Ten() {
        return Ho_Ten;
    }

    public void setHo_Ten(String ho_Ten) {
        Ho_Ten = ho_Ten;
    }

    public String getID_Hinh_Thuc_GH() {
        return ID_Hinh_Thuc_GH;
    }

    public void setID_Hinh_Thuc_GH(String ID_Hinh_Thuc_GH) {
        this.ID_Hinh_Thuc_GH = ID_Hinh_Thuc_GH;
    }

    public String getID_Khuyen_Mai() {
        return ID_Khuyen_Mai;
    }

    public void setID_Khuyen_Mai(String ID_Khuyen_Mai) {
        this.ID_Khuyen_Mai = ID_Khuyen_Mai;
    }

    public String getID_Sach() {
        return ID_Sach;
    }

    public void setID_Sach(String ID_Sach) {
        this.ID_Sach = ID_Sach;
    }

    public String getID_Tai_Khoan() {
        return ID_Tai_Khoan;
    }

    public void setID_Tai_Khoan(String ID_Tai_Khoan) {
        this.ID_Tai_Khoan = ID_Tai_Khoan;
    }

    public String getID_Trang_Thai_DH() {
        return ID_Trang_Thai_DH;
    }

    public void setID_Trang_Thai_DH(String ID_Trang_Thai_DH) {
        this.ID_Trang_Thai_DH = ID_Trang_Thai_DH;
    }

    public String getMa_Don_Hang() {
        return Ma_Don_Hang;
    }

    public void setMa_Don_Hang(String ma_Don_Hang) {
        Ma_Don_Hang = ma_Don_Hang;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTong_Tien() {
        return Tong_Tien;
    }

    public void setTong_Tien(String tong_Tien) {
        Tong_Tien = tong_Tien;
    }

    public Oderdetails(String dia_Chi, String ho_Ten, String ID_Hinh_Thuc_GH, String ID_Khuyen_Mai, String ID_Sach, String ID_Tai_Khoan, String ID_Trang_Thai_DH, String ma_Don_Hang, String SDT, String time, String tong_Tien) {
        Dia_Chi = dia_Chi;
        Ho_Ten = ho_Ten;
        this.ID_Hinh_Thuc_GH = ID_Hinh_Thuc_GH;
        this.ID_Khuyen_Mai = ID_Khuyen_Mai;
        this.ID_Sach = ID_Sach;
        this.ID_Tai_Khoan = ID_Tai_Khoan;
        this.ID_Trang_Thai_DH = ID_Trang_Thai_DH;
        Ma_Don_Hang = ma_Don_Hang;
        this.SDT = SDT;
        Time = time;
        Tong_Tien = tong_Tien;


    }

}
