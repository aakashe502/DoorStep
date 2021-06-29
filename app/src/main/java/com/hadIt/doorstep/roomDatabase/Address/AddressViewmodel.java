package com.hadIt.doorstep.roomDatabase.Address;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.cart.DataRepository;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import java.util.List;

public class AddressViewmodel  extends AndroidViewModel {
    private AddressRepository dataRespository;
    private LiveData<List<AddressModel>> getAllData;

    public AddressViewmodel(@NonNull Application application) {
        super(application);
        dataRespository=new AddressRepository(application);
        getAllData=dataRespository.getAllData();
    }

    public void insert(AddressModel dataList)
    {
        dataRespository.insert(dataList);
    }

    public LiveData<List<AddressModel>> getCheckoutdata() {
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
