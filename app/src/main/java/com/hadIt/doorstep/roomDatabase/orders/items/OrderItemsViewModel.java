package com.hadIt.doorstep.roomDatabase.orders.items;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

public class OrderItemsViewModel extends AndroidViewModel {
    private OrderItemsRepository orderItemsRepository;
    private LiveData<String> getAllData;

    public OrderItemsViewModel(@NonNull Application application, String orderId) {
        super(application);
        orderItemsRepository = new OrderItemsRepository(application, orderId);
        getAllData=orderItemsRepository.getAllData();
    }

    public void insert(OrderItemsRoomModel orderItemsRoomModel)
    {
        orderItemsRepository.insert(orderItemsRoomModel);
    }

    public LiveData<String> getItemsForOrderId(String orderId) {
        return orderItemsRepository.getOrderDetailsForOrderId(orderId);
    }

    public void deleteAll()
    {
        orderItemsRepository.refreshDb();
    }

    public void delete(OrderItemsRoomModel orderItemsRoomModel)
    {
        orderItemsRepository.deleteProductUsingModelObj(orderItemsRoomModel);
    }
}