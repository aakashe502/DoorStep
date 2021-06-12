package com.hadIt.doorstep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.cache.model.Products;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.order_details.OrdersActivity;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class PickOrderAdapter extends RecyclerView.Adapter<PickOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderDetails> dataList;
    private List<Products> productsList;
    private FirebaseFirestore firebaseFirestore;

    public PickOrderAdapter(Context context, List<OrderDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PickOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderDetails orderDetails = dataList.get(position);
        holder.orderId.setText(orderDetails.orderId);
        holder.amount.setText("\u20B9" + orderDetails.totalAmount);
        holder.shopName.setText(orderDetails.sellerShopName);
        holder.date.setText(orderDetails.orderDate);
        holder.status.setText(orderDetails.orderStatus);
        if(orderDetails.orderStatus.equals(OrderStatus.Cancelled.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorRed));
        else if(orderDetails.orderStatus.equals(OrderStatus.Completed.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        if(orderDetails.orderStatus.equals(OrderStatus.InProgress.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorBlue));
        if(orderDetails.orderStatus.equals(OrderStatus.Pending.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorLightBlue));

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsList = new ArrayList<>();
                getProductList(orderDetails.orderId, orderDetails);
            }
        });
    }

    private void getProductList(String orderId, final OrderDetails orderDetails) {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("userOrders").document(orderId).collection("productItems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot dpc:task.getResult().getDocuments()){
                                Products products = dpc.toObject(Products.class);
                                productsList.add(products);
                            }
                            Intent intent = new Intent(context, OrderDetailsActivity.class);
                            intent.putExtra("orderDetailsObj", orderDetails);
                            intent.putExtra("productItems", new Gson().toJson(productsList));
                            context.startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<OrderDetails> dataList) {
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId, date, shopName, amount, status;
        public CardView order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            date = itemView.findViewById(R.id.date);
            shopName = itemView.findViewById(R.id.shopName);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            order = itemView.findViewById(R.id.order);
        }
    }
}
