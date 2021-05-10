package com.hadIt.doorstep.cache.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "data" ,indices = @Index(value = {"id"},unique = true))
public class Data {

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

    public Data(String id, String name, String rate, String image, String quantity) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.image = image;
        this.quantity = quantity;
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
}
