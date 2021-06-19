package com.hadIt.doorstep.roomDatabase.orders.items.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orderItems", indices = @Index(value = {"orderId"}, unique = true))
public class OrderItemsRoomModel implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String orderId;

    @ColumnInfo(name = "productItems")
    private String productItemList;

    public OrderItemsRoomModel(@NonNull String orderId, String productItemList) {
        this.orderId = orderId;
        this.productItemList = productItemList;
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }

    public String getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(String productItemList) {
        this.productItemList = productItemList;
    }
}
