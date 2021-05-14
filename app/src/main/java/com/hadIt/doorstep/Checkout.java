package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hadIt.doorstep.Adapter.DataAdapter;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Checkout extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private List<Data> dataList;
    private DataViewModal dataViewModal;

    private DataAdapter dataAdapter;
    private List<Data> getDataList;
    private int get_priority;
    private int id=1;
    private Data data;
    public int length=0;
    Button checkout;
    int sum=0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle("Cart items");


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

        setSupportActionBar(toolbar);

        dataList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDataList=new ArrayList<>();
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        checkout=findViewById(R.id.checkout);

        dataAdapter=new DataAdapter(this,getDataList);


        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                length=dataList.size();
                dataAdapter.getAllDatas(dataList);
                for(Data ds:dataList) {
                    Log.i("appbar","name="+ds.getName()+ " image="+ds.getImage()+" rate"+ds.getRate()+" quantity="+ds.getQuantity()+" id"+ds.getId());
                    sum+= Integer.parseInt(ds.getQuantity())*Integer.parseInt(ds.getRate());
                }
                recyclerView.setAdapter(dataAdapter);
                checkout.setText("Checkout = "+"Rs "+sum);
            }
        });

    }


}
