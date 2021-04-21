package com.hadIt.doorstep.ui.home;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.Checkout;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.ui.Admin.AddgroceryAdapter;
import com.hadIt.doorstep.ui.Admin.InfoData;
import com.hadIt.doorstep.ui.Interfaces.Datatransfer;

import java.util.ArrayList;
import java.util.List;

public class ViewProduct extends AppCompatActivity implements Datatransfer {
    public RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<InfoData> data;
    Toolbar toolbar;
    private DataRepository dataRespository;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    private DataViewModal dataViewModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
       // getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerproduct);
        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle("ViewProduct");
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataRespository=new DataRepository(getApplication());

        firestore=FirebaseFirestore.getInstance();
        data=new ArrayList<>();
        String str=getIntent().getStringExtra("grocery");
        final AddgroceryAdapter modelAdapter=new AddgroceryAdapter(data,this,this);
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
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                mCartItemCount=dataList.size();
            }
        });












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
//    MenuInflater inflater=getMenuInflater();
//    inflater.inflate(R.menu.addcart,menu);
//    return super.onCreateOptionsMenu(menu);


}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_carta:
              startActivity(new Intent(this,Checkout.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {
        Checkout searchActivity=new Checkout();


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

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.action_carta:
//              startActivity(new Intent(this,SearchActivity.class));
//                break;
//        }
//        return true;
//    }

    @Override
    public void onSetValues(Data al) {
        dataRespository.insert(al);

    }

    @Override
    public void onDelete(Data data) {
        dataRespository.delete(data.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
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