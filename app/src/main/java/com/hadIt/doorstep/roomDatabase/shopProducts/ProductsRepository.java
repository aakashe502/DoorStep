package com.hadIt.doorstep.roomDatabase.shopProducts;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductsRepository {
    private DatabaseRoom dataDatabase;
    private LiveData<List<ProductsTable>> getAllData;


    public ProductsRepository(Application application, String shopUid)
    {
        dataDatabase=DatabaseRoom.getInstance(application);
        getAllData=dataDatabase.Daodata().getDataForShopAndCategory(shopUid);
    }

    public void insert(final ProductsTable dataList)
    {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dataDatabase.Daodata().insert(dataList);
            }
        });
    }

    public  LiveData<List<ProductsTable>> getAllData(String shopUid)
    {
        return dataDatabase.Daodata().getDataForShopAndCategory(shopUid);
    }

    public void refreshDb()
    {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dataDatabase.Daodata().refreshDb();
            }
        });
    }

    public void delete(final String productId)
    {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dataDatabase.Daodata().delete(productId);
            }
        });
    }

    public void deleteProductUsingShopId(final String shopUid){
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dataDatabase.Daodata().deleteProductUsingShopId(shopUid);
            }
        });
    }
}
