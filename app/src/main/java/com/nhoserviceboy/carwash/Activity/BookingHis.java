package com.nhoserviceboy.carwash.Activity;

import java.util.Date;

public class BookingHis {

    String current_date_book , ed_name ,ed_mobile , service_date,car_image, service_time, ed_address ,ed_landmark , ed_email ,   car_name ,washname_tv ,  price_total_final ,
            user_id , wash_count , month_count ,end_date , paymenttype ,referenceId ,Assigned_to,Booking_status, dailybooking,exdate,carModelNumber,packageId;

    String u_name;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDailybooking() {
        return dailybooking;
    }

    public void setDailybooking(String dailybooking) {
        this.dailybooking = dailybooking;
    }

    public String getExdate() {
        return exdate;
    }

    public void setExdate(String exdate) {
        this.exdate = exdate;
    }

    String u_email;
    String u_Address;
    String u_Landmark;

    public String getBooking_status() {
        return Booking_status;
    }

    public void setBooking_status(String booking_status) {
        Booking_status = booking_status;
    }

    String u_latitude;
    String u_phone;
    String u_longitude;
    Date modifyDate;


    public Date getModifyDate()
    {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAssigned_to_uid() {
        return assigned_to_uid;
    }

    public void setAssigned_to_uid(String assigned_to_uid) {
        this.assigned_to_uid = assigned_to_uid;
    }

    String assigned_to_uid ;


    public BookingHis(String current_date_book, String paymenttype, String referenceId, String ed_name, String ed_mobile, String service_date, String service_time, String ed_address, String ed_landmark, String ed_email,
                      String car_name, String washname_tv, String price_total_final, String user_id, String wash_count, String month_count ,
                      String car_image , String  end_date , String Assigned_to   , String u_name, String u_email ,
                      String     u_Address , String  u_Landmark ,
                      String    u_latitude , String u_longitude , String u_phone,String assigned_to_uid,  Date modifyDate,String Booking_status,String dailybooking,String exdate,String carModelNumber,String packageId)
    {
        this.Booking_status=Booking_status;
        this.modifyDate=modifyDate;
        this.current_date_book = current_date_book;
        this.ed_name = ed_name;
        this.ed_mobile = ed_mobile;
        this.service_date = service_date;
        this.service_time = service_time;
        this.ed_address = ed_address;
        this.ed_landmark = ed_landmark;
        this.ed_email = ed_email;
        this.car_name = car_name;
        this.washname_tv = washname_tv;
        this.price_total_final = price_total_final;
        this.user_id = user_id;
        this.wash_count = wash_count;
        this.month_count = month_count;
        this.car_image = car_image;
        this.end_date = end_date;
        this.paymenttype = paymenttype;
        this.referenceId = referenceId;
        this.Assigned_to = Assigned_to;

        this.u_name = u_name;
        this.u_email = u_email;
        this.u_Address = u_Address;
        this.u_Landmark = u_Landmark;
        this.u_latitude = u_latitude;
        this.u_longitude = u_longitude;
        this.u_phone = u_phone;
        this.assigned_to_uid=assigned_to_uid;
        this.dailybooking=dailybooking;
        this.exdate=exdate;
        this.carModelNumber=carModelNumber;
        this.packageId=packageId;




    }

    public String getCarModelNumber() {
        return carModelNumber;
    }

    public void setCarModelNumber(String carModelNumber) {
        this.carModelNumber = carModelNumber;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_Address() {
        return u_Address;
    }

    public void setU_Address(String u_Address) {
        this.u_Address = u_Address;
    }

    public String getU_Landmark() {
        return u_Landmark;
    }

    public void setU_Landmark(String u_Landmark) {
        this.u_Landmark = u_Landmark;
    }

    public String getU_latitude() {
        return u_latitude;
    }

    public void setU_latitude(String u_latitude) {
        this.u_latitude = u_latitude;
    }

    public String getU_longitude() {
        return u_longitude;
    }

    public void setU_longitude(String u_longitude) {
        this.u_longitude = u_longitude;
    }

    public String getAssigned_to() {
        return Assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        Assigned_to = assigned_to;
    }

    public String getCurrent_date_book() {
        return current_date_book;
    }

    public void setCurrent_date_book(String current_date_book) {
        this.current_date_book = current_date_book;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getEd_name() {
        return ed_name;
    }

    public void setEd_name(String ed_name) {
        this.ed_name = ed_name;
    }

    public String getEd_mobile() {
        return ed_mobile;
    }

    public void setEd_mobile(String ed_mobile) {
        this.ed_mobile = ed_mobile;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getEd_address() {
        return ed_address;
    }

    public void setEd_address(String ed_address) {
        this.ed_address = ed_address;
    }

    public String getEd_landmark() {
        return ed_landmark;
    }

    public void setEd_landmark(String ed_landmark) {
        this.ed_landmark = ed_landmark;
    }

    public String getEd_email() {
        return ed_email;
    }

    public void setEd_email(String ed_email) {
        this.ed_email = ed_email;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getWashname_tv() {
        return washname_tv;
    }

    public void setWashname_tv(String washname_tv) {
        this.washname_tv = washname_tv;
    }

    public String getPrice_total_final() {
        return price_total_final;
    }

    public void setPrice_total_final(String price_total_final) {
        this.price_total_final = price_total_final;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWash_count() {
        return wash_count;
    }

    public void setWash_count(String wash_count) {
        this.wash_count = wash_count;
    }

    public String getMonth_count() {
        return month_count;
    }

    public void setMonth_count(String month_count) {
        this.month_count = month_count;
    }
}
