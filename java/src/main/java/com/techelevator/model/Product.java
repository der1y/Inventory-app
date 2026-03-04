package com.techelevator.model;

public class Product {

    private int id;

    private String upc;
    private String name;
    private int categoryId;
    private int default_bottle_ml;
    private boolean is_active;

    public Product(int id, String name, int categoryId, int default_bottle_ml, boolean is_active) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.default_bottle_ml = default_bottle_ml;
        this.is_active = is_active;
    }

    public Product(int id, String upc, String name, int categoryId, int default_bottle_ml, boolean is_active) {
        this.id = id;
        this.upc = upc;
        this.name = name;
        this.categoryId = categoryId;
        this.default_bottle_ml = default_bottle_ml;
        this.is_active = is_active;
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

    public int getDefault_bottle_ml() {
        return default_bottle_ml;
    }

    public void setDefault_bottle_ml(int default_bottle_ml) {
        this.default_bottle_ml = default_bottle_ml;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", upc='" + upc + '\'' +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", default_bottle_ml=" + default_bottle_ml +
                ", is_active=" + is_active +
                '}';
    }
}
