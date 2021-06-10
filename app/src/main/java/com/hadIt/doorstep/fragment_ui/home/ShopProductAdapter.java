package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.Repository.DataRepository;
import com.hadIt.doorstep.ViewModa.DataViewModal;
import com.hadIt.doorstep.cache.model.AdminProductModel;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.fragment_ui.Interfaces.Datatransfer;

import java.util.ArrayList;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ViewHolder> {
    ArrayList<AdminProductModel> productInfoModels;
    Context context;

    public Datatransfer datatransfer;
    public ArrayList<Data> addtocartArrayList=new ArrayList<>();

    ArrayList<Data> savearraylist=new ArrayList<>();
    public ShopProductAdapter(ArrayList<AdminProductModel> arrayList,Context context, Datatransfer datatransfer) {
        this.datatransfer = datatransfer;
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
        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.addbutton.setVisibility(View.GONE);
                holder.linear.setVisibility(View.VISIBLE);


               // Data cart=new Data((String.valueOf(addtocartArrayList.size())),productInfoModels.get(position).productIcon,productInfoModels.get(position).productName,productInfoModels.get(position).productPrice,holder.numb.getText().toString());
                //savearraylist.add(cart);
               // datatransfer.onSetValues(savearraylist);
//                savearraylist.add(new Data(productInfoModels.get(position).productIcon,productInfoModels.get(position).productName,productInfoModels.get(position).productPrice,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString()));
//                datatransfer.onSetValues(savearraylist);
                // savearraylist.add(arrayList.get(position));
//                Data cart=new Data(productInfoModels.get(position).productIcon,productInfoModels.get(position).productName,productInfoModels.get(position).productPrice,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//                //addtocartArrayList.add(cart);
//                datatransfer.onSetValues(cart);
                Data cart=new Data((String.valueOf(addtocartArrayList.size())),productInfoModels.get(position).productName,productInfoModels.get(position).productPrice,productInfoModels.get(position).productIcon,holder.numb.getText().toString());
                //addtocartArrayList.add(cart);
               datatransfer.onSetValues(cart);
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.numb.setText((Integer.parseInt(holder.numb.getText().toString())+1)+"");
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.numb.setText((Integer.parseInt(holder.numb.getText().toString())-1)+"");
                if(Integer.parseInt(holder.numb.getText().toString())==0){
                    holder.addbutton.setVisibility(View.VISIBLE);
                    holder.linear.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return productInfoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname;
        public TextView productrate,number;
        public Button addbutton;
        LinearLayout linear;
        Button minus,plus;
        TextView numb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.itemImage1);
            productname= itemView.findViewById(R.id.itemName);
            productrate=itemView.findViewById(R.id.itemCost);
            addbutton=itemView.findViewById(R.id.addbutton);
            linear=itemView.findViewById(R.id.linear);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);
            numb=itemView.findViewById(R.id.number);
        }
    }
}
