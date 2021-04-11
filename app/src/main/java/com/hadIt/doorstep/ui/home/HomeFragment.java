package com.hadIt.doorstep.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hadIt.doorstep.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public ImageView meat,veg,pastry;
    public RecyclerView recyclerView;
    public ArrayList<ModelClass> model;

    public View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView=root.findViewById(R.id.recyclerview);
        model=new ArrayList<ModelClass>();
        model.add(new ModelClass("VEGETABLES",R.drawable.vegetable));
        model.add(new ModelClass("GROCERY",R.drawable.bake));
        model.add(new ModelClass("MEAT",R.drawable.meat));
        model.add(new ModelClass("DESERT",R.drawable.desert));
        model.add(new ModelClass("HARVEST",R.drawable.harvest));
        model.add(new ModelClass("BREAD",R.drawable.bread));
        model.add(new ModelClass("CLEANING",R.drawable.cleaning));
        model.add(new ModelClass("BEVERAGES",R.drawable.beverages));
        model.add(new ModelClass("NON-VEG",R.drawable.non_veg));

        ModelAdapter modelAdapter=new ModelAdapter(model,root.getContext());
       // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(root.getContext(),3,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
