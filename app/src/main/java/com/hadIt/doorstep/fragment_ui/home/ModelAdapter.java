package com.hadIt.doorstep.fragment_ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ItemViewHolder> {
    private ArrayList<ModelClass> arrayList;
    private Context context;

    public ModelAdapter(ArrayList<ModelClass> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_itemcategories, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder,int position) {

        holder.groceryname.setText(arrayList.get(position).getName());
        Glide.with(context).load(arrayList.get(position).getImages()).into(holder.groceryimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewShop.class);
                intent.putExtra("shopType", holder.groceryname.getText().toString());
               context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView groceryimage;
        public TextView groceryname;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryimage=itemView.findViewById(R.id.groceryimage);
            groceryname=itemView.findViewById(R.id.groceryname);
        }
    }
}
