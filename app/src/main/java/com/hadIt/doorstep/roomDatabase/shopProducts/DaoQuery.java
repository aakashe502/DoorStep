package com.hadIt.doorstep.roomDatabase.shopProducts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DaoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomData dataList );

    @Query("DELETE FROM shops WHERE uid=:uid")
    void delete(String uid);

    @Query("DELETE FROM shops")
    void deleteAll();

    @Query("SELECT * FROM shops WHERE uid=:uid")
    LiveData<RoomData> getDataAtId(String uid);
}
