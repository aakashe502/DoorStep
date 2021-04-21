package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.hadIt.doorstep.Adapter.DataAdapter;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.ui.Interfaces.Datatransfer;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
