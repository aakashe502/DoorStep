package com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView;

import com.hadIt.doorstep.cache.model.TopProductsModel;

public class TopRestraunts_Model  {
    public String productPrice, productCategory, productDescription, productIcon, productId, productQuantity, productName, shopUid;

    public TopRestraunts_Model(String productPrice, String productCategory, String productDescription, String productIcon, String productId, String productQuantity, String productName, String shopUid) {
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productName = productName;
        this.shopUid = shopUid;
    }

    public TopRestraunts_Model() {
    }
}
