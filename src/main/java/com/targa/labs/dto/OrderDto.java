package com.targa.labs.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;
    private String status;
    private ZonedDateTime shipped;
    private Long paymentId;
    private AddressDto shipmentAddress;
    private Set<OrderItemDto> orderItems;
    private CartDto cart;

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal totalPrice, String status,
                    ZonedDateTime shipped, Long paymentId,
                    AddressDto shipmentAddress, Set<OrderItemDto> orderItems, CartDto cart) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shipped = shipped;
        this.paymentId = paymentId;
        this.shipmentAddress = shipmentAddress;
        this.orderItems = orderItems;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getShipped() {
        return shipped;
    }

    public void setShipped(ZonedDateTime shipped) {
        this.shipped = shipped;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public AddressDto getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(AddressDto shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public Set<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }
}
