package com.hadIt.doorstep.fragment_ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.fragment_ui.notifications.NotificationActivity;
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
    int[] images = {R.drawable.a1, R.drawable.lyptus_theme, R.drawable.a2, R.drawable.a3};
    // Creating Object of ViewPagerAdapter
    Timer timer;
    Handler handler;
    TextView cardSearch;
    private PaperDb paperDb;
    private int mCartItemCount = 0;
    public TextView textCartItemCount;

    public View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);

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
        handler=new Handler(Looper.myLooper());
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
        },500,4000);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addcart, menu);

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

        super.onCreateOptionsMenu(menu,inflater);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:
                startActivity(new Intent(getActivity(), NotificationActivity.class));
                break;
            case R.id.action_carta:
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(textCartItemCount!=null)
            textCartItemCount.setText(""+mCartItemCount);
    }
}
