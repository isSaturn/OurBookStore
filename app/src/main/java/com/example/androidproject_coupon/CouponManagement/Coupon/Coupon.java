package com.example.androidproject_coupon.CouponManagement.Coupon;

public class Coupon {
    private String name, code, eStart, eEnd, applyCondition, type;
    private Integer value, valueCondition;
    private Integer imgUrl;

    public Coupon(String name, Integer value, Integer imgUrl){
        this.name = name;
        this.value = value;
        this.imgUrl= imgUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String geteStart() {
        return eStart;
    }

    public void seteStart(String eStart) {
        this.eStart = eStart;
    }

    public String geteEnd() {
        return eEnd;
    }

    public void seteEnd(String eEnd) {
        this.eEnd = eEnd;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(Integer valueCondition) {
        this.valueCondition = valueCondition;
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
