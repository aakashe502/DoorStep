package com.hadIt.doorstep.fragment_ui.home;

import com.hadIt.doorstep.cache.model.TopProductsModel;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.TopRestraunts_Model;

import java.util.ArrayList;
import java.util.List;

public class MergedModelClass {

    public List<ModelClass> modelClass;

    List<TopProductsModel> listmodel;
    List<TopRestraunts_Model> topRestraunts_models;



    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;
    public static final int Layoutthree = 2;
    public static final int Layoutfour = 3;

    public MergedModelClass(int viewType) {
        this.viewType = viewType;
    }

    private int viewType;

    public MergedModelClass() {
    }

    public MergedModelClass(ArrayList<ModelClass> modelClass,int viewType) {
        this.modelClass = modelClass;
        this.viewType = viewType;
    }

    public ModelClass getModelClass() {
        return (ModelClass) modelClass;
    }

    public void setModelClass(ModelClass modelClass) {
        this.modelClass = (List<ModelClass>) modelClass;
    }

    public List<TopProductsModel> topPicksModelclass;

    public MergedModelClass(int viewType,List<TopProductsModel> topPicksModelclass) {
        this.viewType = viewType;
        this.topPicksModelclass = topPicksModelclass;
    }

    public List<TopProductsModel> getTopPicksModelclass() {
        return topPicksModelclass;
    }

    public void setTopPicksModelclass(List<TopProductsModel> topPicksModelclass) {
        this.topPicksModelclass = topPicksModelclass;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    public MergedModelClass(List<TopRestraunts_Model> topRestraunts_models,int viewType) {
        this.topRestraunts_models = topRestraunts_models;
        this.viewType = viewType;
    }
}
