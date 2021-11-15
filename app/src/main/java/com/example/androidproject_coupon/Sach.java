package com.example.androidproject_coupon;

public class Sach {
    private String tensach;
    private int hinh;

    public Sach(String tensach, int hinh){
        this.tensach = tensach;
        this.hinh = hinh;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
}
