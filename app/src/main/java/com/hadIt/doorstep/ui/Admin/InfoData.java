package com.hadIt.doorstep.ui.Admin;

public class InfoData {
    public String productimage;
    public String productname;
    public String productrate;

    public InfoData(String productimage,String productname,String productrate) {
        this.productimage = productimage;
        this.productname = productname;
        this.productrate = productrate;
    }

    public InfoData() {
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
}
