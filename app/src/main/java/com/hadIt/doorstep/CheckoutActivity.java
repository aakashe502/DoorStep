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
import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;
import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;
import com.hadIt.doorstep.roomDatabase.cart.DataViewModal;
import com.hadIt.doorstep.address.SelectAddress;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.cache.model.Products;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsRepository;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsTransfer;
import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;
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

public class CheckoutActivity extends AppCompatActivity implements OrderDetailsTransfer {
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
    public TextView address;
    private ImageButton backBtn;
    BottomNavigationView bottomNavigationView;
    private String todaysDate;
    private PaperDb paperDb;
    public String Tag_Address = "Address Added";
    private OrderDetailsRoomModel orderDetailsRoomModel;
    private String shopUid;
    AddressModel address_setter;

    ImageButton downarrow;

    TextView customername,housenumber,landmark,areaDetails,phoneNumber;

    Toolbar toolbar;
    final String timestamp = ""+System.currentTimeMillis();
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getShopUidFromRoomDb();

        customername=findViewById(R.id.customerName1);
        housenumber=findViewById(R.id.houseNumber1);
        landmark=findViewById(R.id.landmark1);
        areaDetails=findViewById(R.id.areaDetails1);
        phoneNumber=findViewById(R.id.phoneNumber1);
        address=findViewById(R.id.changeAddress1);

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

        firebaseFirestore = FirebaseFirestore.getInstance();
        orderDetailsRepository = new OrderDetailsRepository(getApplication(), firebaseAuth.getUid());

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

        downarrow=findViewById(R.id.downarrow);
        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView.getVisibility()== View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
                    downarrow.setImageResource(R.drawable.downarrow);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    downarrow.setImageResource(R.drawable.uparrow);
                }

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
            address_setter=paperDb.getAddressFromPaperDb();
            customername.setText(address_setter.getFirstName()+"");
            housenumber.setText(address_setter.getHouseNumber()+" ");
            landmark.setText(address_setter.getLandmark()+"");
            areaDetails.setText(address_setter.getAreaDetails()+"");
            phoneNumber.setText(address_setter.getContactNumber()+"");



        }



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
        AddressModel addressModelClass = paperDb.getAddressFromPaperDb();
        if(addressModelClass != null){
            Users users = paperDb.getUserFromPaperDb();
            AddressModel userAddress = paperDb.getAddressFromPaperDb();

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
            notificationBodyJo.put("orderDetailsObj", new Gson().toJson(orderDetailsRoomModel));
            notificationBodyJo.put("notificationTitle", NOTIFICATION_TITLE);
            notificationBodyJo.put("notificationMessage", NOTIFICATION_MESSAGE);
            //where to send
            notificationJo.put("to", NOTIFICATION_TOPIC); //to all who subscribed to this topic
            notificationJo.put("data", notificationBodyJo);

        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendFcmNotification(notificationJo);
    }

    private void sendFcmNotification(JSONObject notificationJo) {
        //send volly request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //after sending fcm start order details activity
                Intent intent = new Intent(CheckoutActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderDetailsObj", orderDetailsRoomModel);
                intent.putExtra("orderId", orderDetailsRoomModel.getOrderId());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if failed sending fcm, still start order details activity
                Intent intent = new Intent(CheckoutActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderDetailsObj", orderDetailsRoomModel);
                intent.putExtra("orderId", orderDetailsRoomModel.getOrderId());
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
                    productsList.add(new Products(ds.getId(), ds.getName(), ds.getRate(), ds.getQuantity(), ds.getImage(), ds.getUnit()));
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

        firebaseFirestore.collection("userOrders").document(orderId).set(orderDetailsRoomModel)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CheckoutActivity.this,"Order Address Saved",Toast.LENGTH_SHORT).show();
                        setOrderDetails(orderDetailsRoomModel);
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

    private void getShopUidFromRoomDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                shopUid = DataDatabase.getInstance(getApplicationContext())
                        .dataDao()
                        .getShopUid();
            }
        }).start();
    }

    private void createOrdersObject(final Users users, final AddressModel userAddress, final String orderId) {
        if(shopUid == null)
            try {
                wait(1000);
            }catch (Exception e){
                Log.i(Tag_Address, e.getMessage());
            }

        firebaseFirestore.collection("admin").whereEqualTo("uid", shopUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Admin admin = task.getResult().getDocuments().get(0).toObject(Admin.class);

                            orderDetailsRoomModel = new OrderDetailsRoomModel(todaysDate, orderId, OrderStatus.Pending.name(), users.emailId, firebaseAuth.getUid(),
                                    userAddress.getFirstName()+" "+userAddress.getLastName(), userAddress.getContactNumber(),
                                    userAddress.getHouseNumber()+"-"+userAddress.getApartmentName(), userAddress.getLandmark(),
                                    userAddress.getAreaDetails(), userAddress.getCity(), userAddress.getPincode(), userAddress.getLatitude(), userAddress.getLongitude(),
                                    admin.shopEmail, admin.uid, admin.shopName, admin.shopPhone, admin.latitude, admin.longitude, admin.city, "sellerPincode",
                                    "sellerAreaDetails", "sellerLandmark", sum, length, "", "", ""
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

    @Override
    public void setOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.insert(orderDetailsRoomModel);
    }

    @Override
    public void deleteOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.deleteProductUsingOrderId(orderDetailsRoomModel);
    }
}
