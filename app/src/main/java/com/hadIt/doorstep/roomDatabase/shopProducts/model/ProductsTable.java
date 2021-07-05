package com.hadIt.doorstep.roomDatabase.shopProducts.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "products", indices = @Index(value = {"productId"}, unique = true))
public class ProductsTable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String productId;

    @ColumnInfo(name = "productCategory")
    private String productCategory;
    @ColumnInfo(name = "productDescription")
    private String productDescription;
    @ColumnInfo(name = "productIcon")
    private String productIcon;
    @ColumnInfo(name = "productName")
    private String productName;
    @ColumnInfo(name = "productPrice")
    private String productPrice;
    @ColumnInfo(name = "productQuantity")
    private String productQuantity;
    @ColumnInfo(name = "shopUid")
    private String shopUid;
    @ColumnInfo(name = "unit")
    private String unit;

    public ProductsTable() {
    }

    public ProductsTable(@NonNull String productId, String productCategory, String productDescription, String productIcon, String productName, String productPrice, String productQuantity, String shopUid, String unit) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.shopUid = shopUid;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }
}
