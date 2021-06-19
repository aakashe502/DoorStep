package com.hadIt.doorstep.roomDatabase.orders.items;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

public class OrderItemsRepository {
    private OrderItemsRoomDb orderItemsRoomDb;
    private LiveData<String> getItems;

    public OrderItemsRepository(Application application, String orderId)
    {
        orderItemsRoomDb = OrderItemsRoomDb.getInstance(application);
        getItems = orderItemsRoomDb.Query().getOrderItemsForOrderId(orderId);
    }

    public void insert(OrderItemsRoomModel orderItemsRoomModel) {
        new OrderItemsRepository.InsertAsynTask1(orderItemsRoomDb).execute(orderItemsRoomModel);
    }

    public  LiveData<String> getAllData()
    {
        return getItems;
    }

    public LiveData<String> getOrderDetailsForOrderId(String orderId){
        return orderItemsRoomDb.Query().getOrderItemsForOrderId(orderId);
    }

    public void refreshDb()
    {
        new OrderItemsRepository.DeleteAllAsynTask(orderItemsRoomDb).execute();
    }

    public void deleteProductUsingModelObj(OrderItemsRoomModel orderItemsRoomModel){
        new OrderItemsRepository.DeleteProductUsingOrderId(orderItemsRoomDb).execute(orderItemsRoomModel);
    }

    static class InsertAsynTask1 extends AsyncTask<OrderItemsRoomModel,Void,Void> {
        private ItemsQuery itemsQuery;
        InsertAsynTask1(OrderItemsRoomDb orderItemsRoomDb)
        {
            itemsQuery =orderItemsRoomDb.Query();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(OrderItemsRoomModel... lists) {
            itemsQuery.insert(lists[0]);
            return null;
        }
    }

    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
        private ItemsQuery itemsQuery;
        DeleteAllAsynTask(OrderItemsRoomDb orderItemsRoomDb)
        {
            itemsQuery =orderItemsRoomDb.Query();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            itemsQuery.refreshDb();
            return null;
        }
    }

    public static class DeleteProductUsingOrderId extends AsyncTask<OrderItemsRoomModel, Void, Void> {
        private ItemsQuery itemsQuery;
        public DeleteProductUsingOrderId(OrderItemsRoomDb orderItemsRoomDb) {
            itemsQuery = orderItemsRoomDb.Query();
        }

        @Override
        protected Void doInBackground(OrderItemsRoomModel... orderItemsRoomModels) {
            itemsQuery.deleteProductUsingModelObj(orderItemsRoomModels[0]);
            return null;
        }
    }
}
