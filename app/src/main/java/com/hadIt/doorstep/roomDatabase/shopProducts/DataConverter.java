package com.hadIt.doorstep.roomDatabase.shopProducts;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hadIt.doorstep.cache.model.ProductModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<ProductModel> optionValues) {
        if (optionValues == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductModel>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<ProductModel> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductModel>>() {
        }.getType();
        List<ProductModel> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
