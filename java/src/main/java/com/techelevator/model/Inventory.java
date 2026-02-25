package com.techelevator.model;

import java.sql.Timestamp;

public class Inventory {

    private int id;
    private int product_id;
    private int location_id;
    private int purchase_id;
    private Timestamp received_at;
    private int full_ml;
    private int remaining_ml;

    public Inventory(int id, int product_id, int location_id, int purchase_id, Timestamp received_at, int full_ml, int remaining_ml) {
        this.id = id;
        this.product_id = product_id;
        this.location_id = location_id;
        this.purchase_id = purchase_id;
        this.received_at = received_at;
        this.full_ml = full_ml;
        this.remaining_ml = remaining_ml;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Timestamp getReceived_at() {
        return received_at;
    }

    public void setReceived_at(Timestamp received_at) {
        this.received_at = received_at;
    }

    public int getFull_ml() {
        return full_ml;
    }

    public void setFull_ml(int full_ml) {
        this.full_ml = full_ml;
    }

    public int getRemaining_ml() {
        return remaining_ml;
    }

    public void setRemaining_ml(int remaining_ml) {
        this.remaining_ml = remaining_ml;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", location_id=" + location_id +
                ", purchase_id=" + purchase_id +
                ", received_at=" + received_at +
                ", full_ml=" + full_ml +
                ", remaining_ml=" + remaining_ml +
                '}';
    }
}
