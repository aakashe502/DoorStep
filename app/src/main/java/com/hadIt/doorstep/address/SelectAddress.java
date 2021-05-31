package com.hadIt.doorstep.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        firebaseFirestore.collection("users").document(usersData.emailId).collection("address")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                            AddressModelClass addressModelClass=dpc.toObject(AddressModelClass.class);
                            addressModelClassList.add(addressModelClass);
                        }
                        selectAddressAdapter.notifyDataSetChanged();
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

    private void getUserInfo() {
        if(paperDb.getUserFromPaperDb() == null){
            try {
                paperDb.saveUserInPaperDb();
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        usersData = paperDb.getUserFromPaperDb();
    }
}