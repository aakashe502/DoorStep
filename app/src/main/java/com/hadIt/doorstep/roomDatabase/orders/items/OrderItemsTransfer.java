package com.hadIt.doorstep.roomDatabase.orders.items;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

public interface OrderItemsTransfer {
    public void setOrderItems(OrderItemsRoomModel orderItemsRoomModel);
    public void deleteOrderItems(OrderItemsRoomModel orderItemsRoomModel);
}
