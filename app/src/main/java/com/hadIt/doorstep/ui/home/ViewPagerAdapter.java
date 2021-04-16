package com.hadIt.doorstep.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hadIt.doorstep.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.viewholder> {
    // Context object
    Context context;

    // Array of images
    int[] images;

    public ViewPagerAdapter(Context context,int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        //datatransfer.onSetValues(savearraylist);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder,int position) {

        Glide.with(context).load(images[position]).into(holder.image);



    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class  viewholder extends RecyclerView.ViewHolder {
        public ImageView image;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageViewMain);
        }
    }


    // Layout Inflater

}
