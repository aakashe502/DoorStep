package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.TopProductsModel;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.GroceryListAdapter;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.TopProductsAdapter;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.TopRestrauntsAdapter;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.TopRestraunts_Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator3;

import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.LayoutOne;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.LayoutTwo;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.Layoutfour;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.Layoutthree;

public class ModelAdapter extends RecyclerView.Adapter {

    private List<MergedModelClass> itemClassList;

    public ModelAdapter(List<MergedModelClass> itemClassList) {
        this.itemClassList = itemClassList;
    }
    public  ModelAdapter(){

    }

    @Override
    public int getItemViewType(int position) {
        switch (itemClassList.get(position).getViewType()) {
            case 0:
                return LayoutOne;
            case 1:
                return LayoutTwo;
            case 2:
                return Layoutthree;
            case 3:
                return Layoutfour;
            default:
                return -1;
        }
    }


    class ModelClassViewholder
            extends RecyclerView.ViewHolder{
       RecyclerView recyclerview;

        public ModelClassViewholder(@NonNull View itemView)
        {
            super(itemView);
           recyclerview=itemView.findViewById(R.id.recyclerview);
        }
    }

    class TopPicksModelViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView12;
        public TopPicksModelViewHolder(@NonNull View itemView)
        {
            super(itemView);
            recyclerView12=itemView.findViewById(R.id.recyclerview12);
        }
    }
    class TopRestrauntsViewholder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView123;
        public TopRestrauntsViewholder(@NonNull View itemView)
        {
            super(itemView);
            recyclerView123=itemView.findViewById(R.id.recyclerview123);
        }
    }
    class BannerViewholder extends RecyclerView.ViewHolder {
        ViewPager2 mViewPager;
        CircleIndicator3 circleIndicator;
        public BannerViewholder(@NonNull View itemView)
        {
            super(itemView);
            mViewPager = itemView.findViewById(R.id.viewPagerMain);
            circleIndicator=itemView.findViewById(R.id.circleindicator);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        switch (viewType) {
            case LayoutOne:
                View layoutOne
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grocerylistrecycler, parent,
                                false);
                return new ModelClassViewholder(layoutOne);
            case LayoutTwo:
                View layoutTwo
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.toppick_productsui, parent,
                                false);
                return new TopPicksModelViewHolder(layoutTwo);
            case Layoutthree:
                View layoutthree=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_toprestraunts,parent,false);
                return new TopRestrauntsViewholder(layoutthree);
            case Layoutfour:
                View layoutfour=LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_slider,parent,false);
                return new BannerViewholder(layoutfour);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,int position) {
        switch (itemClassList.get(position).getViewType()) {
            case LayoutOne:

                List<ModelClass> modelClasses= (List<ModelClass>) itemClassList.get(position).modelClass;
                GroceryListAdapter groceryListAdapter=new GroceryListAdapter((ArrayList<ModelClass>) modelClasses,holder.itemView.getContext());
                GridLayoutManager gridLayoutManager=new GridLayoutManager(holder.itemView.getContext(),4, LinearLayoutManager.VERTICAL,false);
                ((ModelClassViewholder)holder).recyclerview.setLayoutManager(gridLayoutManager);
                ((ModelClassViewholder) holder).recyclerview.setAdapter(groceryListAdapter);
                break;
//                String text
//                        = itemClassList.get(position).getText();
//                ((LayoutOneViewHolder)holder).setView(text);

                // The following code pops a toast message
                // when the item layout is clicked.
                // This message indicates the corresponding
                // layout.



            case LayoutTwo:

                List<TopProductsModel> models=itemClassList.get(position).topPicksModelclass;
                TopProductsAdapter adapter=new TopProductsAdapter(holder.itemView.getContext(),models);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(holder.itemView.getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
                ((TopPicksModelViewHolder)holder).recyclerView12.setLayoutManager(gridLayoutManager1);
                ((TopPicksModelViewHolder)holder).recyclerView12.setAdapter(adapter);
                break;
            case Layoutthree:
                List<TopRestraunts_Model> model=itemClassList.get(position).topRestraunts_models;
                TopRestrauntsAdapter adapter1=new TopRestrauntsAdapter(holder.itemView.getContext(),(ArrayList<TopRestraunts_Model>) model);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(holder.itemView.getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
                ((TopRestrauntsViewholder)holder).recyclerView123.setLayoutManager(gridLayoutManager2);
                ((TopRestrauntsViewholder)holder).recyclerView123.setAdapter(adapter1);
                break;
            case Layoutfour:
                final int[] images = {R.drawable.a1, R.drawable.lyptus_theme, R.drawable.a2, R.drawable.a3};
                final ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(holder.itemView.getContext(), images);
                final Handler handler;
                handler=new Handler(Looper.myLooper());

                // Adding the Adapter to the ViewPager
                ((BannerViewholder)holder).mViewPager.setAdapter(mViewPagerAdapter);
                ((BannerViewholder)holder).circleIndicator.setViewPager(((BannerViewholder)holder).mViewPager);
                 Timer timer;
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                int i=((BannerViewholder)holder).mViewPager.getCurrentItem();
                                if (i==images.length-1){
                                    i=0;
                                    ((BannerViewholder)holder).mViewPager.setCurrentItem(i,true);
                                }
                                else{
                                    i++;
                                    ((BannerViewholder)holder).mViewPager.setCurrentItem(i,true);
                                }
                            }
                        });
                    }
                },500,4000);

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {

        return itemClassList.size();
    }
}
