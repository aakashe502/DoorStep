package com.hadIt.doorstep.roomDatabase.address;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

import java.util.List;
public class AddressRepository {
    private AddressDatabase dataDatabase;
    private LiveData<List<AddressModel>> getAllData;

    public AddressRepository(Application application)
    {
        dataDatabase = AddressDatabase.getInstance(application);
        getAllData = dataDatabase.addressDao().getAllData();
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

    public void delete(String addressUid)
    {
        new AddressRepository.DeleteAsynTask(dataDatabase).execute(addressUid);
    }

    static class InsertAsynTask extends AsyncTask<AddressModel,Void,Void>
    {
        private AddressDao addressDao;
        InsertAsynTask(AddressDatabase addressDatabase)
        {
            addressDao=addressDatabase.addressDao();
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(AddressModel... lists) {
            addressDao.insert(lists[0]);
            return null;
        }
    }

    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void>{
        private AddressDao dataDao;
        DeleteAllAsynTask(AddressDatabase addressDatabase)
        {
            dataDao=addressDatabase.addressDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }

    static class DeleteAsynTask  extends AsyncTask<String,Void,Void>{
        private AddressDao dataDao;
        public DeleteAsynTask(AddressDatabase dataDatabase) {
            dataDao=dataDatabase.addressDao();
        }

        @Override
        protected Void doInBackground(String... strings) {
            dataDao.delete(strings[0]);
            return null;
        }
    }
}
