package com.example.androidproject_coupon.InvoiceManagement.Invoice;

public class ProductBought {
    private String ID_Sach, Ma_Don_Hang;

    public ProductBought(String ID_Sach, String Ma_Don_Hang){
        this.ID_Sach = ID_Sach;
        this.Ma_Don_Hang = Ma_Don_Hang;
    }
    public String getID_Sach() {
        return ID_Sach;
    }

    public void setID_Sach(String ID_Sach) {
        this.ID_Sach = ID_Sach;
    }

    public String getMa_Don_Hang() {
        return Ma_Don_Hang;
    }

    public void setMa_Don_Hang(String Ma_Don_Hang) {
        this.Ma_Don_Hang = Ma_Don_Hang;
    }
}

