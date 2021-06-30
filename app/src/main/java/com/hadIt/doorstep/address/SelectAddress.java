package com.hadIt.doorstep.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.Adapter.SelectAddressAdapter;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.roomDatabase.address.AddressDataTransfer;
import com.hadIt.doorstep.roomDatabase.address.AddressRepository;
import com.hadIt.doorstep.roomDatabase.address.AddressViewModel;
import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SelectAddress extends AppCompatActivity implements AddressDataTransfer {
    private ImageButton addNewAddress, backBtn;
    private RecyclerView recyclerView;
    private SelectAddressAdapter selectAddressAdapter;
    private List<AddressModel> addressModelClassList;
    private FirebaseFirestore firebaseFirestore;
    private Users usersData;
    private PaperDb paperDb;
    private AddressViewModel addressViewModel;
    private AddressRepository addressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        backBtn = findViewById(R.id.backBtn);
        addNewAddress = findViewById(R.id.addNewAddress);
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectAddress.this, AddNewAddress.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addressModelClassList = new ArrayList<>();
        selectAddressAdapter = new SelectAddressAdapter(this, addressModelClassList, getApplication());
        firebaseFirestore = FirebaseFirestore.getInstance();
        paperDb = new PaperDb();
        addressViewModel = new AddressViewModel(getApplication());
        addressRepository = new AddressRepository(getApplication());

        usersData = paperDb.getUserFromPaperDb();
        getUserAddress();

        selectAddressAdapter.setDataList(addressModelClassList);
        recyclerView.setAdapter(selectAddressAdapter);
    }

    private void getUserAddress() {
        addressViewModel.getAllAddress().observe(this, new Observer<List<AddressModel>>() {
            @Override
            public void onChanged(List<AddressModel> addressModels) {
                addressModelClassList.clear();
                addressModelClassList.addAll(addressModels);
                if(addressModelClassList.size() == 0)
                    storeInRoomDb();
                selectAddressAdapter.notifyDataSetChanged();
            }
        });
    }

    private void storeInRoomDb() {
        firebaseFirestore.collection("users").document(usersData.emailId).collection("address")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                                AddressModel addressModelClass=dpc.toObject(AddressModel.class);
                                onSetValues(addressModelClass);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SelectAddress.this, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSetValues(AddressModel addressModel) {
        addressRepository.insert(addressModel);
    }

    @Override
    public void onDelete(AddressModel addressModel) {
        addressRepository.delete(addressModel.getAddressUid());
    }
}