package com.hadIt.doorstep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Products;

import java.util.List;

public class OrderDetailsProductAdapter extends RecyclerView.Adapter<OrderDetailsProductAdapter.DataViewHolder> {
    private static final String TAG = "OrderDetailsProductAdapter";

    private Context context;
    private List<Products> dataList;

    public OrderDetailsProductAdapter(Context context, List<Products> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.checkoutrecycler,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder holder, final int position) {
        final Products data = dataList.get(position);
        holder.name.setText(data.getName());
        holder.ruppee.setText(data.getRate());
        holder.number.setText(data.getQuantity());
        Glide.with(context).load(data.getImage()).into(holder.image);
        holder.unit.setText(data.getUnit());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void getAllDatas(List<Products> dataList) {
        this.dataList = dataList;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView name, ruppee, number;
        ImageView image;
        TextView unit;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ruppee = itemView.findViewById(R.id.ruppee);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.image);
            unit=itemView.findViewById(R.id.value);
        }
    }
}