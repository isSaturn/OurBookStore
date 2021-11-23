package com.example.androidproject_coupon.BookManagement;

public class Book {
    private String Ten_Sach, Gia, Anh;

    public Book(String ten_Sach, String gia, String anh) {
        Ten_Sach = ten_Sach;
        Gia = gia;
        Anh = anh;
    }

    public String getTen_Sach() {
        return Ten_Sach;
    }

    public void setTen_Sach(String ten_Sach) {
        Ten_Sach = ten_Sach;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }
}
