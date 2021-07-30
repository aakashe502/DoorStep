package com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.TopProductsModel;
import com.hadIt.doorstep.fragment_ui.home.ShopProductAdapter;
import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;
import com.hadIt.doorstep.roomDatabase.cart.DataTransfer;
import com.hadIt.doorstep.roomDatabase.cart.model.Data;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.List;

public class TopProductsAdapter extends RecyclerView.Adapter<ShopProductAdapter.ViewHolder> {
    Context context;
    List<TopProductsModel> productsModels;

    public TopProductsAdapter(Context context,List<TopProductsModel> productsModels) {
        this.context = context;
        this.productsModels = productsModels;
    }

    @NonNull
    @Override
    public ShopProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_picks, parent, false);
        return new ShopProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopProductAdapter.ViewHolder holder,final int position) {
        holder.productName.setText(productsModels.get(position).productName);
        holder.productRate.setText(productsModels.get(position).productPrice);
        holder.productId.setText(String.format("#%s", productsModels.get(position).productId));
        holder.description.setText(productsModels.get(position).productDescription);
        Glide.with(context).load(productsModels.get(position).productIcon).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productImage;
        public TextView productName, productRate, number, productId, description, numb, unit;
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
        }
    }
}
