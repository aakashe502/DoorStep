package com.hadIt.doorstep.fragment_ui.home;

public class ModelClass {
    public String name;
    public int images;

    public ModelClass(String name,int images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
