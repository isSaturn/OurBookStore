package com.example.androidproject_coupon.OrderManagement;

public class Oder {
    String time, code, status, price, address;

    public Oder(String time, String code, String status, String price, String address) {
        this.time = time;
        this.code = code;
        this.status = status;
        this.price = price;
        this.address = address;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
