package com.hadIt.doorstep.cache.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String id, name, rate, quantity, image, unit;

    public Products() {
    }

    public Products(String id, String name, String rate, String quantity, String image,String  unit) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.quantity = quantity;
        this.image = image;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
