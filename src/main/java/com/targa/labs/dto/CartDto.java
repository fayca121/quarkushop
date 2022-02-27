package com.targa.labs.dto;

public class CartDto {
    private Long id;
    private CustomerDto customer;
    private String status;

    public CartDto() {
    }

    public CartDto(Long id, CustomerDto customer, String status) {
        this.id = id;
        this.customer = customer;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
