package com.nhoserviceboy.carwash.Admin.Model;

import com.google.firebase.Timestamp;

import java.util.Date;

public class AssignedUser
{




    String St_car_image;
    String St_car_name;
    String St_washname_tv;
    String St_wash_count;
    String St_user_id;
    String St_referenceId;
    String St_price_total_final;
    String St_paymenttype;
    String St_month_count;
    String St_end_date;
    String St_service_time;
    String St_ed_name;
    String St_ed_mobile;
    String St_ed_landmark;
    String St_ed_address;
    String St_current_date_book;
    String St_service_date;
    String servies_boy_fId;
    String servies_boy_email;
    String servies_boy_phone;
    String servies_boy_user_id;
    String servies_boy_username;
    String u_longitude;
    String u_latitude;
    Date modifyDate;
    String flag;
    String booking_status;
    String dailybooking;
    String exdate;
    String carModelNumber;

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


    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getTraking_key() {
        return traking_key;
    }

    public void setTraking_key(String traking_key) {
        this.traking_key = traking_key;
    }

    String traking_key;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    String booking_id;

    public AssignedUser(String st_car_image, String st_car_name, String st_washname_tv, String st_wash_count, String st_user_id,
                        String st_referenceId, String st_price_total_final, String st_paymenttype, String st_month_count,
                        String st_end_date, String st_service_time, String st_ed_name, String st_ed_mobile, String st_ed_landmark,
                        String st_ed_address, String st_current_date_book, String st_service_date, String servies_boy_fId,
                        String servies_boy_email, String servies_boy_phone, String servies_boy_user_id, String servies_boy_username
                        , String u_longitude , String u_latitude, String booking_id, String key, Date modifyDate,
                        String flag,String booking_status, String dailybooking,
                                String exdate,String carModelNumber)
    {

         this.booking_status=booking_status;
        this.flag=flag;
         this.modifyDate=modifyDate;
        this.u_longitude=  u_longitude;
        this.u_latitude=  u_latitude;
        this.St_car_image=  st_car_image;
       this.St_car_name =   st_car_name;;
       this.St_washname_tv =  st_washname_tv;
       this.St_wash_count    =st_wash_count;
        this.St_user_id    =st_user_id;
        this.St_referenceId    =st_referenceId;
        this.St_price_total_final    =st_price_total_final;
        this.St_paymenttype    =st_paymenttype;
        this.St_month_count    =st_month_count;
        this.St_end_date     =st_end_date;
        this.St_service_time    =st_service_time;
       this.St_ed_name    =st_ed_name;
       this.St_ed_mobile     =st_ed_mobile;
       this.St_ed_landmark     =st_ed_landmark;
       this.St_ed_address     =st_ed_address;
       this.St_current_date_book     =st_current_date_book;
        this.St_service_date    =st_service_date;
        this.servies_boy_fId=servies_boy_fId;
       this.servies_boy_email=servies_boy_email;
        this.servies_boy_phone =servies_boy_phone;
       this.servies_boy_user_id =servies_boy_user_id;
       this.booking_id =booking_id;
       this.servies_boy_username=servies_boy_username;
       this.traking_key=key;
       this.dailybooking=dailybooking;
       this.exdate=exdate;
       this.carModelNumber=carModelNumber;

    }

    public String getCarModelNumber() {
        return carModelNumber;
    }

    public void setCarModelNumber(String carModelNumber) {
        this.carModelNumber = carModelNumber;
    }

    public String getU_longitude() {
        return u_longitude;
    }

    public void setU_longitude(String u_longitude) {
        this.u_longitude = u_longitude;
    }

    public String getU_latitude() {
        return u_latitude;
    }

    public void setU_latitude(String u_latitude) {
        this.u_latitude = u_latitude;
    }

    public String getSt_car_image() {
        return St_car_image;
    }

    public void setSt_car_image(String st_car_image) {
        St_car_image = st_car_image;
    }

    public String getSt_car_name() {
        return St_car_name;
    }

    public void setSt_car_name(String st_car_name) {
        St_car_name = st_car_name;
    }

    public String getSt_washname_tv() {
        return St_washname_tv;
    }

    public void setSt_washname_tv(String st_washname_tv) {
        St_washname_tv = st_washname_tv;
    }

    public String getSt_wash_count() {
        return St_wash_count;
    }

    public void setSt_wash_count(String st_wash_count) {
        St_wash_count = st_wash_count;
    }

    public String getSt_user_id() {
        return St_user_id;
    }

    public void setSt_user_id(String st_user_id) {
        St_user_id = st_user_id;
    }

    public String getSt_referenceId() {
        return St_referenceId;
    }

    public void setSt_referenceId(String st_referenceId) {
        St_referenceId = st_referenceId;
    }

    public String getSt_price_total_final() {
        return St_price_total_final;
    }

    public void setSt_price_total_final(String st_price_total_final) {
        St_price_total_final = st_price_total_final;
    }

    public String getSt_paymenttype() {
        return St_paymenttype;
    }

    public void setSt_paymenttype(String st_paymenttype) {
        St_paymenttype = st_paymenttype;
    }

    public String getSt_month_count() {
        return St_month_count;
    }

    public void setSt_month_count(String st_month_count) {
        St_month_count = st_month_count;
    }

    public String getSt_end_date() {
        return St_end_date;
    }

    public void setSt_end_date(String st_end_date) {
        St_end_date = st_end_date;
    }

    public String getSt_service_time() {
        return St_service_time;
    }

    public void setSt_service_time(String st_service_time) {
        St_service_time = st_service_time;
    }

    public String getSt_ed_name() {
        return St_ed_name;
    }

    public void setSt_ed_name(String st_ed_name) {
        St_ed_name = st_ed_name;
    }

    public String getSt_ed_mobile() {
        return St_ed_mobile;
    }

    public void setSt_ed_mobile(String st_ed_mobile) {
        St_ed_mobile = st_ed_mobile;
    }

    public String getSt_ed_landmark() {
        return St_ed_landmark;
    }

    public void setSt_ed_landmark(String st_ed_landmark) {
        St_ed_landmark = st_ed_landmark;
    }

    public String getSt_ed_address() {
        return St_ed_address;
    }

    public void setSt_ed_address(String st_ed_address) {
        St_ed_address = st_ed_address;
    }

    public String getSt_current_date_book() {
        return St_current_date_book;
    }

    public void setSt_current_date_book(String st_current_date_book) {
        St_current_date_book = st_current_date_book;
    }

    public String getSt_service_date() {
        return St_service_date;
    }

    public void setSt_service_date(String st_service_date) {
        St_service_date = st_service_date;
    }

    public String getServies_boy_fId() {
        return servies_boy_fId;
    }

    public void setServies_boy_fId(String servies_boy_fId) {
        this.servies_boy_fId = servies_boy_fId;
    }

    public String getServies_boy_email() {
        return servies_boy_email;
    }

    public void setServies_boy_email(String servies_boy_email) {
        this.servies_boy_email = servies_boy_email;
    }

    public String getServies_boy_phone() {
        return servies_boy_phone;
    }

    public void setServies_boy_phone(String servies_boy_phone) {
        this.servies_boy_phone = servies_boy_phone;
    }

    public String getServies_boy_user_id() {
        return servies_boy_user_id;
    }

    public void setServies_boy_user_id(String servies_boy_user_id) {
        this.servies_boy_user_id = servies_boy_user_id;
    }

    public String getServies_boy_username() {
        return servies_boy_username;
    }

    public void setServies_boy_username(String servies_boy_username) {
        this.servies_boy_username = servies_boy_username;
    }
}
