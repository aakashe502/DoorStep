package com.hadIt.doorstep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

import java.util.List;

import static java.lang.Thread.sleep;

public class PickOrderAdapter extends RecyclerView.Adapter<PickOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderDetailsRoomModel> dataList;
    private FirebaseFirestore firebaseFirestore;

    public PickOrderAdapter(Context context, List<OrderDetailsRoomModel> dataList) {
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
        final OrderDetailsRoomModel orderDetailsRoomModel = dataList.get(position);
        holder.orderId.setText(orderDetailsRoomModel.getOrderId());
        holder.amount.setText("\u20B9" + orderDetailsRoomModel.getTotalAmount());
        holder.shopName.setText(orderDetailsRoomModel.getSellerShopName());
        holder.date.setText(orderDetailsRoomModel.getOrderDate());
        holder.status.setText(orderDetailsRoomModel.getOrderStatus());
        if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Cancelled.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorRed));
        else if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Completed.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.InProgress.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorBlue));
        if(orderDetailsRoomModel.getOrderStatus().equals(OrderStatus.Pending.name()))
            holder.status.setTextColor(context.getResources().getColor(R.color.colorLightBlue));

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderDetailsObj", orderDetailsRoomModel);
                intent.putExtra("orderId", orderDetailsRoomModel.getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<OrderDetailsRoomModel> dataList) {
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
