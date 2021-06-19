package com.hadIt.doorstep.roomDatabase.orders.items;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

@Dao
public interface ItemsQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderItemsRoomModel orderItemsRoomModel);

    @Delete
    void deleteProductUsingModelObj(OrderItemsRoomModel orderDetailsRoomModel);

    @Query("DELETE FROM orderItems")
    void refreshDb();

    @Query("SELECT productItems FROM orderItems WHERE orderId=:orderId")
    LiveData<String> getOrderItemsForOrderId(String orderId);
}
