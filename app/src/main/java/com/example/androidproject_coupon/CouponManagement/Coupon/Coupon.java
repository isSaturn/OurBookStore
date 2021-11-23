package com.example.androidproject_coupon.CouponManagement.Coupon;

public class Coupon  {
    private String name, code, eStart, eEnd, id;
    private Integer value, valueCondition, idType, idCondition;
    private Integer imgUrl;

    public Coupon(String id,String code,String name, String eStart, String eEnd, Integer value, Integer valueCondition, Integer idCondition, Integer idType,Integer imgUrl){
        this.code = code;
        this.id = id;
        this.name = name;
        this.valueCondition = valueCondition;
        this.idCondition = idCondition;
        this.idType = idType;
        this.value = value;
        this.imgUrl= imgUrl;
        this.eStart = eStart;
        this.eEnd = eEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Integer getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(Integer idCondition) {
        this.idCondition = idCondition;
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
