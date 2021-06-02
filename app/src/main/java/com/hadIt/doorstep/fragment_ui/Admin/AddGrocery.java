package com.hadIt.doorstep.fragment_ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.hadIt.doorstep.R;
import com.hadIt.doorstep.fragment_ui.home.ModelClass;

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
        model.add(new ModelClass("VEGETABLES & FRUITS", R.drawable.vegetable));
        model.add(new ModelClass("GROCERY", R.drawable.bake));
        model.add(new ModelClass("BEVERAGES",R.drawable.beverages));
        model.add(new ModelClass("NON-VEG",R.drawable.meat));
        model.add(new ModelClass("CAKES & MORE",R.drawable.desert));
        model.add(new ModelClass("HARVEST",R.drawable.harvest));
        model.add(new ModelClass("BREAD",R.drawable.bread));
        model.add(new ModelClass("CLEANING",R.drawable.cleaning));
        model.add(new ModelClass("BOOKS & STATIONERY",R.drawable.books_and_stationary));
        model.add(new ModelClass("NUTRITION & HEALTHCARE",R.drawable.nutrition_and_healthcare));
        model.add(new ModelClass("HOME-MADE",R.drawable.home_made));
        model.add(new ModelClass("DAIRY PRODUCTS",R.drawable.dairy_products));

        GroceryAdapter modelAdapter=new GroceryAdapter(model,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

    }
}