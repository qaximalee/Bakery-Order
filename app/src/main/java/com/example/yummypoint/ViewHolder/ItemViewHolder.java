package com.example.yummypoint.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummypoint.Interface.ItemClickListener;
import com.example.yummypoint.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView itemImage;
    public TextView itemName, itemDescription, itemPrice;


    private ItemClickListener itemClickListener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name);
        itemDescription = itemView.findViewById(R.id.item_description);
        itemPrice = itemView.findViewById(R.id.item_price);

        itemImage = itemView.findViewById(R.id.item_image);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
