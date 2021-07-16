package com.hadIt.doorstep.roomDatabase.cart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

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