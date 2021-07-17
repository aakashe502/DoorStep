package com.hadIt.doorstep.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.address.EditAddress;
import com.hadIt.doorstep.address.SelectAddress;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.login_signup.LoginActivity;
import com.hadIt.doorstep.progressBar.CustomProgressBar;
import com.hadIt.doorstep.roomDatabase.address.AddressDataTransfer;
import com.hadIt.doorstep.roomDatabase.address.AddressRepository;
import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

import java.util.List;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.ViewHolder> implements AddressDataTransfer {
    private static final String Tag = "SelectAddressAdapter";
    private Context context;
    private List<AddressModel> dataList;
    private FirebaseFirestore firebaseFirestore;
    private PaperDb paperDb;
    private Users users;
    private AddressRepository addressRepository;
    private CustomProgressBar customProgressBar;

    public SelectAddressAdapter(Context context, List<AddressModel> dataList, Application application) {
        this.context = context;
        this.dataList = dataList;
        addressRepository = new AddressRepository(application);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        customProgressBar = new CustomProgressBar(context);
        return new SelectAddressAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address,
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AddressModel addressModelClass = dataList.get(position);
        holder.customerName.setText(addressModelClass.getFirstName() + " " + addressModelClass.getLastName());
        holder.houseNumber.setText(addressModelClass.getHouseNumber()+", "+addressModelClass.getApartmentName());
        holder.landmark.setText(addressModelClass.getLandmark());
        holder.areaDetails.setText(addressModelClass.getAreaDetails()+", "+addressModelClass.getCity()+"-"+addressModelClass.getPincode());
        holder.phoneNumber.setText("Phone: " + addressModelClass.getContactNumber());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddress.class);
                intent.putExtra("AddressUid", dataList.get(position).getAddressUid());
                intent.putExtra("firstName", dataList.get(position).getFirstName());
                intent.putExtra("lastName", dataList.get(position).getLastName());
                intent.putExtra("contactNumber", dataList.get(position).getContactNumber());
                intent.putExtra("houseNumber", dataList.get(position).getHouseNumber());
                intent.putExtra("apartmentName", dataList.get(position).getApartmentName());
                intent.putExtra("landmark", dataList.get(position).getLandmark());
                intent.putExtra("areaDetails", dataList.get(position).getAreaDetails());
                intent.putExtra("city", dataList.get(position).getCity());
                intent.putExtra("pincode", dataList.get(position).getPincode());
                intent.putExtra("latitude", dataList.get(position).getLatitude());
                intent.putExtra("longitude", dataList.get(position).getLongitude());
                context.startActivity(intent);
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        paperDb = new PaperDb();
        users = paperDb.getUserFromPaperDb();

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String addressUid = dataList.get(position).getAddressUid();

                String[] options={"Delete","Cancel"};
                //dialog
                final android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
                builder.setTitle("Delete Address")
                    .setItems(options,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface,int i) {
                            if(i==0){
                                removeAddress(dataList.get(position));
                            }
                        }
                    }).show();
            }
        });

        holder.LeftRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    customProgressBar.show();
                try {
                    paperDb.saveAddressInPaperDb(dataList.get(position));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customProgressBar.dismiss();
                context.startActivity(new Intent(context, CheckoutActivity.class));
            }
        });
    }

    private void removeAddress(final AddressModel addressModel) {
        firebaseFirestore.collection("users").document(users.emailId).collection("address").document(addressModel.getAddressUid())
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    onDelete(addressModel);
                    Toast.makeText(context, "Address Deleted Successfully...", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, SelectAddress.class));
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(Tag, e.getStackTrace().toString());
                    Toast.makeText( context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<AddressModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onSetValues(AddressModel addressModel) {
        addressRepository.insert(addressModel);
    }

    @Override
    public void onDelete(AddressModel addressModel) {
        addressRepository.delete(addressModel.getAddressUid());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName, houseNumber, landmark, areaDetails, phoneNumber;
        public TextView editBtn, deleteBtn;
        public RelativeLayout LeftRelative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            houseNumber = itemView.findViewById(R.id.houseNumber);
            landmark = itemView.findViewById(R.id.landmark);
            areaDetails = itemView.findViewById(R.id.areaDetails);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editBtn = itemView.findViewById(R.id.editBtn);
            LeftRelative = itemView.findViewById(R.id.LeftRelative);
        }
    }
}
