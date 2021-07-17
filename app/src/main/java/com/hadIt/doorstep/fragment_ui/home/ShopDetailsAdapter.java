package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Admin;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;

import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;

import java.util.ArrayList;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ViewHolder> {

    ArrayList<Admin> arrayList;
    Context context;
    public DataTransfer datatransfer;
    public ArrayList<Data> addtocartArrayList=new ArrayList<>();

    public ShopDetailsAdapter(ArrayList<Admin> arrayList, Context context, DataTransfer datatransfer) {
        this.arrayList = arrayList;
        this.context = context;
        this.datatransfer = datatransfer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_ui, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.productname.setText(arrayList.get(position).shopName);
        holder.shopLocation.setText(arrayList.get(position).city);

       // Glide.with(context).load(arrayList.get(position).profileImage).into(holder.productimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewShopProducts.class);
                intent.putExtra("shopUid",arrayList.get(position).uid);
                intent.putExtra("shopType", arrayList.get(position).shoptype);
                intent.putExtra("shopName", arrayList.get(position).shopName);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname;
        public TextView shopLocation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.shopPhoto);
            productname= itemView.findViewById(R.id.shopName);
           shopLocation=itemView.findViewById(R.id.shopLocation);
        }
    }
}
