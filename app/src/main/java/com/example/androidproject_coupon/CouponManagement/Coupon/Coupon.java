package com.example.androidproject_coupon.CouponManagement.Coupon;

public class Coupon  {
    private String name, code, eStart, eEnd ,value, valueCondition, idType, idCondition;
    private Integer imgUrl;

    public Coupon(String code,String name, String eStart, String eEnd, String value, String valueCondition, String idCondition, String idType,Integer imgUrl){
        this.code = code;
        this.name = name;
        this.valueCondition = valueCondition;
        this.idCondition = idCondition;
        this.idType = idType;
        this.value = value;
        this.imgUrl= imgUrl;
        this.eStart = eStart;
        this.eEnd = eEnd;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(String valueCondition) {
        this.valueCondition = valueCondition;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(String idCondition) {
        this.idCondition = idCondition;
    }
}
