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

import java.util.ArrayList;

public class AdminAddapter extends RecyclerView.Adapter<AdminAddapter.ViewHolder> {
    ArrayList<ProductInfo> arrayList;
    Context context;

    ArrayList<ProductInfo> savearraylist=new ArrayList<>();
    public AdminAddapter(ArrayList<ProductInfo> arrayList,Context context) {

        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {

//        holder.productname.setText(arrayList.get(position).getProductname().toString());
//        holder.productrate.setText(arrayList.get(position).getProductrate().toString());
//        //holder.productimage.setImageResource(arrayList.get(position).getProductimage());
//        Glide.with(context).load(arrayList.get(position).getProductimage()).into(holder.productimage);
//        holder.addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar snackbar = Snackbar
//                        .make(view, "ITEM ADDED TO CART", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                holder.addbutton.setVisibility(View.GONE);
//                holder.linear.setVisibility(View.VISIBLE);
//                savearraylist.add(arrayList.get(position));
//                //datatransfer.onSetValues(savearraylist);
//
//            }
//        });
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.number.setText(""+(Integer.parseInt(holder.number.getText().toString())-1));
//                if(Integer.parseInt(holder.number.getText().toString())<=0){
//                    holder.linear.setVisibility(View.GONE);
//                    holder.addbutton.setVisibility(View.VISIBLE);
//                    savearraylist.remove(arrayList.get(position));
//                }
//            }
//        });
//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.number.setText(""+(Integer.parseInt(holder.number.getText().toString())+1));
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
