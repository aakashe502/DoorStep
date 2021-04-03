package com.hadIt.doorstep.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.hadIt.doorstep.R;
import com.hadIt.doorstep.ui.home.ModelAdapter;
import com.hadIt.doorstep.ui.home.ModelClass;

import java.util.ArrayList;

public class AddGrocery extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<ModelClass> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);
        recyclerView=findViewById(R.id.recyclerview);
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

        ModelAdapter modelAdapter=new ModelAdapter(model,this);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

    }
}