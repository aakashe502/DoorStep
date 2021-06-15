package com.hadIt.doorstep.roomDatabase.shopProducts;

import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

public interface ProductTransfer {
    public void setProductsTable(ProductsTable productsTable);
    public void deleteProductsTable(ProductsTable productsTable);
}
