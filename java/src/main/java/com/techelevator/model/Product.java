package com.techelevator.model;

public class Product {

    private int id;
    private String sku;
    private String name;
    private int category_id;
    private int default_bottle_ml;
    private boolean is_active;

    public Product(int id, String sku, String name, int category_id, int default_bottle_ml, boolean is_active) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.category_id = category_id;
        this.default_bottle_ml = default_bottle_ml;
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", category_id=" + category_id +
                ", default_bottle_ml=" + default_bottle_ml +
                ", is_active=" + is_active +
                '}';
    }
}
