package com.hadIt.doorstep.roomDatabase.orders.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

import java.util.List;

public class OrderDetailsViewModel extends AndroidViewModel {
    private OrderDetailsRepository orderDetailsRepository;
    private LiveData<List<OrderDetailsRoomModel>> getAllData;

    public OrderDetailsViewModel(@NonNull Application application, String sellerUid) {
        super(application);
        orderDetailsRepository = new OrderDetailsRepository(application, sellerUid);
        getAllData=orderDetailsRepository.getAllData();
    }

    public void insert(OrderDetailsRoomModel dataList)
    {
        orderDetailsRepository.insert(dataList);
    }

    public LiveData<List<OrderDetailsRoomModel>> getAllOrders() {
        return getAllData;
    }

    public void deleteAll()
    {
        orderDetailsRepository.refreshDb();
    }

    public void delete(OrderDetailsRoomModel orderDetailsRoomModel)
    {
        orderDetailsRepository.deleteProductUsingOrderId(orderDetailsRoomModel);
    }
}