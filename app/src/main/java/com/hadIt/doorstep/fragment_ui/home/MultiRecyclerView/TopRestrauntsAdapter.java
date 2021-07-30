package com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.TopProductsModel;
import com.hadIt.doorstep.fragment_ui.home.ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopRestrauntsAdapter extends RecyclerView.Adapter<TopRestrauntsAdapter.MyViewHoll> {
    private Context context;
    private ArrayList<TopRestraunts_Model> itemData;

    public TopRestrauntsAdapter(Context context,ArrayList<TopRestraunts_Model> itemData) {
        this.context = context;
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MyViewHoll onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.top_restraunts,parent,false);
        return new MyViewHoll(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoll holder,final int position) {


        TopRestraunts_Model topProductsModels=itemData.get(position);
        holder.Price.setText(topProductsModels.productPrice);
        holder.sorname.setText(topProductsModels.productName);
        Glide.with(context).load(topProductsModels.productIcon).into(holder.item_image);


    }

    @Override
    public int getItemCount() {
        return (itemData!=null?itemData.size():0);
    }

    public  class  MyViewHoll extends RecyclerView.ViewHolder{
        private ImageView item_image;
        private TextView sorname;
        private TextView Price,Discounted;

        public MyViewHoll(@NonNull View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_image);
            sorname=itemView.findViewById(R.id.productname);
            Price=itemView.findViewById(R.id.price);
            Discounted=itemView.findViewById(R.id.original);
        }
    }
}
