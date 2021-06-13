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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.AdminProductModel;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.fragment_ui.interfaces.DataTransfer;
import com.hadIt.doorstep.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewShopProducts extends AppCompatActivity implements DataTransfer {
    RecyclerView recyclerView;
    public ArrayList<AdminProductModel> arrayList;
    public Button addprod;
    public FirebaseFirestore firebaseFirestore;
    private DataRepository dataRespository;
    private GridLayout gridLayout;
    public Toolbar toolbar;
    private ShopProductAdapter modelAdapter;

    private TextView textCartItemCount;
    private int mCartItemCount = 10;
    private DataViewModal dataViewModal;
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

        gridLayout = findViewById(R.id.radioBtn);
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        getChipGroup(shopType, radioGroup);

        recyclerView = findViewById(R.id.productrecycler);
        arrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        modelAdapter = new ShopProductAdapter(arrayList, this, this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                arrayList.clear();
                modelAdapter.notifyDataSetChanged();
                RadioButton radioButton = group.findViewById(checkedId);
                setRecyclerView(radioButton, shopType, shopUid);
            }
        });

        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                array =dataList;
                int total = 0;
                for(Data data: dataList)
                    total += Integer.parseInt(data.getQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null){
                    textCartItemCount.setText(""+mCartItemCount);
                }
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
    public void onSetValues(ArrayList<Data> dataArrayList) {
        for(Data data: dataArrayList)
            dataRespository.insert(data);
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
                    total += Integer.parseInt(data.getQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null)
                    textCartItemCount.setText(""+(mCartItemCount));
            }
        });
    }

    @Override
    public void onDelete(Data data) {
        if(Integer.parseInt(data.getQuantity()) >= 1){
            dataRespository.insert(data);
        }
        else{
            dataRespository.delete(data.getId());
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
                    total += Integer.parseInt(data.getQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null)
                    textCartItemCount.setText(""+mCartItemCount);
            }
        });
    }

    private void setRecyclerView(RadioButton radioButton, String shopType, String shopUid) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        firebaseFirestore.collection("Products").whereEqualTo("shopUid", shopUid).whereEqualTo("productCategory", shopType+" "+radioButton.getText())
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

    private void getChipGroup(String shopType, RadioGroup radioGroup) {
        String[] product = new Constants().products.get(shopType);
        RadioGroup.LayoutParams layoutParams;
        for(String i: product){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(i);

            layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(radioButton, layoutParams);
        }
        gridLayout.addView(radioGroup);
    }
}