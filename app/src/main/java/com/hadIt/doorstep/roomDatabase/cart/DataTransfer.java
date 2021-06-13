package com.hadIt.doorstep.roomDatabase.cart;

import com.hadIt.doorstep.roomDatabase.cart.model.Data;

public interface DataTransfer {
    public void onSetValues(Data al);
    public void onDelete(Data data);
}
