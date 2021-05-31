package com.hadIt.doorstep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.cache.model.OrderStatus;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;

import java.util.List;

public class PickOrderAdapter extends RecyclerView.Adapter<PickOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderDetails> dataList;

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
                Intent intent = getIntent(orderDetails);
                context.startActivity(intent);
            }
        });
    }

    private Intent getIntent(OrderDetails orderDetails) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("orderDetailsObj", orderDetails);
        return intent;
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
