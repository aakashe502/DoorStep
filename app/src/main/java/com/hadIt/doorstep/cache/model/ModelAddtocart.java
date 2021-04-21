package com.hadIt.doorstep.cache.model;

public class ModelAddtocart {
    public String id;
    public String productimage;
    public String productname;
    public String productrate;
    public String quantity;

    public ModelAddtocart(String id,String productimage,String productname,String productrate,String quantity) {
        this.id = id;
        this.productimage = productimage;
        this.productname = productname;
        this.productrate = productrate;
        this.quantity = quantity;
    }

    public ModelAddtocart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
