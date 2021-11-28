package com.example.androidproject_coupon.InvoiceManagement.Invoice;

public class InvoiceBook {
    private String anh,ten,gia,xoa;


    public InvoiceBook(String anh,String ten,String gia,String xoa) {
        this.anh = anh;
        this.ten = ten;
        this.gia = gia;
        this.xoa = xoa;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getXoa() {
        return xoa;
    }

    public void setXoa(String xoa) {
        this.xoa = xoa;
    }
}
