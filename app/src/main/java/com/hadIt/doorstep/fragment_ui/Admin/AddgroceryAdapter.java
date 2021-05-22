package com.hadIt.doorstep.fragment_ui.Admin;

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
import com.google.android.material.snackbar.Snackbar;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Data;
import com.hadIt.doorstep.fragment_ui.Interfaces.Datatransfer;
import com.hadIt.doorstep.fragment_ui.home.ViewProduct;

import java.util.ArrayList;

public class AddgroceryAdapter extends RecyclerView.Adapter<AddgroceryAdapter.ViewHolder> {

    ArrayList<ProductInfo> arrayList;
    Context context;
    public Datatransfer datatransfer;
    public ArrayList<Data> addtocartArrayList=new ArrayList<>();


    ArrayList<ProductInfo> saveArrayList =new ArrayList<>();

    public AddgroceryAdapter(ArrayList<ProductInfo> arrayList,Context context,Datatransfer datatransfer) {
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
//        holder.productname.setText(arrayList.get(position).getProductname().toString());
//        holder.productrate.setText(arrayList.get(position).getProductrate().toString());
//        //holder.productimage.setImageResource(arrayList.get(position).getProductimage());
//        Glide.with(context).load(arrayList.get(position).getProductimage()).into(holder.productimage);
//        Data cart=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//        //addtocartArrayList.add(cart);
//        ViewProduct viewProduct=new ViewProduct();
//       // datatransfer.onSetValues(cart);
//
//        holder.addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar snackbar = Snackbar
//                        .make(view, "ITEM ADDED TO CART", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                holder.addbutton.setVisibility(View.GONE);
//                holder.linear.setVisibility(View.VISIBLE);
//              // savearraylist.add(arrayList.get(position));
//               Data cart=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//               //addtocartArrayList.add(cart);
//               datatransfer.onSetValues(cart);
//            }
//        });
//
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Integer.parseInt(holder.number.getText().toString())<=1){
//                    holder.linear.setVisibility(View.GONE);
//                    holder.addbutton.setVisibility(View.VISIBLE);
//                    //savearraylist.remove(arrayList.get(position));
//                    Data cart=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//                    datatransfer.onDelete(cart);
//                   // datatransfer.onSetValues();
//
//                }
//                else{
//                    holder.number.setText(""+(Integer.parseInt(holder.number.getText().toString())-1));
//                    Data cart=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//                    datatransfer.onSetValues(cart);
//                }
//            }
//        });
//
//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Data cart=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//                datatransfer.onDelete(cart);
//                holder.number.setText(""+(Integer.parseInt(holder.number.getText().toString())+1));
//                Data catoon=new Data(arrayList.get(position).productimage,arrayList.get(position).productname,arrayList.get(position).productrate,(String.valueOf(addtocartArrayList.size())),holder.number.getText().toString());
//
//                datatransfer.onSetValues(catoon);
//            }
//        });
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
            productimage= itemView.findViewById(R.id.image);
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
