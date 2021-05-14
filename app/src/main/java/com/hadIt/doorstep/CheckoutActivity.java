package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.hadIt.doorstep.Adapter.DataAdapter;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.Data;

import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.utils.Constants;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import java.util.Map;

public class CheckoutActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private List<Data> dataList;
    private DataViewModal dataViewModal;

    private DataAdapter dataAdapter;
    private List<Data> getDataList;
    private int get_priority;
    private int id=1;
    private Data data;
    public int length=0;
    private Button checkout;
    private FirebaseAuth firebaseAuth;
    int sum=0;

    Toolbar toolbar;
    final String timestamp = ""+System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle("Cart items");


        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);

        dataList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDataList=new ArrayList<>();
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        checkout=findViewById(R.id.checkout);

        setContentView(R.layout.activity_checkout);
        firebaseAuth = FirebaseAuth.getInstance();

        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDataList = new ArrayList<>();
        dataViewModal = new ViewModelProvider(this).get(DataViewModal.class);
        checkout = findViewById(R.id.checkout);

        dataAdapter = new DataAdapter(this,getDataList);

        dataViewModal.getAllData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                length=dataList.size();
                dataAdapter.getAllDatas(dataList);
                for(Data ds:dataList) {
                    Log.i("appbar","name="+ds.getName()+ " image="+ds.getImage()+" rate"+ds.getRate()+" quantity="+ds.getQuantity()+" id"+ds.getId());
                    sum += Integer.parseInt(ds.getQuantity())*Integer.parseInt(ds.getRate());
                }
                recyclerView.setAdapter(dataAdapter);
                checkout.setText("Checkout = " + "Rs " + sum);
            }
        });
    }

    private void submitOrder(){
        prepareNotificationMessage(timestamp);
    }

    private void prepareNotificationMessage(String orderId){
//        When user places order, send notification to seller

        //prepare data for notification
        String NOTIFICATION_TOPIC = "/topics/" + Constants.FCM_TOPIC; //must be same as subscribed by user
        String NOTIFICATION_TITLE = "New Order: " +  orderId;
        String NOTIFICATION_MESSAGE = "Congratulations...! You have new order.";
        String NOTIFICATION_TYPE = "NewOrder";

        //prepare json (what to send and where to send)
        JSONObject notificationJo = new JSONObject();
        JSONObject notificationBodyJo = new JSONObject();
        try {
            //what to send
            notificationBodyJo.put("notificationType", NOTIFICATION_TYPE);
            notificationBodyJo.put("buyerId", firebaseAuth.getUid());
            notificationBodyJo.put("sellerUid", null);
            notificationBodyJo.put("orderId", orderId);
            notificationBodyJo.put("notificationTitle", NOTIFICATION_TITLE);
            notificationBodyJo.put("notificationMessage", NOTIFICATION_MESSAGE);
            //where to send
            notificationJo.put("to", NOTIFICATION_TOPIC); //to all who subscribed to this topic
            notificationJo.put("data", notificationBodyJo);

        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendFcmNotification(notificationJo, orderId);
    }

    private void sendFcmNotification(JSONObject notificationJo, final String orderId) {
        //send volly request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //after sending fcm start order details activity
                Intent intent = new Intent(CheckoutActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderTo", "shopUid");
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if failed sending fcm, still start order details activity
                Intent intent = new Intent(CheckoutActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderTo", "shopUid");
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //required headers
                Map<String, String> headers=new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key=" + Constants.FCM_KEY);

                return headers;
            }
        };

        //enqueue the volly request
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
