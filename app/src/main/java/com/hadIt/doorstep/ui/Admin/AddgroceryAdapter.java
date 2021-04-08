package com.hadIt.doorstep.ui.Admin;

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
import com.hadIt.doorstep.ui.home.ModelAdapter;

import java.net.ContentHandler;
import java.util.ArrayList;

public class AddgroceryAdapter extends RecyclerView.Adapter<AddgroceryAdapter.ViewHolder> {

  ArrayList<InfoData> arrayList;
  Context context;

    public AddgroceryAdapter(ArrayList<InfoData> arrayList,Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        holder.productname.setText(arrayList.get(position).getProductname().toString());
        holder.productrate.setText(arrayList.get(position).getProductrate().toString());
        //holder.productimage.setImageResource(arrayList.get(position).getProductimage());
        Glide.with(context).load(arrayList.get(position).getProductimage()).into(holder.productimage);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productimage;
        public TextView productname;
        public TextView productrate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage= itemView.findViewById(R.id.image);
            productname= itemView.findViewById(R.id.name);
            productrate=itemView.findViewById(R.id.ruppee);

        }
    }
}
