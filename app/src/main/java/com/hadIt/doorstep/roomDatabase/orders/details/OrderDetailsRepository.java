package com.hadIt.doorstep.roomDatabase.orders.details;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderDetailsRepository {
    private DetailsRoomDb detailsRoomDb;
    private LiveData<List<OrderDetailsRoomModel>> getAllData;

    public OrderDetailsRepository(Application application, String buyerUid)
    {
        detailsRoomDb = DetailsRoomDb.getInstance(application);
        getAllData = detailsRoomDb.OrderQuery().getAllOrders(buyerUid);
    }

    public void insert(final OrderDetailsRoomModel dataList) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                detailsRoomDb.OrderQuery().insert(dataList);
            }
        });
    }

    public  LiveData<List<OrderDetailsRoomModel>> getAllData()
    {
        return getAllData;
    }

    public LiveData<OrderDetailsRoomModel> getOrderDetailsForOrderId(String orderId){
        return detailsRoomDb.OrderQuery().getOrderDetailsForOrderId(orderId);
    }

    public void refreshDb()
    {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                detailsRoomDb.OrderQuery().refreshDb();
            }
        });
    }

    public void deleteProductUsingOrderId(final OrderDetailsRoomModel orderDetailsRoomModel){
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                detailsRoomDb.OrderQuery().deleteProductUsingObject(orderDetailsRoomModel);
            }
        });
    }

//    static class InsertAsynTask1 extends AsyncTask<OrderDetailsRoomModel,Void,Void> {
//        private OrdersQuery ordersQuery;
//        InsertAsynTask1(DetailsRoomDb detailsRoomDb)
//        {
//            ordersQuery =detailsRoomDb.OrderQuery();
//        }
//        @SafeVarargs
//        @Override
//        protected final Void doInBackground(OrderDetailsRoomModel... lists) {
//            ordersQuery.insert(lists[0]);
//            return null;
//        }
//    }
//
//    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
//        private OrdersQuery ordersQuery;
//        DeleteAllAsynTask(DetailsRoomDb dataDatabase)
//        {
//            ordersQuery =dataDatabase.OrderQuery();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            ordersQuery.refreshDb();
//            return null;
//        }
//    }
//
//    public static class DeleteProductUsingOrderId extends AsyncTask<OrderDetailsRoomModel, Void, Void> {
//        private OrdersQuery ordersQuery;
//        public DeleteProductUsingOrderId(DetailsRoomDb detailsRoomDb) {
//            ordersQuery = detailsRoomDb.OrderQuery();
//        }
//
//        @Override
//        protected Void doInBackground(OrderDetailsRoomModel... strings) {
//            ordersQuery.deleteProductUsingObject(strings[0]);
//            return null;
//        }
//    }
}
