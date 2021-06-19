package com.hadIt.doorstep.roomDatabase.orders.details;

import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

public interface OrderDetailsTransfer {
    public void setOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel);
    public void deleteOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel);
}
