package com.hadIt.doorstep.roomDatabase.orders;

import com.hadIt.doorstep.roomDatabase.orders.model.Data;

import java.util.ArrayList;

public interface DataTransfer {
    public void onSetValues(Data al);
    public void onDelete(Data data);
}
