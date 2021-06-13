package com.hadIt.doorstep.RoomDb.ShopProducts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.hadIt.doorstep.cache.model.AdminProductModel;

import java.util.List;

@Entity(tableName = "Shops" ,indices = @Index(value = {"uid"}, unique = true))
public class RoomData {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String uid;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "option_values")
    private List<AdminProductModel> optionValues;
}
