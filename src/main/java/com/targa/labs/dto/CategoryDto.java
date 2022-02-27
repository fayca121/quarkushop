package com.targa.labs.dto;

public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private Long products;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description, Long products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProducts() {
        return products;
    }

    public void setProducts(Long products) {
        this.products = products;
    }
}
