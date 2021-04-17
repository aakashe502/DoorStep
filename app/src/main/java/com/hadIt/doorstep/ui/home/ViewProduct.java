package com.hadIt.doorstep.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.ui.Admin.AddgroceryAdapter;
import com.hadIt.doorstep.ui.Admin.InfoData;
import com.hadIt.doorstep.ui.Interfaces.Datatransfer;

import java.util.ArrayList;
import java.util.Objects;

public class ViewProduct extends AppCompatActivity implements Datatransfer {
    public RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<InfoData> data;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
       // getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerproduct);
        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle("ViewProduct");
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


        firestore=FirebaseFirestore.getInstance();
        data=new ArrayList<>();
        String str=getIntent().getStringExtra("grocery");
        final AddgroceryAdapter modelAdapter=new AddgroceryAdapter(data,this,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

      firestore.collection("Products").document("products").collection(str).get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if(task.isSuccessful()){
                      for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                          InfoData productInfoModel=dpc.toObject(InfoData.class);
                          data.add(productInfoModel);
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
    }

    @Override
    public void onSetValues(ArrayList<InfoData> al) {
        Log.i("answeraakash",al.get(al.size()-1).getProductname());
    }
}