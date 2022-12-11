package com.nhoserviceboy.carwash.Activity;

public class RegisterModel {

     private String uitokenId;
    private String email;
    private String passsword;
    private String phone;
    private String username;
    private String userid;
    private String landmark_us;
    private String address_us;
    private String loginType;

    public String getUitokenId() {
        return uitokenId;
    }

    public void setUitokenId(String uitokenId) {
        this.uitokenId = uitokenId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
    double latitude , longitude;

    public RegisterModel(String email, String phone, String username , String Address , String Landmark , double latitude
            , double longitude, String loginType,String uid ,String uitokenId)
    {

        this.email = email;
        this.uitokenId=uitokenId;


        this.phone = phone;
        this.username = username;

        this.address_us = Address;
        this.landmark_us = Landmark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.loginType = loginType;
        this.uid = uid;
    }


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLandmark() {
        return landmark_us;
    }

    public void setLandmark(String landmark) {
        landmark_us = landmark;
    }

    public String getAddress() {
        return address_us;
    }

    public void setAddress(String address) {
        address_us = address;
    }

    public String getUser_id() {
        return userid;
    }

    public void setUser_id(String user_id) {
        userid = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}