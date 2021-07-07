package com.hadIt.doorstep.fragment_ui.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

class ShopProductDiffUtils {
    public static DiffUtil.ItemCallback<ProductsTable> itemCallback = new DiffUtil.ItemCallback<ProductsTable>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductsTable oldItem, @NonNull ProductsTable newItem) {
            return oldItem.getShopUid().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductsTable oldItem, @NonNull ProductsTable newItem) {
            if(!oldItem.getProductId().equals(newItem.getProductId())) return false;
            if(!oldItem.getProductName().equals(newItem.getProductName())) return false;
            if(!oldItem.getProductPrice().equals(newItem.getProductPrice())) return false;
            if(!oldItem.getProductCategory().equals(newItem.getProductCategory())) return false;
            if(!oldItem.getProductDescription().equals(newItem.getProductDescription())) return false;
            if(!oldItem.getShopUid().equals(newItem.getShopUid())) return false;
            if(!oldItem.getProductIcon().equals(newItem.getProductIcon())) return false;
            if(!oldItem.getUnit().equals(newItem.getUnit())) return false;
            if(!oldItem.getProductQuantity().equals(newItem.getProductQuantity())) return false;
            return true;
        }
    };
}