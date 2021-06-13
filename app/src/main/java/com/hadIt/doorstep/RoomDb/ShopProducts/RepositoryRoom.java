package com.hadIt.doorstep.RoomDb.ShopProducts;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class RepositoryRoom {
    private DatabaseRoom dataDatabase;
    private LiveData<RoomData> getAllData;


    public RepositoryRoom(Application application,String id)
    {
        dataDatabase=DatabaseRoom.getInstance(application);
        getAllData=dataDatabase.Daodata().getDataAtId(id);
    }

    public void insert(RoomData dataList)
    {
        new InsertAsynTask1(dataDatabase).execute(dataList);
    }

    public  LiveData<RoomData> getAllData()
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
    // public void Search(String id){ new UpdateAsyncTask(dataDatabase).excecute(id);}

    static class InsertAsynTask1 extends AsyncTask<RoomData,Void,Void>
    {
        private DaoQuery dataDaoQuery;
        InsertAsynTask1(DatabaseRoom dataDatabase)
        {
            dataDaoQuery =dataDatabase.Daodata();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(RoomData... lists) {
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
            dataDaoQuery.deleteAll();
            return null;
        }
    }

    static class DeleteAsynTask  extends AsyncTask<String,Void,Void>{
        private DaoQuery dataDaoQuery;
        public DeleteAsynTask(DatabaseRoom dataDatabase) {
            dataDaoQuery =dataDatabase.Daodata();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dataDaoQuery.delete(strings[0]);
            return null;
        }
    }

}
