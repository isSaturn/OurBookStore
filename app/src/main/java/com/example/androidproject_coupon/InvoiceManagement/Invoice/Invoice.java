package com.example.androidproject_coupon.InvoiceManagement.Invoice;

public class Invoice {
    public String diachi, hoten, idHinhthucGH, idKhuyenmai, idTaiKhoan, idTrangthaiDH, maDonhang, sdt, time, tongtien;


    public Invoice(String invoiceID, String diachi, String hoten, String idHinhthucGH, String idKhuyenmai, String idTaiKhoan, String idTrangthaiDH, String maDonhang, String sdt, String time, String tongtien) {
        this.diachi = diachi;
        this.hoten = hoten;
        this.idHinhthucGH = idHinhthucGH;
        this.idKhuyenmai = idKhuyenmai;
        this.idTaiKhoan = idTaiKhoan;
        this.idTrangthaiDH = idTrangthaiDH;
        this.maDonhang = maDonhang;
        this.sdt = sdt;
        this.time = time;
        this.tongtien = tongtien;
    }


    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getIdHinhthucGH() {
        return idHinhthucGH;
    }

    public void setIdHinhthucGH(String idHinhthucGH) {
        this.idHinhthucGH = idHinhthucGH;
    }

    public String getIdKhuyenmai() {
        return idKhuyenmai;
    }

    public void setIdKhuyenmai(String idKhuyenmai) {
        this.idKhuyenmai = idKhuyenmai;
    }

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getIdTrangthaiDH() {
        return idTrangthaiDH;
    }

    public void setIdTrangthaiDH(String idTrangthaiDH) {
        this.idTrangthaiDH = idTrangthaiDH;
    }

    public String getMaDonhang() {
        return maDonhang;
    }

    public void setMaDonhang(String maDonhang) {
        this.maDonhang = maDonhang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
}