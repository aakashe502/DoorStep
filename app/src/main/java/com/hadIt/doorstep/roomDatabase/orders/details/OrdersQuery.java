package com.hadIt.doorstep.roomDatabase.orders.details;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

import java.util.List;

@Dao
public interface OrdersQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderDetailsRoomModel orderDetailsRoomModel);

    @Delete
    void deleteProductUsingOrderId(OrderDetailsRoomModel orderDetailsRoomModel);

    @Query("DELETE FROM orderDetails")
    void refreshDb();

    @Query("SELECT * FROM orderDetails WHERE orderId=:orderId")
    LiveData<OrderDetailsRoomModel> getOrderDetailsForOrderId(String orderId);

    @Query("SELECT * FROM orderDetails WHERE buyerUid=:buyerUid ORDER BY orderId DESC")
    LiveData<List<OrderDetailsRoomModel>> getAllOrders(String buyerUid);
}
