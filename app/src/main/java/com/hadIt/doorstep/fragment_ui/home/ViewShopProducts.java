package com.hadIt.doorstep.fragment_ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.cache.model.AdminProductModel;
import com.hadIt.doorstep.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class ViewShopProducts extends AppCompatActivity {
    RecyclerView recyclerView;
    public ArrayList<AdminProductModel> arrayList;
    public Button addprod;
    public FirebaseFirestore firebaseFirestore;
    private DataRepository dataRespository;
    private GridLayout gridLayout;
    public Toolbar toolbar;
    private AdminAddapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        dataRespository = new DataRepository(getApplication());
        final String shopUid = getIntent().getStringExtra("grocery");
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
        modelAdapter = new AdminAddapter(arrayList, this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                arrayList.clear();
                modelAdapter.notifyDataSetChanged();
                RadioButton radioButton = group.findViewById(checkedId);
                setRecyclerView(radioButton, shopType, shopUid);
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