package com.hadIt.doorstep.fragment_ui.home;

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
import com.hadIt.doorstep.cache.model.AdminProductModel;

import java.util.ArrayList;

public class AdminAddapter extends RecyclerView.Adapter<AdminAddapter.ViewHolder> {
    ArrayList<AdminProductModel> productInfoModels;
    Context context;

    ArrayList<AdminProductModel> savearraylist=new ArrayList<>();
    public AdminAddapter(ArrayList<AdminProductModel> arrayList,Context context) {

        this.productInfoModels = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopproducts, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

        holder.productname.setText(productInfoModels.get(position).productName);
        holder.productrate.setText(productInfoModels.get(position).productPrice);
        Glide.with(context).load(productInfoModels.get(position).productIcon).into(holder.productimage);
    }
    @Override
    public int getItemCount() {
        return productInfoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname;
        public TextView productrate,number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.itemImage1);
            productname= itemView.findViewById(R.id.itemName);
            productrate=itemView.findViewById(R.id.itemCost);
        }
    }
}
