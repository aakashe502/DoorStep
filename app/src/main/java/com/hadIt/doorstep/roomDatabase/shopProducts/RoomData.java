package com.hadIt.doorstep.roomDatabase.shopProducts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.hadIt.doorstep.cache.model.ProductModel;

import java.util.List;

@Entity(tableName = "shops" ,indices = @Index(value = {"uid"}, unique = true))
public class RoomData {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String uid;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "option_values")
    private List<ProductModel> optionValues;

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public List<ProductModel> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<ProductModel> optionValues) {
        this.optionValues = optionValues;
    }
}
