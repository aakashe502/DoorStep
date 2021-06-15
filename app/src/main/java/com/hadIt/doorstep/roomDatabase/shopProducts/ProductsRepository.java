package com.hadIt.doorstep.roomDatabase.shopProducts;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.cache.model.Products;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

public class ProductsRepository {
    private DatabaseRoom dataDatabase;
    private LiveData<List<ProductsTable>> getAllData;

    public ProductsRepository(Application application, String shopUid, String productCategory)
    {
        dataDatabase=DatabaseRoom.getInstance(application);
        getAllData=dataDatabase.Daodata().getDataForShopAndCategory(shopUid, productCategory);
    }

    public void insert(ProductsTable dataList)
    {
        new InsertAsynTask1(dataDatabase).execute(dataList);
    }

    public  LiveData<List<ProductsTable>> getAllData()
    {
        return getAllData;
    }

    public void refreshDb()
    {
        new DeleteAllAsynTask(dataDatabase).execute();
    }

    public void delete(String productId)
    {
        new DeleteAsynTask(dataDatabase).execute(productId);
    }

    public void deleteProductUsingShopId(String shopUid){
        new DeleteProductUsingShopId(dataDatabase).execute(shopUid);
    }

    static class InsertAsynTask1 extends AsyncTask<ProductsTable,Void,Void>
    {
        private DaoQuery dataDaoQuery;
        InsertAsynTask1(DatabaseRoom dataDatabase)
        {
            dataDaoQuery =dataDatabase.Daodata();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(ProductsTable... lists) {
            dataDaoQuery.insert(lists[0]);
            return null;
        }
    }

    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
        private DaoQuery dataDaoQuery;
        DeleteAllAsynTask(DatabaseRoom dataDatabase)
        {
            dataDaoQuery =dataDatabase.Daodata();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDaoQuery.refreshDb();
            return null;
        }
    }

    static class DeleteAsynTask  extends AsyncTask<String,Void,Void>{
        private DaoQuery dataDaoQuery;
        public DeleteAsynTask(DatabaseRoom dataDatabase) {
            dataDaoQuery = dataDatabase.Daodata();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dataDaoQuery.delete(strings[0]);
            return null;
        }
    }

    private class DeleteProductUsingShopId extends AsyncTask<String,Void,Void> {
        private DaoQuery dataDaoQuery;
        public DeleteProductUsingShopId(DatabaseRoom dataDatabase) {
            dataDaoQuery = dataDatabase.Daodata();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dataDaoQuery.deleteProductUsingShopId(strings[0]);
            return null;
        }
    }
}
