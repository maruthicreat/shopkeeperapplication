package com.example.maruthiraja.shopkeeperapp;

/**
 * Created by maruthiraja on 3/22/2018.
 */

public class GetPurchaseData {

    private String amountpayable;
    private String ispayed;

    public String getAmountpayable() {
        return amountpayable;
    }

    public void setAmountpayable(String amountpayable) {
        this.amountpayable = amountpayable;
    }

    public String getIspayed() {
        return ispayed;
    }

    public void setIspayed(String ispayed) {
        this.ispayed = ispayed;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getItemrating() {
        return itemrating;
    }

    public void setItemrating(String itemrating) {
        this.itemrating = itemrating;
    }

    private String itemid;
    private String paymentMode;
    private String itemimage;
    private String itemname;
    private String itemrating;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;

    public String getOrderat() {
        return orderat;
    }

    public void setOrderat(String orderat) {
        this.orderat = orderat;
    }

    private String orderat;

    public GetPurchaseData(String amountpayable, String ispayed, String itemid, String paymentMode, String itemimage, String itemname, String purchaseTime, String orderat ,String price) {
        this.amountpayable = amountpayable;
        this.ispayed = ispayed;
        this.itemid = itemid;
        this.price = price;
        this.paymentMode = paymentMode;
        this.itemimage = itemimage;
        this.itemname = itemname;
        this.purchaseTime = purchaseTime;
        this.itemrating = itemrating;
        this.orderat = orderat;
    }

    private String purchaseTime;

    public GetPurchaseData()
    {
    }
}
