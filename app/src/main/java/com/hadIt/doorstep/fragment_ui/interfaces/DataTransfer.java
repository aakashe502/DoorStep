package com.hadIt.doorstep.fragment_ui.interfaces;

import com.hadIt.doorstep.cache.model.Data;

import java.util.ArrayList;

public interface DataTransfer {
    public void onSetValues(ArrayList<Data> al);
    public void onDelete(Data data);
}
