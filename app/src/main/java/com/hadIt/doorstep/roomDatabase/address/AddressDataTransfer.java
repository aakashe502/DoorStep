package com.hadIt.doorstep.roomDatabase.address;

import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

public interface AddressDataTransfer {
    public void onSetValues(AddressModel addressModel);
    public void onDelete(AddressModel addressModel);
}
