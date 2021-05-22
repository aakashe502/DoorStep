package com.hadIt.doorstep.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.Adapter.SelectAddressAdapter;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SelectAddress extends AppCompatActivity {
    private ImageButton addNewAddress, backBtn;
    private RecyclerView recyclerView;
    private SelectAddressAdapter selectAddressAdapter;
    private List<AddressModelClass> addressModelClassList;
    private FirebaseFirestore firebaseFirestore;
    private Users usersData;
    private PaperDb paperDb;

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
        selectAddressAdapter = new SelectAddressAdapter(this, addressModelClassList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        paperDb = new PaperDb();

        getUserInfo();
        getUserAddress();

        selectAddressAdapter.setDataList(addressModelClassList);
        recyclerView.setAdapter(selectAddressAdapter);
    }

    private void getUserAddress() {
        try {
            Task<QuerySnapshot> documents = firebaseFirestore.collection("users").document(usersData.emailId).collection("address").get();
            sleep(1000);
            QuerySnapshot userObject = documents.getResult();
            if(userObject!=null){
                for(DocumentSnapshot dpc:userObject.getDocuments()){
                    AddressModelClass addressModelClass=dpc.toObject(AddressModelClass.class);
                    addressModelClassList.add(addressModelClass);
                }
                selectAddressAdapter.notifyDataSetChanged();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        if(paperDb.getFromPaperDb() == null){
            try {
                paperDb.saveInPaperDb();
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        usersData = paperDb.getFromPaperDb();
    }
}