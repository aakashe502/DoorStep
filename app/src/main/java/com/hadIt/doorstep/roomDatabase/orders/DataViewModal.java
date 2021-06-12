package com.hadIt.doorstep.roomDatabase.orders;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.hadIt.doorstep.roomDatabase.orders.model.Data;

import java.util.List;

public class DataViewModal extends AndroidViewModel {
    private DataRepository dataRespository;
    private LiveData<List<Data>> getAllData;

    public DataViewModal(@NonNull Application application) {
        super(application);
        dataRespository=new DataRepository(application);
        getAllData=dataRespository.getAllData();
    }

    public void insert(Data dataList)
    {
        dataRespository.insert(dataList);
    }

    public LiveData<List<Data>> getCheckoutdata() {
        return getAllData;
    }

    public void deleteAll()
    {
        dataRespository.deleteAll();
    }

    public void delete(String id)
    {
        dataRespository.delete(id);
    }
}