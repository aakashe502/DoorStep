package com.hadIt.doorstep.cache.model;

public class ProductModel {
    public String productPrice, productCategory, productDescription, productIcon, productId, productQuantity, productName, shopUid;

    public ProductModel(String productPrice, String productCategory, String productDescription, String productIcon, String productId, String productQuantity, String productName, String shopUid) {
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productName = productName;
        this.shopUid = shopUid;
    }

    public ProductModel() {
    }
}
