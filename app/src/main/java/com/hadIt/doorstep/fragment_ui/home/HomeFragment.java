package com.hadIt.doorstep.fragment_ui.home;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.search.SearchActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<ModelClass> model;
    ViewPager2 mViewPager;
    CircleIndicator3 circleIndicator;
    // images array
    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3};
    // Creating Object of ViewPagerAdapter
    Timer timer;
    Handler handler;
    TextView cardSearch;
    private PaperDb paperDb;

    public View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container,false);
        paperDb = new PaperDb();
        Users users = paperDb.getUserFromPaperDb();
        getActivity().setTitle("Lyptus");

        mViewPager = root.findViewById(R.id.viewPagerMain);
        circleIndicator=root.findViewById(R.id.circleindicator);
        cardSearch=root.findViewById(R.id.cardSearch);

        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

        // Initializing the ViewPagerAdapter
        final ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(root.getContext(), images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        circleIndicator.setViewPager(mViewPager);

        recyclerView=root.findViewById(R.id.recyclerview);
        model= new ArrayList<>();

        model.add(new ModelClass("VEGETABLES & FRUITS", R.drawable.vegetables));
        model.add(new ModelClass("GROCERY", R.drawable.bake));
        model.add(new ModelClass("BEVERAGES", R.drawable.beverages));
        model.add(new ModelClass("NON-VEG", R.drawable.meat));
        model.add(new ModelClass("CAKES & MORE", R.drawable.desert));
        model.add(new ModelClass("HARVEST", R.drawable.harvest));
        model.add(new ModelClass("BREAD", R.drawable.bread));
        model.add(new ModelClass("CLEANING", R.drawable.cleaning));
        model.add(new ModelClass("BOOKS & STATIONERY", R.drawable.books_and_stationary));
        model.add(new ModelClass("NUTRITION & HEALTHCARE", R.drawable.nutrition_and_healthcare));
        model.add(new ModelClass("HOME-MADE", R.drawable.home_made));
        model.add(new ModelClass("DAIRY PRODUCTS", R.drawable.dairy_products));

        ModelAdapter modelAdapter=new ModelAdapter(model,root.getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(root.getContext(),4, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(modelAdapter);
        handler=new Handler();
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i=mViewPager.getCurrentItem();
                        if (i==images.length-1){
                            i=0;
                            mViewPager.setCurrentItem(i,true);
                        }
                        else{
                            i++;
                            mViewPager.setCurrentItem(i,true);
                        }
                    }
                });
            }
        },4000,4000);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
