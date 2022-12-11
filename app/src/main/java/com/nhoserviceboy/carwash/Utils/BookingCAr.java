package com.nhoserviceboy.carwash.Utils;

public class BookingCAr {

    private String name ,mobile, time,date,address, landmark ,total_amount ,car ,wash;


    public BookingCAr(String name, String mobile, String time, String date, String address, String landmark, String total_amount, String car, String wash) {
        this.name = name;
        this.mobile = mobile;
        this.time = time;
        this.date = date;
        this.address = address;
        this.landmark = landmark;
        this.total_amount = total_amount;
        this.car = car;
        this.wash = wash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }
}
