package com.hadIt.doorstep.roomDatabase.cart;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

public class DataRepository {
    private DataDatabase dataDatabase;
    private LiveData<List<Data>> getAllData;

    public DataRepository(Application application)
    {
        dataDatabase = DataDatabase.getInstance(application);
        getAllData = dataDatabase.dataDao().getAllData();
    }

    public void insert(Data dataList)
    {
        new InsertAsynTask(dataDatabase).execute(dataList);
    }

    public LiveData<Data> getProductObserverWithId(String productId) {
        return dataDatabase.dataDao().getProductObserverWithId(productId);
    }

    public  LiveData<List<Data>> getAllData()
    {
        return getAllData;
    }

    public void deleteAll()
    {
        new DeleteAllAsynTask(dataDatabase).execute();
    }

    public void delete(String id)
    {
        new DeleteAsynTask(dataDatabase).execute(id);
    }


    static class InsertAsynTask extends AsyncTask<Data,Void,Void>
    {
        private DataDao dataDao;
        InsertAsynTask(DataDatabase dataDatabase)
        {
            dataDao=dataDatabase.dataDao();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(Data... lists) {
            dataDao.insert(lists[0]);
            return null;
        }
    }

    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
        private DataDao dataDao;
        DeleteAllAsynTask(DataDatabase dataDatabase)
        {
            dataDao=dataDatabase.dataDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }

    static class DeleteAsynTask  extends AsyncTask<String,Void,Void>{
        private DataDao dataDao;
        public DeleteAsynTask(DataDatabase dataDatabase) {
            dataDao=dataDatabase.dataDao();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dataDao.delete(strings[0]);
            return null;
        }
    }
}
