package com.nhoserviceboy.carwash.Activity.dailyService;

public class DailyServiceDataModel {
    String serviceBoyId;
    String serviceBoyName;
    String bookingId;
    String ServiceName;
    String packageId;
    String date;
    String time;
    String status;
    String pin;
    boolean pinStatus;
    String latitude;
    String longitude;
    String userName;
    String userNumber;
    String userAddress;
    String userLandmark;
    String washCount;
    String userId;

   /* public DailyServiceDataModel(String serviceBoyId, String serviceBoyName, String bookingId, String serviceName, String packageId, String date, String time, String status, String pin,
                                 boolean pinStatus, String latitude, String longitude, String userName,
            String userNumber,
            String userAddress,
            String userLandmark,  String washCount, String userId) {
        this.serviceBoyId = serviceBoyId;
        this.serviceBoyName = serviceBoyName;
        this.bookingId = bookingId;
        ServiceName = serviceName;
        this.packageId = packageId;
        this.date = date;
        this.time = time;
        this.status = status;
        this.pin = pin;
        this.pinStatus = pinStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userAddress = userAddress;
        this.userLandmark = userLandmark;
        this.washCount = washCount;
        this.userId = userId;
    }
*/

    public DailyServiceDataModel(String serviceBoyId, String serviceBoyName, String bookingId, String serviceName, String packageId, String date, String time, String status, String pin, boolean pinStatus, String latitude, String longitude, String userName, String userNumber, String userAddress, String userLandmark, String washCount, String userId) {
        this.serviceBoyId = serviceBoyId;
        this.serviceBoyName = serviceBoyName;
        this.bookingId = bookingId;
        ServiceName = serviceName;
        this.packageId = packageId;
        this.date = date;
        this.time = time;
        this.status = status;
        this.pin = pin;
        this.pinStatus = pinStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userAddress = userAddress;
        this.userLandmark = userLandmark;
        this.washCount = washCount;
        this.userId = userId;
    }

    public String getServiceBoyId() {
        return serviceBoyId;
    }

    public void setServiceBoyId(String serviceBoyId) {
        this.serviceBoyId = serviceBoyId;
    }

    public String getServiceBoyName() {
        return serviceBoyName;
    }

    public void setServiceBoyName(String serviceBoyName) {
        this.serviceBoyName = serviceBoyName;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(boolean pinStatus) {
        this.pinStatus = pinStatus;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLandmark() {
        return userLandmark;
    }

    public void setUserLandmark(String userLandmark) {
        this.userLandmark = userLandmark;
    }

    public String getWashCount() {
        return washCount;
    }

    public void setWashCount(String washCount) {
        this.washCount = washCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}