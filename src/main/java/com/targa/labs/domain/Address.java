package com.targa.labs.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Embeddable
public class Address {
    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @NotNull
    @Size(max = 10)
    @Column(name = "postcode",nullable = false,length = 10)
    private String postCode;

    @NotNull
    @Size(max = 2)
    @Column(name = "country",nullable = false,length = 2)
    private String country;

    public Address() {
    }

    public Address(String address1, String address2, String city,
                   String postCode, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return address1.equals(address.address1) && Objects.equals(address2, address.address2) &&
                city.equals(address.city) && postCode.equals(address.postCode) &&
                country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address1, address2, city, postCode, country);
    }
}
