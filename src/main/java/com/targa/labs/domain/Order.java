package com.targa.labs.domain;

import com.targa.labs.domain.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity{

    @NotNull
    @Column(name = "total_price",precision = 10,scale = 2,nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status;

    @Embedded
    private Address shipmentAddress;

    @Column(name = "shipped")
    private ZonedDateTime shipped;

    @OneToOne
    private Payment payment;

    @OneToOne
    private Cart cart;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(BigDecimal price, OrderStatus status, Address shipmentAddress, ZonedDateTime shipped,
                 Payment payment, Cart cart, Set<OrderItem> orderItems) {
        this.price = price;
        this.status = status;
        this.shipmentAddress = shipmentAddress;
        this.shipped = shipped;
        this.payment = payment;
        this.cart = cart;
        this.orderItems = orderItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Address getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(Address shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public ZonedDateTime getShipped() {
        return shipped;
    }

    public void setShipped(ZonedDateTime shipped) {
        this.shipped = shipped;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(price, order.price) && status == order.status &&
                Objects.equals(shipmentAddress, order.shipmentAddress) &&
                Objects.equals(shipped, order.shipped) &&
                Objects.equals(payment, order.payment) && Objects.equals(cart, order.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, status, shipmentAddress, shipped, payment, cart);
    }
}
