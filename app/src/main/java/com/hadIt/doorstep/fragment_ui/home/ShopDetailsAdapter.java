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
import com.hadIt.doorstep.cache.model.Admin;
import com.hadIt.doorstep.cache.model.Data;

import com.hadIt.doorstep.fragment_ui.Interfaces.Datatransfer;

import java.util.ArrayList;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ViewHolder> {

    ArrayList<Admin> arrayList;
    Context context;
    public Datatransfer datatransfer;
    public ArrayList<Data> addtocartArrayList=new ArrayList<>();


    //ArrayList<ProductInfo> saveArrayList =new ArrayList<>();

    public ShopDetailsAdapter(ArrayList<Admin> arrayList,Context context,Datatransfer datatransfer) {
        this.arrayList = arrayList;
        this.context = context;
        this.datatransfer = datatransfer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist, parent, false);


       //datatransfer.onSetValues(savearraylist);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.productname.setText(arrayList.get(position).shopName);
       // holder.productrate.setText(arrayList.get(position).);

        Glide.with(context).load(arrayList.get(position).profileImage).into(holder.productimage);
        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewShopProducts.class);
                intent.putExtra("grocery",arrayList.get(position).uid);
                context.startActivity(intent);

            }
        });
//
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname;
        public TextView productrate,number;
        public Button addbutton;
        public LinearLayout linear;
        public Button plus,minus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.image23);
            productname= itemView.findViewById(R.id.name);
            productrate=itemView.findViewById(R.id.ruppee);
            addbutton=itemView.findViewById(R.id.addbutton);
            linear=itemView.findViewById(R.id.linear);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            number=itemView.findViewById(R.id.number);
        }
    }
}
