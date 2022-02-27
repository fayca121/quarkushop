package com.targa.labs.dto;

public class PaymentDto {
    private Long id;
    private String paypalPaymentId;
    private String status;
    private Long orderId;

    public PaymentDto() {
    }

    public PaymentDto(Long id, String paypalPaymentId, String status, Long orderId) {
        this.id = id;
        this.paypalPaymentId = paypalPaymentId;
        this.status = status;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
