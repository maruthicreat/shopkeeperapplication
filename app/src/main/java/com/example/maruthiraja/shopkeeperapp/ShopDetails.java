package com.example.maruthiraja.shopkeeperapp;

/**
 * Created by maruthiraja on 2/17/2018.
 */

public class ShopDetails {
    private String Address,latitude,longitude,mail_id,password,shop_name,shop_type,user_id;


    public ShopDetails(String address, String latitude, String longitude, String mail_id, String password, String shop_name, String shop_type, String user_id) {
        Address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mail_id = mail_id;
        this.password = password;
        this.shop_name = shop_name;
        this.shop_type = shop_type;
        this.user_id = user_id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
