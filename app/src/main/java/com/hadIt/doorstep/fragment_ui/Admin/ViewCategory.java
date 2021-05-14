package com.hadIt.doorstep.fragment_ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;

import java.util.ArrayList;
import java.util.Objects;

public class ViewCategory extends AppCompatActivity  {
    RecyclerView recyclerView;
    public ArrayList<InfoData> arrayList;
    public Button addprod;
    FirebaseFirestore firestore;
    private DataRepository dataRespository;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        dataRespository=new DataRepository(getApplication());
        final String sessionId = getIntent().getStringExtra("grocery");

        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(sessionId);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.productrecycler);
        arrayList=new ArrayList<>();
        firestore=FirebaseFirestore.getInstance();

        addprod=findViewById(R.id.addprod);
        Toast.makeText(this,"id 1"+sessionId,Toast.LENGTH_SHORT).show();
        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewCategory.this,AddProduct.class);
                intent.putExtra("name",""+sessionId);
                startActivity(intent);
            }
        });



        final AdminAddapter modelAdapter=new AdminAddapter(arrayList,this);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        firestore.collection("Products").document("products").collection(sessionId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                                InfoData productInfoModel=dpc.toObject(InfoData.class);
                                arrayList.add(productInfoModel);
                                modelAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        recyclerView.setAdapter(modelAdapter);
    }


}