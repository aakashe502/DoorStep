package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hadIt.doorstep.R;

import com.hadIt.doorstep.SplashScreenActivity;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.login_signup.LoginActivity;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.ArrayList;
import java.util.List;

public class ShopProductAdapter extends ListAdapter<ProductsTable, ShopProductAdapter.ViewHolder> {
    Context context;
    public DataTransfer datatransfer;

    protected ShopProductAdapter(@NonNull DiffUtil.ItemCallback<ProductsTable> diffCallback, Context context, DataTransfer dataTransfer) {
        super(diffCallback);
        this.context = context;
        this.datatransfer = dataTransfer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopproducts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.productname.setText(getItem(position).getProductName());
        holder.productrate.setText(getItem(position).getProductPrice());

        Glide.with(context).load(getItem(position).getProductIcon()).into(holder.productimage);
        holder.unit.setText(" / "+getItem(position).getUnit());

        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addbutton.setVisibility(View.GONE);
                holder.linear.setVisibility(View.VISIBLE);
                Data cart=new Data(getItem(position).getShopUid() + getItem(position).getProductName(), getItem(position).getProductName(),
                        getItem(position).getProductPrice(), getItem(position).getProductIcon(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

                datatransfer.onSetValues(cart);
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.numb.setText((Integer.parseInt(holder.numb.getText().toString())+1)+"");
                Data cart=new Data(getItem(position).getShopUid() + getItem(position).getProductName(), getItem(position).getProductName(),
                        getItem(position).getProductPrice(), getItem(position).getProductIcon(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

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
                    Data cart=new Data(getItem(position).getShopUid() + getItem(position).getProductName(), getItem(position).getProductName(),
                            getItem(position).getProductPrice(), getItem(position).getProductIcon(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

                    datatransfer.onDelete(cart);
                }
                else{
                    Data cart=new Data(getItem(position).getShopUid() + getItem(position).getProductName(), getItem(position).getProductName(),
                            getItem(position).getProductPrice(), getItem(position).getProductIcon(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

                    datatransfer.onDelete(cart);
                }
            }
        });
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
