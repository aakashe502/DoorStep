package com.hadIt.doorstep.roomDatabase.shopProducts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductsRepository productsRepository;
    private LiveData<List<ProductsTable>> getAllData;

    public ProductViewModel(@NonNull Application application, String shopUid, String productCategory) {
        super(application);
        productsRepository = new ProductsRepository(application, shopUid, productCategory);
        getAllData=productsRepository.getAllData();
    }

    public void insert(ProductsTable dataList)
    {
        productsRepository.insert(dataList);
    }

    public LiveData<List<ProductsTable>> getShopProducts() {
        return getAllData;
    }

    public void deleteAll()
    {
        productsRepository.refreshDb();
    }

    public void delete(String productId)
    {
        productsRepository.delete(productId);
    }
}