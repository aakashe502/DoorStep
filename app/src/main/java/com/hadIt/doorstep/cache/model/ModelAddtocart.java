package com.hadIt.doorstep.cache.model;

public class ModelAddtocart {
    public String id;
    public String productImage;
    public String productName;
    public String productRate;
    public String quantity;

    public ModelAddtocart(String id, String productImage, String productName, String productRate, String quantity) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.productRate = productRate;
        this.quantity = quantity;
    }

    public ModelAddtocart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductRate() {
        return productRate;
    }

    public void setProductRate(String productRate) {
        this.productRate = productRate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
