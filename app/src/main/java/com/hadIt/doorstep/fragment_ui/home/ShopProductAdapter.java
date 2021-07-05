package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
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

import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.ArrayList;

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ViewHolder> {
    ArrayList<ProductsTable> productInfoModels;
    Context context;

    public DataTransfer datatransfer;

    public ShopProductAdapter(ArrayList<ProductsTable> arrayList, Context context, DataTransfer datatransfer) {
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
        holder.productname.setText(productInfoModels.get(position).getProductName());
        holder.productrate.setText(productInfoModels.get(position).getProductPrice());
        Glide.with(context).load(productInfoModels.get(position).getProductIcon()).into(holder.productimage);
        holder.unit.setText(productInfoModels.get(position).getUnit());

        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addbutton.setVisibility(View.GONE);
                holder.linear.setVisibility(View.VISIBLE);
                Data cart=new Data(productInfoModels.get(position).getShopUid() + productInfoModels.get(position).getProductName(), productInfoModels.get(position).getProductName(),
                        productInfoModels.get(position).getProductPrice(), productInfoModels.get(position).getProductIcon(), holder.numb.getText().toString(), productInfoModels.get(position).getShopUid());

                datatransfer.onSetValues(cart);
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.numb.setText((Integer.parseInt(holder.numb.getText().toString())+1)+"");
                Data cart=new Data(productInfoModels.get(position).getShopUid() + productInfoModels.get(position).getProductName(), productInfoModels.get(position).getProductName(),
                        productInfoModels.get(position).getProductPrice(), productInfoModels.get(position).getProductIcon(), holder.numb.getText().toString(), productInfoModels.get(position).getShopUid());

                datatransfer.onSetValues(cart);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.numb.setText((Integer.parseInt(holder.numb.getText().toString())-1)+"");
                if(Integer.parseInt(holder.numb.getText().toString())<=0){
                    holder.numb.setText("0");
                    holder.addbutton.setVisibility(View.VISIBLE);
                    holder.linear.setVisibility(View.GONE);
                    Data cart=new Data(productInfoModels.get(position).getShopUid() + productInfoModels.get(position).getProductName(), productInfoModels.get(position).getProductName(),
                            productInfoModels.get(position).getProductPrice(), productInfoModels.get(position).getProductIcon(), holder.numb.getText().toString(), productInfoModels.get(position).getShopUid());

                    datatransfer.onDelete(cart);
                }
                else{
                    Data cart=new Data(productInfoModels.get(position).getShopUid() + productInfoModels.get(position).getProductName(), productInfoModels.get(position).getProductName(),
                            productInfoModels.get(position).getProductPrice(), productInfoModels.get(position).getProductIcon(), holder.numb.getText().toString(), productInfoModels.get(position).getShopUid());

                    datatransfer.onDelete(cart);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productInfoModels.size();
    }

    public void filterList(ArrayList<ProductsTable> filteredList){
        productInfoModels = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname, productrate, number;
        public Button addbutton;
        LinearLayout linear;
        Button minus,plus;
        TextView numb;
        TextView unit;
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
            unit=itemView.findViewById(R.id.value);
        }
    }
}
