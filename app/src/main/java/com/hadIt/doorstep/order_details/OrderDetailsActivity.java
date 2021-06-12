package com.hadIt.doorstep.order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.hadIt.doorstep.Adapter.OrderDetailsProductAdapter;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.cache.model.Products;
import com.hadIt.doorstep.dao.PaperDb;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView orderIdTv, dateTv, orderStatusTv, shopEmailTv, shopPhoneTv, totalItemsTv, amountTv, deliveryAddressTv;
    private OrderDetails orderDetails;
    private RecyclerView itemsRv;
    private List<Products> getProductsList;
    private OrderDetailsProductAdapter orderDetailsProductAdapter;

    private FirebaseFirestore firebaseFirestore;
    private PaperDb paperDb;

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

        orderDetails = (OrderDetails) getIntent().getSerializableExtra("orderDetailsObj");

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Products>>(){}.getType();
        getProductsList = gson.fromJson(getIntent().getStringExtra("productItems"), listType);

        orderDetailsProductAdapter = new OrderDetailsProductAdapter(this, getProductsList);

        orderIdTv.setText(String.valueOf(orderDetails.orderId));
        dateTv.setText(String.valueOf(orderDetails.orderDate));

        if(orderDetails.orderStatus.equals(OrderStatus.Pending.name())){
            orderStatusTv.setText(OrderStatus.Pending.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
        }
        else if(orderDetails.orderStatus.equals(OrderStatus.InProgress.name())){
            orderStatusTv.setText(OrderStatus.InProgress.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorBlue));
        }
        else if(orderDetails.orderStatus.equals(OrderStatus.Completed.name())){
            orderStatusTv.setText(OrderStatus.Completed.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else if(orderDetails.orderStatus.equals(OrderStatus.Cancelled.name())){
            orderStatusTv.setText(OrderStatus.Cancelled.name());
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorRed));
        }

        shopEmailTv.setText(String.valueOf(orderDetails.sellerEmail));
        shopPhoneTv.setText(String.valueOf(orderDetails.sellerMobileNumber));
        totalItemsTv.setText(String.valueOf(orderDetails.itemCount));
        amountTv.setText("\u20B9" + orderDetails.totalAmount);
        deliveryAddressTv.setText(orderDetails.usersAddress());
        itemsRv.setAdapter(orderDetailsProductAdapter);
    }
}