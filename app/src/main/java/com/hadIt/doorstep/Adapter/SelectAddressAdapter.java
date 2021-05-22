package com.hadIt.doorstep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.address.EditAddress;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;

import java.util.List;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.ViewHolder> {
    private static final String TAG = "SelectAddressAdapter";
    private Context context;
    private List<AddressModelClass> dataList;

    public SelectAddressAdapter(Context context, List<AddressModelClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectAddressAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AddressModelClass addressModelClass = dataList.get(position);
        holder.customerName.setText(addressModelClass.firstName + " " + addressModelClass.lastName);
        holder.houseNumber.setText(addressModelClass.houseNumber+", "+addressModelClass.apartmentName);
        holder.landmark.setText(addressModelClass.landmark);
        holder.areaDetails.setText(addressModelClass.areaDetails+", "+addressModelClass.city+"-"+addressModelClass.pincode);
        holder.phoneNumber.setText("Phone: " + addressModelClass.contactNumber);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddress.class);
                intent.putExtra("AddressUid", dataList.get(position).addressUid);
                intent.putExtra("firstName", dataList.get(position).firstName);
                intent.putExtra("lastName", dataList.get(position).lastName);
                intent.putExtra("contactNumber", dataList.get(position).contactNumber);
                intent.putExtra("houseNumber", dataList.get(position).houseNumber);
                intent.putExtra("apartmentName", dataList.get(position).apartmentName);
                intent.putExtra("landmark", dataList.get(position).landmark);
                intent.putExtra("areaDetails", dataList.get(position).areaDetails);
                intent.putExtra("city", dataList.get(position).city);
                intent.putExtra("pincode", dataList.get(position).pincode);
                intent.putExtra("latitude", dataList.get(position).latitude);
                intent.putExtra("longitude", dataList.get(position).longitude);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<AddressModelClass> dataList) {
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName, houseNumber, landmark, areaDetails, phoneNumber;
        public ImageButton editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            houseNumber = itemView.findViewById(R.id.houseNumber);
            landmark = itemView.findViewById(R.id.landmark);
            areaDetails = itemView.findViewById(R.id.areaDetails);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editBtn = itemView.findViewById(R.id.editBtn);

        }
    }
}
