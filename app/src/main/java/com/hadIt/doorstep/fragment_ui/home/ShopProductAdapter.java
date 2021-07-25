package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hadIt.doorstep.R;

import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;
import com.hadIt.doorstep.roomDatabase.cart.DataViewModal;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShopProductAdapter extends ListAdapter<ProductsTable, ShopProductAdapter.ViewHolder> {
    Context context;
    public DataTransfer datatransfer;
    private DataViewModal dataViewModal;
    private static final String TAG = "ShopProductAdapter";

    protected ShopProductAdapter(@NonNull DiffUtil.ItemCallback<ProductsTable> diffCallback, Context context, DataTransfer dataTransfer) {
        super(diffCallback);
        this.context = context;
        this.datatransfer = dataTransfer;
        dataViewModal=new ViewModelProvider((ViewModelStoreOwner) context).get(DataViewModal.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopproducts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.productName.setText(getItem(position).getProductName());
        holder.productRate.setText(getItem(position).getProductPrice());
        holder.productId.setText(String.format("#%s", getItem(position).getProductId()));
        holder.description.setText(getItem(position).getProductDescription());
        Glide.with(context).load(getItem(position).getProductIcon()).into(holder.productImage);
        holder.unit.setText(String.format("/%s", getItem(position).getUnit()));

        dataViewModal.getProductObserverWithId(getItem(position).getProductId()).observe((LifecycleOwner) context, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                int quantity = Integer.parseInt(getItem(position).getProductQuantity());
                if(quantity>0)
                    holder.inStock.setText(R.string.InStock);
                else
                    holder.inStock.setText(R.string.OutOfStock);

                if(data!=null) {
                    holder.numb.setText(data.getProductQuantity());
                    holder.addButton.setVisibility(View.GONE);
                    holder.linear.setVisibility(View.VISIBLE);
                }

                final int finalQuantity = quantity;
                holder.addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalQuantity>0) {
                            holder.addButton.setVisibility(View.GONE);
                            holder.linear.setVisibility(View.VISIBLE);
                            holder.numb.setText("1");
                            Data cart = new Data(getItem(position).getProductId(), getItem(position).getProductCategory(), getItem(position).getProductDescription(),
                                    getItem(position).getProductIcon(), getItem(position).getProductName(), getItem(position).getProductPrice(), holder.numb.getText().toString(), getItem(position).getShopUid(), getItem(position).getUnit());

                            datatransfer.onSetValues(cart);
                            holder.inStock.setText(R.string.InStock);
                        }
                        else{
                            holder.inStock.setText(R.string.OutOfStock);
                            Toast.makeText(context, "This product is not available.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalQuantity-Integer.parseInt(holder.numb.getText().toString())>0) {
                            holder.numb.setText(String.format("%s", Integer.parseInt(holder.numb.getText().toString()) + 1));
                            Data cart = new Data(getItem(position).getProductId(), getItem(position).getProductCategory(), getItem(position).getProductDescription(),
                                    getItem(position).getProductIcon(), getItem(position).getProductName(), getItem(position).getProductPrice(), holder.numb.getText().toString(), getItem(position).getShopUid(), getItem(position).getUnit());

                            datatransfer.onSetValues(cart);
                            holder.inStock.setText(R.string.InStock);
                        }
                        else{
                            holder.inStock.setText(R.string.OutOfStock);
                            Toast.makeText(context, "Not enough available to add.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                holder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.numb.setText(String.format("%s", Integer.parseInt(holder.numb.getText().toString())-1));
                        if(Integer.parseInt(holder.numb.getText().toString())<1){
                            holder.numb.setText("0");
                            holder.addButton.setVisibility(View.VISIBLE);
                            holder.linear.setVisibility(View.GONE);
                            Data cart=new Data(getItem(position).getProductId(), getItem(position).getProductCategory(), getItem(position).getProductDescription(),
                                    getItem(position).getProductIcon(), getItem(position).getProductName(), getItem(position).getProductPrice(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

                            datatransfer.onDelete(cart);
                            holder.inStock.setText(R.string.InStock);
                        }
                        else{
                            Data cart=new Data(getItem(position).getProductId(), getItem(position).getProductCategory(), getItem(position).getProductDescription(),
                                    getItem(position).getProductIcon(), getItem(position).getProductName(), getItem(position).getProductPrice(), holder.numb.getText().toString(), getItem(position).getShopUid(),getItem(position).getUnit());

                            datatransfer.onDelete(cart);
                            holder.inStock.setText(R.string.InStock);
                        }
                    }
                });
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productImage;
        public TextView productName, productRate, number, productId, description, numb, unit, inStock;
        public Button addButton;
        public LinearLayout linear;
        public Button minus, plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.itemImage1);
            productName = itemView.findViewById(R.id.itemName);
            productRate =itemView.findViewById(R.id.itemCost);
            addButton =itemView.findViewById(R.id.addbutton);
            linear=itemView.findViewById(R.id.linear);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);
            numb=itemView.findViewById(R.id.number);
            unit=itemView.findViewById(R.id.value);
            productId=itemView.findViewById(R.id.productId);
            description=itemView.findViewById(R.id.description);
            inStock=itemView.findViewById(R.id.inStock);
        }
    }
}
