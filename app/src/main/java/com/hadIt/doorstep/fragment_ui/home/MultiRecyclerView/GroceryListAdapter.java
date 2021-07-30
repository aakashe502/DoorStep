package com.hadIt.doorstep.fragment_ui.home.MultiRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.fragment_ui.home.ModelClass;
import com.hadIt.doorstep.fragment_ui.home.ViewShop;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.DataViewholder> {
    private ArrayList<ModelClass> arrayList;
    private Context context;


    public GroceryListAdapter(ArrayList<ModelClass> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewholder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_itemcategories, parent, false);
        return new DataViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewholder holder,int position) {
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

    public class DataViewholder  extends RecyclerView.ViewHolder{
        public CircleImageView groceryimage;
        public TextView groceryname;
        public DataViewholder(@NonNull View itemView) {
            super(itemView);
            groceryimage=itemView.findViewById(R.id.groceryimage);
            groceryname=itemView.findViewById(R.id.groceryname);
        }
    }
}
