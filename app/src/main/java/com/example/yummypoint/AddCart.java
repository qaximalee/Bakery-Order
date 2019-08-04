package com.example.yummypoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yummypoint.Interface.ItemClickListener;
import com.example.yummypoint.Model.Item;
import com.example.yummypoint.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AddCart extends AppCompatActivity {
    RecyclerView rView_cart;
    RecyclerView.LayoutManager layoutManager_cart;

    FirebaseDatabase database_cart;
    DatabaseReference items_cart;
    String categoryId;
    FirebaseRecyclerAdapter<Item, ItemViewHolder> cart_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);

        database_cart = FirebaseDatabase.getInstance();
        items_cart = database_cart.getReference("Cart");

        rView_cart = findViewById(R.id.rView_cart);
        rView_cart.setHasFixedSize(true);
        layoutManager_cart = new LinearLayoutManager(this);
        rView_cart.setLayoutManager(layoutManager_cart);
        loadCartList();




    }

    private void loadCartList() {
        cart_adapter= new FirebaseRecyclerAdapter<Item, ItemViewHolder>(Item.class, R.layout.item_list, ItemViewHolder.class,items_cart) {
            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, Item model, int position) {
                viewHolder.itemName.setText(model.getName());
                viewHolder.itemDescription.setText(model.getDescription());
                viewHolder.itemPrice.setText("Rs: "+model.getPrice());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.itemImage);

                final Item itemDAO = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(AddCart.this, ItemInfo.class);
                        intent.putExtra("Name", itemDAO.getName());
                        intent.putExtra("Description", itemDAO.getDescription());
                        intent.putExtra("Discount", itemDAO.getDiscount());
                        intent.putExtra("Price", itemDAO.getPrice());
                        intent.putExtra("MenuID", itemDAO.getMenuId());
                        intent.putExtra("Image", itemDAO.getImage());


                        startActivity(intent);
                    }
                });
            }
        };

        rView_cart.setAdapter(cart_adapter);
    }
}
