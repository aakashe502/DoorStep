package com.hadIt.doorstep.order_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.hadIt.doorstep.Adapter.OrderDetailsProductAdapter;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;
import com.hadIt.doorstep.roomDatabase.orders.items.OrderItemsRepository;
import com.hadIt.doorstep.roomDatabase.orders.items.OrderItemsTransfer;
import com.hadIt.doorstep.roomDatabase.orders.items.OrderItemsViewModel;
import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity implements OrderItemsTransfer {

    private ImageButton backBtn;
    private TextView orderIdTv, dateTv, orderStatusTv, shopEmailTv, shopPhoneTv, totalItemsTv, amountTv,
            deliveryAddressTv, deliveryBoyName, deliveryBoyPhone;
    private OrderDetailsRoomModel orderDetailsRoomModel;
    private RecyclerView itemsRv;
    private List<ProductsTable> getProductsList;
    private OrderDetailsProductAdapter orderDetailsProductAdapter;

    private FirebaseFirestore firebaseFirestore;
    private PaperDb paperDb;
    private String orderId;
    private OrderItemsViewModel orderItemsViewModel;
    private OrderItemsRepository orderItemsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orderIdTv = findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        orderStatusTv = findViewById(R.id.orderStatusTv);
        shopEmailTv = findViewById(R.id.shopEmailTv);
        shopPhoneTv = findViewById(R.id.shopPhoneTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        amountTv = findViewById(R.id.amountTv);
        deliveryAddressTv = findViewById(R.id.deliveryAddressTv);
        itemsRv = findViewById(R.id.itemsRv);
        deliveryBoyName = findViewById(R.id.deliveryBoyName);
        deliveryBoyPhone = findViewById(R.id.deliveryBoyPhone);

        orderDetailsRoomModel = (OrderDetailsRoomModel) getIntent().getSerializableExtra("orderDetailsObj");

        orderId = getIntent().getStringExtra("orderId");
        getProductList();

        orderIdTv.setText(String.valueOf(orderDetailsRoomModel.getOrderId()));
        dateTv.setText(String.valueOf(orderDetailsRoomModel.getOrderDate()));

        if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Pending.name())){
            orderStatusTv.setText(OrderStatus.Pending.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
        }
        else if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.InProgress.name())){
            orderStatusTv.setText(OrderStatus.InProgress.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorBlue));
        }
        else if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.OutForDelivery.name())){
            orderStatusTv.setText(OrderStatus.OutForDelivery.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Completed.name())){
            orderStatusTv.setText(OrderStatus.Completed.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Cancelled.name())){
            orderStatusTv.setText(OrderStatus.Cancelled.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorRed));
        }

        shopEmailTv.setText(String.valueOf(orderDetailsRoomModel.getSellerEmail()));
        shopPhoneTv.setText(String.valueOf(orderDetailsRoomModel.getSellerMobileNumber()));
        totalItemsTv.setText(String.valueOf(orderDetailsRoomModel.getItemCount()));
        amountTv.setText("\u20B9" + orderDetailsRoomModel.getTotalAmount());
        deliveryAddressTv.setText(orderDetailsRoomModel.usersAddress());
        itemsRv.setAdapter(orderDetailsProductAdapter);

        if(orderDetailsRoomModel.getDeliveryBoyName() != null){
            deliveryBoyName.setText(orderDetailsRoomModel.getDeliveryBoyName());
            deliveryBoyPhone.setText(orderDetailsRoomModel.getDeliveryBoyMobile());
        }
    }

    private void getProductList() {
        orderItemsViewModel = new OrderItemsViewModel(getApplication(), orderId);
        orderItemsRepository = new OrderItemsRepository(getApplication(), orderId);

        orderItemsViewModel.getItemsForOrderId(orderId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)
                    storeInRoomDb(orderId);
                else{
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ProductsTable>>(){}.getType();
                    getProductsList = gson.fromJson(s, listType);
                    orderDetailsProductAdapter = new OrderDetailsProductAdapter(OrderDetailsActivity.this, getProductsList);
                    itemsRv.setAdapter(orderDetailsProductAdapter);
                }
            }
        });
    }

    private void storeInRoomDb(final String orderId) {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("userOrders").document(orderId).collection("productItems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<ProductsTable> productsArrayList = new ArrayList<>();
                            for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                                ProductsTable products = dpc.toObject(ProductsTable.class);
                                productsArrayList.add(products);
                            }
                            OrderItemsRoomModel orderItemsRoomModel = new OrderItemsRoomModel(orderId, new Gson().toJson(productsArrayList));
                            setOrderItems(orderItemsRoomModel);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderDetailsActivity.this, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void setOrderItems(OrderItemsRoomModel orderItemsRoomModel) {
        orderItemsRepository.insert(orderItemsRoomModel);
    }

    @Override
    public void deleteOrderItems(OrderItemsRoomModel orderItemsRoomModel) {
        orderItemsRepository.deleteProductUsingModelObj(orderItemsRoomModel);
    }
}