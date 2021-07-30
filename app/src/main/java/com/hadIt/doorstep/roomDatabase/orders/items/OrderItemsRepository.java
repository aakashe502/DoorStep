package com.hadIt.doorstep.roomDatabase.orders.items;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderItemsRepository {
    private OrderItemsRoomDb orderItemsRoomDb;
    private LiveData<String> getItems;

    public OrderItemsRepository(Application application, String orderId)
    {
        orderItemsRoomDb = OrderItemsRoomDb.getInstance(application);
        getItems = orderItemsRoomDb.Query().getOrderItemsForOrderId(orderId);
    }

    public void insert(final OrderItemsRoomModel orderItemsRoomModel) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsRoomDb.Query().insert(orderItemsRoomModel);
            }
        });
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
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsRoomDb.Query().refreshDb();
            }
        });
    }

    public void deleteProductUsingModelObj(final OrderItemsRoomModel orderItemsRoomModel){
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsRoomDb.Query().deleteProductUsingModelObj(orderItemsRoomModel);
            }
        });
    }

//    static class InsertAsynTask1 extends AsyncTask<OrderItemsRoomModel,Void,Void> {
//        private ItemsQuery itemsQuery;
//        InsertAsynTask1(OrderItemsRoomDb orderItemsRoomDb)
//        {
//            itemsQuery =orderItemsRoomDb.Query();
//        }
//        @SafeVarargs
//        @Override
//        protected final Void doInBackground(OrderItemsRoomModel... lists) {
//            itemsQuery.insert(lists[0]);
//            return null;
//        }
//    }
//
//    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
//        private ItemsQuery itemsQuery;
//        DeleteAllAsynTask(OrderItemsRoomDb orderItemsRoomDb)
//        {
//            itemsQuery =orderItemsRoomDb.Query();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            itemsQuery.refreshDb();
//            return null;
//        }
//    }
//
//    public static class DeleteProductUsingOrderId extends AsyncTask<OrderItemsRoomModel, Void, Void> {
//        private ItemsQuery itemsQuery;
//        public DeleteProductUsingOrderId(OrderItemsRoomDb orderItemsRoomDb) {
//            itemsQuery = orderItemsRoomDb.Query();
//        }
//
//        @Override
//        protected Void doInBackground(OrderItemsRoomModel... orderItemsRoomModels) {
//            itemsQuery.deleteProductUsingModelObj(orderItemsRoomModels[0]);
//            return null;
//        }
//    }
}
