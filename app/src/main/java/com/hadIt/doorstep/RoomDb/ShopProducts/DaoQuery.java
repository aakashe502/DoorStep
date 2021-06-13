package com.hadIt.doorstep.RoomDb.ShopProducts;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface DaoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomData dataList );

    @Query("DELETE FROM data WHERE id=:id")
    void delete(String id);

    @Query("DELETE FROM data")
    void deleteAll();

    @Query("SELECT * FROM data WHERE id=:id")
    LiveData<RoomData> getDataAtId(String id);
}
