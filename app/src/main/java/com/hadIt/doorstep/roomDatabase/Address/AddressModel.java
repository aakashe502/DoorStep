package com.hadIt.doorstep.roomDatabase.Address;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "address" ,indices = @Index(value = {"id"},unique = true))
public class AddressModel {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "rate")
    private String rate;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "shopUid")
    private String shopUid;

    public AddressModel(String id, String name, String rate, String image, String quantity, String shopUid) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.image = image;
        this.quantity = quantity;
        this.shopUid = shopUid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }
}
