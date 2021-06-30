package com.hadIt.doorstep.roomDatabase.address;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AddressModel dataList);

    @Query("SELECT * FROM address")
    LiveData<List<AddressModel>> getAllData();

    @Query("DELETE FROM address WHERE addressUid=:addressUid")
    void delete(String addressUid);

    @Query("DELETE from address")
    public void deleteAll();
}
