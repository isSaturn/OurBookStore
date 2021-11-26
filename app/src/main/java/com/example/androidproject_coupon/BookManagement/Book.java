package com.example.androidproject_coupon.BookManagement;

import java.io.Serializable;

public class Book implements Serializable {
    private String ID,Ma_Sach, Ten_Sach, Tac_Gia, Mo_Ta, Gia, So_Luong, Anh, ID_Nhom_Sach;

    public Book(String ID, String ma_Sach, String ten_Sach, String tac_Gia, String mo_Ta,
                String gia, String so_Luong, String anh, String ID_Nhom_Sach) {
        this.ID = ID;
        Ma_Sach = ma_Sach;
        Ten_Sach = ten_Sach;
        Tac_Gia = tac_Gia;
        Mo_Ta = mo_Ta;
        Gia = gia;
        So_Luong = so_Luong;
        Anh = anh;
        this.ID_Nhom_Sach = ID_Nhom_Sach;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMa_Sach() {
        return Ma_Sach;
    }

    public void setMa_Sach(String ma_Sach) {
        Ma_Sach = ma_Sach;
    }

    public String getTen_Sach() {
        return Ten_Sach;
    }

    public void setTen_Sach(String ten_Sach) {
        Ten_Sach = ten_Sach;
    }

    public String getTac_Gia() {
        return Tac_Gia;
    }

    public void setTac_Gia(String tac_Gia) {
        Tac_Gia = tac_Gia;
    }

    public String getMo_Ta() {
        return Mo_Ta;
    }

    public void setMo_Ta(String mo_Ta) {
        Mo_Ta = mo_Ta;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getSo_Luong() {
        return So_Luong;
    }

    public void setSo_Luong(String so_Luong) {
        So_Luong = so_Luong;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public String getID_Nhom_Sach() {
        return ID_Nhom_Sach;
    }

    public void setID_Nhom_Sach(String ID_Nhom_Sach) {
        this.ID_Nhom_Sach = ID_Nhom_Sach;
    }
}
