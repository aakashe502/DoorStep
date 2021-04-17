package com.hadIt.doorstep.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hadIt.doorstep.R;
import com.hadIt.doorstep.ui.home.ModelAdapter;
import com.hadIt.doorstep.ui.home.ModelClass;

import java.util.ArrayList;
import java.util.Objects;

public class AddGrocery extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<ModelClass> model;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_grocery);
        recyclerView=findViewById(R.id.recyclerview);

        toolbar = findViewById(R.id.toolBar);

        toolbar.setTitle("ADMIN DASHBOARD");
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

        model=new ArrayList<>();
        model.add(new ModelClass("VEGETABLES",R.drawable.vegetable));
        model.add(new ModelClass("GROCERY",R.drawable.bake));
        model.add(new ModelClass("MEAT",R.drawable.meat));
        model.add(new ModelClass("DESERT",R.drawable.desert));
        model.add(new ModelClass("HARVEST",R.drawable.harvest));
        model.add(new ModelClass("BREAD",R.drawable.bread));
        model.add(new ModelClass("CLEANING",R.drawable.cleaning));
        model.add(new ModelClass("BEVERAGES",R.drawable.beverages));
        model.add(new ModelClass("NON-VEG",R.drawable.non_veg));

        GroceryAdapter modelAdapter=new GroceryAdapter(model,this);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

    }
}