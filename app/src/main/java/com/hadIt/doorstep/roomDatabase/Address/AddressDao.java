package com.hadIt.doorstep.roomDatabase.Address;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import java.util.List;
@Dao
public class AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AddressModel dataList) {

    }


    @Query("SELECT * FROM data")
    LiveData<List<Data>> getAllData() {
        return null;
    }

    @Query("DELETE FROM data WHERE id=:id        ")
    void delete(String id) {
    }

    @Query("SELECT shopUid FROM data LIMIT 1")
    String getShopUid() {
        return null;
    }

    public void deleteAll() {
    }
}
