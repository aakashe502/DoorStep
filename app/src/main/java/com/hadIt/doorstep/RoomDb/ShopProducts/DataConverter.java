package com.hadIt.doorstep.RoomDb.ShopProducts;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hadIt.doorstep.fragment_ui.home.ProductInfoModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<ProductInfoModel> optionValues) {
        if (optionValues == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductInfoModel>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<ProductInfoModel> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductInfoModel>>() {
        }.getType();
        List<ProductInfoModel> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
