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
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private static final String TAG = "main";
    private Context context;
    private List<Data> dataList;

    public DataAdapter(Context context, List<Data> dataList) {
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
        final Data data = dataList.get(position);
        holder.name.setText(data.getProductName());
        holder.ruppee.setText(data.getProductPrice());
        holder.number.setText(data.getProductQuantity());
        Glide.with(context).load(data.getProductIcon()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void getAllDatas(List<Data> dataList) {
        this.dataList = dataList;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView name, ruppee, number;
        ImageView image;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ruppee = itemView.findViewById(R.id.ruppee);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.image);
        }
    }
}