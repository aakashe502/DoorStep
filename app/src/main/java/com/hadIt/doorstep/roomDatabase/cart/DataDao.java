package com.hadIt.doorstep.roomDatabase.cart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import java.util.List;

@Dao
public interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data dataList);

    @Query("DELETE FROM data")
    void deleteAll();

    @Query("SELECT * FROM data")
    LiveData<List<Data>>  getAllData();

    @Query("DELETE FROM data WHERE id=:id")
    void delete(String id);

    @Query("SELECT shopUid FROM data LIMIT 1")
    String getShopUid();
}