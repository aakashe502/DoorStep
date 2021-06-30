package com.hadIt.doorstep.roomDatabase.address;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private AddressRepository dataRespository;
    private LiveData<List<AddressModel>> getAllData;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        dataRespository=new AddressRepository(application);
        getAllData=dataRespository.getAllData();
    }

    public void insert(AddressModel dataList)
    {
        dataRespository.insert(dataList);
    }

    public LiveData<List<AddressModel>> getAllAddress() {
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
