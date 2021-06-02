package com.hadIt.doorstep.fragment_ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.cache.model.AdminProductModel;

import java.util.ArrayList;
import java.util.Objects;

public class ViewShopProducts extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<AdminProductModel> arrayList;
    public Button addprod;
    FirebaseFirestore firestore;
    private DataRepository dataRespository;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        dataRespository = new DataRepository(getApplication());
        final String sessionId = getIntent().getStringExtra("grocery");
        final String shopName = getIntent().getStringExtra("shopName");

        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(shopName + " Dashboard");
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

        recyclerView = findViewById(R.id.productrecycler);
        arrayList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();

        final AdminAddapter modelAdapter = new AdminAddapter(arrayList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        firestore.collection("Products").whereEqualTo("shopUid", sessionId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot dpc : task.getResult().getDocuments()) {
                            AdminProductModel productInfoModel = dpc.toObject(AdminProductModel.class);
                            arrayList.add(productInfoModel);
                        }
                        modelAdapter.notifyDataSetChanged();
                    }
                }
            });

        recyclerView.setAdapter(modelAdapter);
    }
}