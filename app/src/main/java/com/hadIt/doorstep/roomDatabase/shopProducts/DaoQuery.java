package com.hadIt.doorstep.roomDatabase.shopProducts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

@Dao
public interface DaoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductsTable dataList);

    @Query("DELETE FROM products WHERE productId=:productId")
    void delete(String productId);

    @Query("DELETE FROM products WHERE shopUid=:shopUid")
    void deleteProductUsingShopId(String shopUid);

    @Query("DELETE FROM products")
    void refreshDb();

    @Query("SELECT * FROM products WHERE shopUid=:shopUid AND productCategory =:productCategory")
    LiveData<List<ProductsTable>> getDataForShopAndCategory(String shopUid, String productCategory);

    @Query("SELECT * FROM products WHERE shopUid=:shopUid")
    LiveData<List<ProductsTable>> getDataForShop(String shopUid);
}
