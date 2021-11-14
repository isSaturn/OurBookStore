package com.example.androidproject_coupon;

public class Coupon {
    private String name;
    private Integer value;
    private Integer imgUrl;

    public Coupon(String name, Integer value, Integer imgUrl){
        this.name = name;
        this.value = value;
        this.imgUrl= imgUrl;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getValue(){
        return value;
    }
    public void setValue(Integer value){
        this.value = value;
    }
}
