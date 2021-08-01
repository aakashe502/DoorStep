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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.TopProductsModel;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView.TopRestraunts_Model;
import com.hadIt.doorstep.fragment_ui.notifications.NotificationActivity;
import com.hadIt.doorstep.roomDatabase.cart.DataViewModal;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator3;

import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.LayoutOne;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.LayoutTwo;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.Layoutfour;
import static com.hadIt.doorstep.fragment_ui.home.MergedModelClass.Layoutthree;

public class HomeFragment extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<ModelClass> model;
    public List< MergedModelClass> mergedModelClasses;
    public List<TopProductsModel> topProductsModels;
    public List<TopRestraunts_Model> topRestraunts_models;

    // images array

    // Creating Object of ViewPagerAdapter


    TextView cardSearch;
    private PaperDb paperDb;
    private int mCartItemCount = 0;
    public TextView textCartItemCount;
    private DataViewModal dataViewModal;

    public View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        root = inflater.inflate(R.layout.fragment_home, container,false);
        paperDb = new PaperDb();
        Users users = paperDb.getUserFromPaperDb();
        getActivity().setTitle("Lyptus");

        cardSearch=root.findViewById(R.id.cardSearch);

        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

        // Initializing the ViewPagerAdapter

        recyclerView=root.findViewById(R.id.recyclerview1);
        model= new ArrayList<>();

        mergedModelClasses=  new ArrayList<>();
        topProductsModels=new ArrayList<TopProductsModel>();
        topRestraunts_models=new ArrayList<>();

        FirebaseFirestore.getInstance().collection("Products").whereEqualTo("productCategory", "VEGETABLES & FRUITS Others")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot dpc : task.getResult().getDocuments()) {
                                TopRestraunts_Model productsTable1 = dpc.toObject(TopRestraunts_Model.class);
                                topRestraunts_models.add(productsTable1);
                            }
                            FirebaseFirestore.getInstance().collection("Products").whereEqualTo("productCategory", "VEGETABLES & FRUITS Others")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot dpc : task.getResult().getDocuments()) {
                                                    TopProductsModel productsTable1 = dpc.toObject(TopProductsModel.class);
                                                    topProductsModels.add(productsTable1);
                                                }
                                                createView();
                                            }
                                        }
                                    });
                        }
                    }
                });


        return root;
    }

    private void createView() {
        model.add(new ModelClass("VEGETABLES & FRUITS", R.drawable.vegetables));
        model.add(new ModelClass("GROCERY", R.drawable.bake));
        model.add(new ModelClass("BEVERAGES", R.drawable.beverages));
        model.add(new ModelClass("NON-VEG", R.drawable.meat));
        model.add(new ModelClass("CAKES & BAKERY", R.drawable.desert));
        model.add(new ModelClass("HARVEST", R.drawable.harvest));
        model.add(new ModelClass("BREAD", R.drawable.bread));
        model.add(new ModelClass("CLEANING", R.drawable.cleaning));
        model.add(new ModelClass("BOOKS & STATIONERY", R.drawable.books_and_stationary));
        model.add(new ModelClass("NUTRITION & HEALTHCARE", R.drawable.nutrition_and_healthcare));
        model.add(new ModelClass("HOME-MADE", R.drawable.home_made));
        model.add(new ModelClass("DAIRY PRODUCTS", R.drawable.dairy_products));

        mergedModelClasses.add(new MergedModelClass(Layoutfour));
        mergedModelClasses.add(new MergedModelClass(model,LayoutOne));
        mergedModelClasses.add(new MergedModelClass(LayoutTwo,topProductsModels));
        mergedModelClasses.add(new MergedModelClass(topRestraunts_models,Layoutthree));

        ModelAdapter modelAdapter=new ModelAdapter(mergedModelClasses);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(modelAdapter);
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

        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                int total = 0;
                for(Data data: dataList)
                    total += Integer.parseInt(data.getProductQuantity());
                mCartItemCount=total;
                if(textCartItemCount!=null){
                    textCartItemCount.setText(""+mCartItemCount);
                }
            }
        });
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
