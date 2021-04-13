package com.hadIt.doorstep.ui.Admin;

import android.widget.ImageView;
import android.widget.TextView;

public class ProductInfoModel {
    public int productimage;
    public String productname;
    public String productrate;

    public ProductInfoModel(int productimage,String productname,String productrate) {
        this.productimage = productimage;
        this.productname = productname;
        this.productrate = productrate;
    }

    public ProductInfoModel() {
    }

    public int getProductimage() {
        return productimage;
    }

    public void setProductimage(int productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductrate() {
        return productrate;
    }

    public void setProductrate(String productrate) {
        this.productrate = productrate;
    }
}
