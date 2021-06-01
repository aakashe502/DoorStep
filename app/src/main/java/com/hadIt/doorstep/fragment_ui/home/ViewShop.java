package com.hadIt.doorstep.fragment_ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Admin;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.fragment_ui.Interfaces.Datatransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewShop extends AppCompatActivity implements Datatransfer {
    public RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<Admin> data;
    Toolbar toolbar;
    private DataRepository dataRespository;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    private DataViewModal dataViewModal;
    public List<Data> arra=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
       // getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerproduct);
        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle("ViewProduct");

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

        firestore=FirebaseFirestore.getInstance();
        data=new ArrayList<>();
        String str=getIntent().getStringExtra("grocery");
        final ShopDetailsAdapter modelAdapter=new ShopDetailsAdapter(data,this,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

//        firestore.collection("AdminProducts").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection(str).get()
//              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                  @Override
//                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                      if(task.isSuccessful()){
//                          for(DocumentSnapshot dpc:task.getResult().getDocuments()){
//                              ProductInfo productInfoModel=dpc.toObject(ProductInfo.class);
//                              data.add(productInfoModel);
//                              modelAdapter.notifyDataSetChanged();
//                          }
//                      }
//                  }
//              });
        firestore.collection("admin").whereEqualTo("shoptype", str).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot dpc:task.getResult().getDocuments()){

                        Admin productInfoModel=dpc.toObject(Admin.class);
                        data.add(productInfoModel);
                        modelAdapter.notifyDataSetChanged();}
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addcart, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_carta);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_carta:
              startActivity(new Intent(this, CheckoutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupBadge() {
        if (textCartItemCount != null) {
            textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));

            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            }
            else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }

    }
    @Override
    public void onSetValues(Data al) {
        dataRespository.insert(al);
        setLength();
      //textCartItemCount.setText(""+(mCartItemCount+1));
    }

    private void setLength() {
        if(dataViewModal==null)
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
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
        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
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