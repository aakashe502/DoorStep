package com.hadIt.doorstep.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.ui.Admin.AddgroceryAdapter;
import com.hadIt.doorstep.ui.Admin.InfoData;
import com.hadIt.doorstep.ui.Admin.ProductInfoModel;

import java.util.ArrayList;
import java.util.List;

public class ViewProduct extends AppCompatActivity {
    public RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<InfoData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        recyclerView=findViewById(R.id.recyclerproduct);
        firestore=FirebaseFirestore.getInstance();
        data=new ArrayList<>();
        String str=getIntent().getStringExtra("grocery");
        final AddgroceryAdapter modelAdapter=new AddgroceryAdapter(data,this);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);

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

}