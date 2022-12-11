package com.nhoserviceboy.carwash.Admin.Model;

public class BookingModels
{
    String userId,packageId,Redeem,bookingDate,modifyDate,expireDate,washCount,status,packageAmount,currentDate,modelId,paymentstatus;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BookingModels(String userId, String packageId, String redeem, String bookingDate, String modifyDate, String expireDate, String washCount,String status,String packageAmount,String currentDate,String modelId,String paymentstatus ) {
        this.userId = userId;
        this.packageId = packageId;
        Redeem = redeem;
        this.bookingDate = bookingDate;
        this.modifyDate = modifyDate;
        this.expireDate = expireDate;
        this.washCount = washCount;
        this.status=status;
        this.packageAmount=packageAmount;
        this.currentDate=currentDate;
        this.modelId=modelId;
        this.paymentstatus=paymentstatus;

    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getRedeem() {
        return Redeem;
    }

    public void setRedeem(String redeem) {
        Redeem = redeem;
    }



    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getWashCount() {
        return washCount;
    }

    public void setWashCount(String washCount) {
        this.washCount = washCount;
    }
}
