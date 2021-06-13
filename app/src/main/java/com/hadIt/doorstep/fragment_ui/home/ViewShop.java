package com.hadIt.doorstep.fragment_ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Admin;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.fragment_ui.interfaces.DataTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewShop extends AppCompatActivity implements DataTransfer {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Admin> data;
    private Toolbar toolbar;
    private DataRepository dataRespository;
    private TextView textCartItemCount;
    private int mCartItemCount = 10;
    private DataViewModal dataViewModal;
    public List<Data> arra=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        recyclerView=findViewById(R.id.recyclerproduct);
        toolbar=findViewById(R.id.toolBar);
        String shopType=getIntent().getStringExtra("shopType");
        toolbar.setTitle(shopType + " SHOP'S");

        dataRespository=new DataRepository(getApplication());
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

        firebaseFirestore =FirebaseFirestore.getInstance();
        data=new ArrayList<>();

        final ShopDetailsAdapter modelAdapter=new ShopDetailsAdapter(data,this,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

        firebaseFirestore.collection("admin").whereEqualTo("shoptype", shopType)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                    Admin productInfoModel=dpc.toObject(Admin.class);
                    data.add(productInfoModel);
                    modelAdapter.notifyDataSetChanged();
                }
            }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                arra=dataList;
                mCartItemCount=dataList.size();
            }
        });
        if(textCartItemCount!=null){
            textCartItemCount.setText(""+mCartItemCount);
        }
    }

    @Override
    public void onSetValues(ArrayList<Data> al) {
//        for(Data d:al)
//        dataRespository.insert(d);
        setLength();
    }

    private void setLength() {
        if(dataViewModal==null)
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                mCartItemCount=dataList.size();
            }
        });
        if(textCartItemCount!=null){
            textCartItemCount.setText(""+(mCartItemCount));
        }
    }

    @Override
    public void onDelete(Data data) {
        dataRespository.delete(data.getId());
        if(textCartItemCount!=null){
            textCartItemCount.setText(""+(mCartItemCount-1));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                mCartItemCount=dataList.size();
            }
        });
        if(textCartItemCount!=null){
            textCartItemCount.setText(""+mCartItemCount);
        }
    }
}