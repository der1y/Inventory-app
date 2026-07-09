package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private int id;

    private String upc;
    private String name;

    // Java stays camelCase; @JsonProperty pins the snake_case JSON contract the
    // client sends/expects (this endpoint's payloads use snake_case).
    @JsonProperty("category_id")
    private int categoryId;

    @JsonProperty("default_bottle_ml")
    private int defaultBottleMl;

    @JsonProperty("is_active")
    private boolean active;

    public Product(int id, String name, int categoryId, int defaultBottleMl, boolean active) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.defaultBottleMl = defaultBottleMl;
        this.active = active;
    }

    public Product(int id, String upc, String name, int categoryId, int defaultBottleMl, boolean active) {
        this.id = id;
        this.upc = upc;
        this.name = name;
        this.categoryId = categoryId;
        this.defaultBottleMl = defaultBottleMl;
        this.active = active;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDefaultBottleMl() {
        return defaultBottleMl;
    }

    public void setDefaultBottleMl(int defaultBottleMl) {
        this.defaultBottleMl = defaultBottleMl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", upc='" + upc + '\'' +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", defaultBottleMl=" + defaultBottleMl +
                ", active=" + active +
                '}';
    }
}
