package com.hadIt.doorstep.roomDatabase.orders;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.hadIt.doorstep.roomDatabase.orders.model.Data;

import java.util.List;

public class DataRepository {
    private DataDatabase dataDatabase;
    private LiveData<List<Data>> getAllData;
    private static String shopUid;

    public DataRepository(Application application)
    {
        dataDatabase = DataDatabase.getInstance(application);
        getAllData = dataDatabase.dataDao().getAllData();
    }

    public void insert(Data dataList)
    {
        new InsertAsynTask(dataDatabase).execute(dataList);
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

    public void getUid(){new GetUidAsyncTask(dataDatabase).execute();}

    static class GetUidAsyncTask extends AsyncTask<Data, Void, Data>
    {
        GetUidAsyncTask(DataDatabase dataDatabase)
        {
            shopUid = dataDatabase.dataDao().getShopUid();
        }

        @Override
        protected Data doInBackground(Data... data) {
            return null;
        }
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
