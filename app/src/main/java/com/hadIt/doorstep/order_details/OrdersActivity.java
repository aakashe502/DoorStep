package com.hadIt.doorstep.order_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.hadIt.doorstep.Adapter.PickOrderAdapter;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsRepository;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsTransfer;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsViewModel;
import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrderDetailsTransfer {

    private ImageButton backBtn;
    private RecyclerView recyclerView;
    private PickOrderAdapter pickOrderAdapter;
    private List<OrderDetailsRoomModel> orderDetailsList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private OrderDetailsViewModel orderDetailsViewModel;
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderDetailsList = new ArrayList<>();
        pickOrderAdapter = new PickOrderAdapter(this, orderDetailsList);
        
        getOrdersList();
        pickOrderAdapter.setDataList(orderDetailsList);
        recyclerView.setAdapter(pickOrderAdapter);
    }

    private void getOrdersList() {
        orderDetailsViewModel = new OrderDetailsViewModel(getApplication(), firebaseAuth.getUid());
        orderDetailsRepository = new OrderDetailsRepository(getApplication(), firebaseAuth.getUid());

        orderDetailsViewModel.getAllOrders().observe(this, new Observer<List<OrderDetailsRoomModel>>() {
            @Override
            public void onChanged(List<OrderDetailsRoomModel> orderDetailsRoomModels) {
                orderDetailsList.clear();
                orderDetailsList.addAll(orderDetailsRoomModels);
                if(orderDetailsList.size() == 0)
                    storeInRoomDb();
                pickOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void storeInRoomDb() {
        firebaseFirestore.collection("userOrders").whereEqualTo("buyerUid", firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                                OrderDetailsRoomModel orderDetailsRoomModel = dpc.toObject(OrderDetailsRoomModel.class);
                                setOrderDetails(orderDetailsRoomModel);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrdersActivity.this, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void setOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.insert(orderDetailsRoomModel);
    }

    @Override
    public void deleteOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.deleteProductUsingOrderId(orderDetailsRoomModel);
    }
}