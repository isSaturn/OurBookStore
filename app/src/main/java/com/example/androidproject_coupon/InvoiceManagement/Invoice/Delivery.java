package com.example.androidproject_coupon.InvoiceManagement.Invoice;


public class Delivery {
    private String Gia_Tien, Hinh_Thuc;

    public Delivery(String Gia_Tien, String Hinh_Thuc){
        this.Gia_Tien = Gia_Tien;
        this.Hinh_Thuc = Hinh_Thuc;
    }
    public String getGia_Tien() {
        return Gia_Tien;
    }

    public void setGia_Tien(String Gia_Tien) {
        this.Gia_Tien = Gia_Tien;
    }

    public String getHinh_Thuc() {
        return Hinh_Thuc;
    }

    public void setHinh_Thuc(String Hinh_Thuc) {
        this.Hinh_Thuc = Hinh_Thuc;
    }
}

