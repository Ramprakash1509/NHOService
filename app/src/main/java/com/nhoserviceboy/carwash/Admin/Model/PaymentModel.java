package com.nhoserviceboy.carwash.Admin.Model;

public class PaymentModel {
    String transactionId;
    String PaymentMode;
    String orderId;
    String status;
    String orderAmount;
    String userId;
    String userName;

    public PaymentModel(String transactionId, String paymentMode, String orderId, String status, String orderAmount,String userId,String userName) {
        this.transactionId = transactionId;
        PaymentMode = paymentMode;
        this.orderId = orderId;
        this.status = status;
        this.orderAmount = orderAmount;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }
}
