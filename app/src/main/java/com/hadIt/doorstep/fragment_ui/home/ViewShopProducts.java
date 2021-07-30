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
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;

import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;
import com.hadIt.doorstep.roomDatabase.cart.DataRepository;
import com.hadIt.doorstep.roomDatabase.cart.DataViewModal;
import com.hadIt.doorstep.cache.model.ProductModel;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductViewModel;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductsRepository;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;
import com.hadIt.doorstep.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewShopProducts extends AppCompatActivity implements DataTransfer, ProductTransfer {
    RecyclerView recyclerView;
    public ArrayList<ProductsTable> arrayList;
    public Button addprod;
    private EditText search;
    public FirebaseFirestore firebaseFirestore;
    private DataRepository dataRespository;
    private ProductsRepository productsRepository;
    public Toolbar toolbar;
    private ShopProductAdapter modelAdapter;

    private TextView textCartItemCount;
    private int mCartItemCount = 10;
    private DataViewModal dataViewModal;
    private ProductViewModel productViewModel;
    public List<Data> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        dataRespository = new DataRepository(getApplication());
        final String shopUid = getIntent().getStringExtra("shopUid");
        final String shopName = getIntent().getStringExtra("shopName");
        final String shopType = getIntent().getStringExtra("shopType");

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

        setRecyclerView(shopType, shopUid);
        arrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        modelAdapter = new ShopProductAdapter(ShopProductDiffUtils.itemCallback, this, this);

        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                array =dataList;
                int total = 0;
                for(Data data: dataList)
                    total += Integer.parseInt(data.getProductQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null){
                    textCartItemCount.setText(""+mCartItemCount);
                }
            }
        });

        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String searchString) {
        ArrayList<ProductsTable> filteredList = new ArrayList<>();

        for(ProductsTable productsTable: arrayList){
            if(productsTable.getProductName().toLowerCase().contains(searchString.toLowerCase()))
                filteredList.add(productsTable);
        }
        modelAdapter.submitList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addcart, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_carta);
        final MenuItem menuItem2 = menu.findItem(R.id.notification);
        menuItem2.setVisible(false);

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

            if (textCartItemCount.getVisibility() != View.VISIBLE) {
                textCartItemCount.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSetValues(final Data data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String shopUid = DataDatabase.getInstance(getApplicationContext())
                        .dataDao()
                        .getShopUid();

                if(shopUid == null)
                    dataRespository.insert(data);
                else if(shopUid.equals(data.getShopUid()))
                    dataRespository.insert(data);
                else{
                    dataRespository.deleteAll();
                    dataRespository.insert(data);
                }
            }
        }).start();

        setLength();
    }

    private void setLength() {
        if(dataViewModal==null)
            dataViewModal = new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                int total = 0;
                for(Data data: dataList)
                    total += Integer.parseInt(data.getProductQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null)
                    textCartItemCount.setText(""+(mCartItemCount));
            }
        });
    }

    @Override
    public void onDelete(Data data) {
        if(Integer.parseInt(data.getProductQuantity()) >= 1){
            dataRespository.insert(data);
        }
        else{
            dataRespository.delete(data.getProductId());
        }
        setLength();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                int total = 0;
                for(Data data: dataList)
                    total += Integer.parseInt(data.getProductQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null)
                    textCartItemCount.setText(""+mCartItemCount);
            }
        });
    }

    private void setRecyclerView(final String shopType, final String shopUid) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        productsRepository = new ProductsRepository(getApplication(), shopUid);
        productViewModel = new ProductViewModel(getApplication(), shopUid);

        productViewModel.getShopProducts(shopUid).observe(this, new Observer<List<ProductsTable>>() {
            @Override
            public void onChanged(List<ProductsTable> productsTables) {
                arrayList.clear();
                for(ProductsTable productsTable: productsTables){
                    arrayList.add(productsTable);
                }
                if(arrayList.size()==0){
                    storeInRoomDb(shopType, shopUid);
                }
                modelAdapter.submitList(arrayList);
                recyclerView.setAdapter(modelAdapter);
            }
        });
    }

    private void storeInRoomDb(String shopType, String shopUid){
        firebaseFirestore.collection("Products").whereEqualTo("shopUid", shopUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot dpc : task.getResult().getDocuments()) {
                                ProductsTable productsTable = dpc.toObject(ProductsTable.class);
                                setProductsTable(productsTable);
                            }
                        }
                    }
                });
    }

    @Override
    public void setProductsTable(ProductsTable productsTable) {
        productsRepository.insert(productsTable);
    }

    @Override
    public void deleteProductsTable(ProductsTable productsTable) {
        productsRepository.delete(productsTable.getProductId());
    }
}