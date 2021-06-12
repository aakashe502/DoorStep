package com.hadIt.doorstep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.hadIt.doorstep.Adapter.DataAdapter;
import com.hadIt.doorstep.cache.model.Admin;
import com.hadIt.doorstep.roomDatabase.orders.DataDatabase;
import com.hadIt.doorstep.roomDatabase.orders.DataViewModal;
import com.hadIt.doorstep.address.SelectAddress;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.roomDatabase.orders.model.Data;

import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.cache.model.Products;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.utils.Constants;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Data> dataList;
    private DataViewModal dataViewModal;
    private List<Products> productsList;

    private DataAdapter dataAdapter;
    private List<Data> getDataList;
    private int get_priority;
    private int id=1;
    private Data data;
    public int length=0;
    private Button checkout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    double sum=0.0;
    public TextView address, addressSelected;
    private ImageButton backBtn;
    BottomNavigationView bottomNavigationView;
    private String todaysDate;
    private PaperDb paperDb;
    public String Tag_Address = "Address Added";
    private OrderDetails orders;

    Toolbar toolbar;
    final String timestamp = ""+System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        dataList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDataList=new ArrayList<>();
        productsList = new ArrayList<>();
        dataViewModal=new ViewModelProvider(this).get(DataViewModal.class);
        checkout=findViewById(R.id.checkout);
        firebaseAuth = FirebaseAuth.getInstance();
        paperDb = new PaperDb();
        backBtn = findViewById(R.id.backBtn);
        addressSelected = findViewById(R.id.addressSelected);
        firebaseFirestore = FirebaseFirestore.getInstance();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataAdapter = new DataAdapter(this, getDataList);

        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                dataAdapter.getAllDatas(dataList);
                for(Data ds:dataList) {
                    Log.i("appbar","name="+ds.getName()+ " image="+ds.getImage()+" rate"+ds.getRate()+" quantity="+ds.getQuantity()+" id"+ds.getId());
                    sum += Integer.parseInt(ds.getQuantity())*Integer.parseInt(ds.getRate());
                    length += Integer.parseInt(ds.getQuantity());
                    getDataList.add(ds);
                }
                recyclerView.setAdapter(dataAdapter);
                checkout.setText("Proceed To Checkout = " + "\u20B9" + sum);
            }
        });

        getTodaysDate();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        if(paperDb.getAddressFromPaperDb() != null){
            addressSelected.setText(paperDb.getAddressFromPaperDb().toString());
        }

        address = findViewById(R.id.address);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, SelectAddress.class));
            }
        });
    }

    private void getTodaysDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        todaysDate = df.format(c);
    }

    private void submitOrder(){
        AddressModelClass addressModelClass = paperDb.getAddressFromPaperDb();
        if(addressModelClass != null){
            Users users = paperDb.getUserFromPaperDb();
            AddressModelClass userAddress = paperDb.getAddressFromPaperDb();

            createOrdersObject(users, userAddress, timestamp);
        }
        else{
            Toast.makeText(this, "Please select Address First", Toast.LENGTH_SHORT).show();
        }
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
            notificationBodyJo.put("orderDetailsObj", new Gson().toJson(orders));
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
                intent.putExtra("orderDetailsObj", orders);
                intent.putExtra("productItems", new Gson().toJson(productsList));
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if failed sending fcm, still start order details activity
                Intent intent = new Intent(CheckoutActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderDetailsObj", orders);
                intent.putExtra("productItems", new Gson().toJson(productsList));
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

    private void saveOrderDetailsFirst(final String orderId) {

        dataViewModal.getCheckoutdata().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                for(Data ds:dataList) {
                    productsList.add(new Products(ds.getId(), ds.getName(), ds.getRate(), ds.getQuantity(), ds.getImage()));
                    firebaseFirestore.collection("userOrders").document(orderId).collection("productItems").document(ds.getId()).set(ds)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CheckoutActivity.this,"Order Address Saved",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(Tag_Address, "Order Address Failed...");
                                Toast.makeText(CheckoutActivity.this,"Order Address Failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });

        firebaseFirestore.collection("userOrders").document(orderId).set(orders)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CheckoutActivity.this,"Order Address Saved",Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(Tag_Address, "Order Address Failed...");
                    Toast.makeText(CheckoutActivity.this,"Order Address Failed",Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void createOrdersObject(final Users users, final AddressModelClass userAddress, final String orderId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String shopUid = DataDatabase.getInstance(getApplicationContext())
                        .dataDao()
                        .getShopUid();

                firebaseFirestore.collection("admin").whereEqualTo("uid", shopUid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Admin admin = task.getResult().getDocuments().get(0).toObject(Admin.class);

                                    orders = new OrderDetails(todaysDate, orderId, OrderStatus.Pending.name(), users.emailId, firebaseAuth.getUid(),
                                            userAddress.firstName+" "+userAddress.lastName, userAddress.contactNumber, userAddress.houseNumber+"-"+userAddress.apartmentName,
                                            userAddress.landmark, userAddress.areaDetails, userAddress.city, userAddress.pincode, userAddress.latitude, userAddress.longitude,
                                            admin.shopEmail, admin.uid, admin.shopName, admin.shopPhone, admin.latitude, admin.longitude, admin.city, "sellerPincode",
                                            "sellerAreaDetails", "sellerLandmark", sum, length
                                    );
                                    saveOrderDetailsFirst(orderId);
                                    prepareNotificationMessage(timestamp);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CheckoutActivity.this,"Failed to create orders object",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).start();
    }
}
