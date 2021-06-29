package com.hadIt.doorstep.roomDatabase.Address;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.cart.DataDao;
import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;
import com.hadIt.doorstep.roomDatabase.cart.DataRepository;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import java.util.List;
public class AddressRepository {
    private DataDatabase dataDatabase;
    private LiveData<List<AddressModel>> getAllData;

    public AddressRepository(Application application)
    {
        dataDatabase = DataDatabase.getInstance(application);
        getAllData = dataDatabase.dataDao().getAllData();
    }

    public void insert(AddressModel dataList)
    {
        new AddressRepository.InsertAsynTask(dataDatabase).execute(dataList);
    }

    public  LiveData<List<AddressModel>> getAllData()
    {
        return getAllData;
    }

    public void deleteAll()
    {
        new AddressRepository.DeleteAllAsynTask(dataDatabase).execute();
    }

    public void delete(String id)
    {
        new AddressRepository.DeleteAsynTask(dataDatabase).execute(id);
    }


    static class InsertAsynTask extends AsyncTask<AddressModel,Void,Void>
    {
        private DataDao dataDao;
        InsertAsynTask(DataDatabase dataDatabase)
        {
            dataDao=dataDatabase.dataDao();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(AddressModel... lists) {

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
