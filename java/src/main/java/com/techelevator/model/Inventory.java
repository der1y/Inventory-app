package com.techelevator.model;

import java.sql.Timestamp;

public class Inventory {

    private int id;
    private int productId;
    private int locationId;
    private int purchaseId;
    private Timestamp received_at;
    private int full_ml;
    private int remaining_ml;

    public Inventory(int id, int productId, int locationId, int purchaseId, Timestamp received_at, int full_ml, int remaining_ml) {
        this.id = id;
        this.productId = productId;
        this.locationId = locationId;
        this.purchaseId = purchaseId;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
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
                ", productId=" + productId +
                ", locationId=" + locationId +
                ", purchaseId=" + purchaseId +
                ", received_at=" + received_at +
                ", full_ml=" + full_ml +
                ", remaining_ml=" + remaining_ml +
                '}';
    }
}
