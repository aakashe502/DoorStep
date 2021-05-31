package com.hadIt.doorstep.order_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;

public class OrderDetailsActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView orderIdTv, dateTv, orderStatusTv, shopEmailTv, shopPhoneTv, totalItemsTv, amountTv, deliveryAddressTv;
    private OrderDetails orderDetails;

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

        orderDetails = (OrderDetails) getIntent().getSerializableExtra("orderDetailsObj");

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
        amountTv.setText("\u20B9" + String.valueOf(orderDetails.totalAmount));
        deliveryAddressTv.setText(orderDetails.usersAddress());
    }
}