package com.example.yummypoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.yummypoint.Interface.ItemClickListener;
import com.example.yummypoint.Model.Item;
import com.example.yummypoint.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ItemList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference items;

    String categoryId;

    FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        items = database.getReference("Items");

        recyclerView = findViewById(R.id.recycler_item);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!categoryId.isEmpty() && categoryId != null){
            loadListItem(categoryId);
        }

    }

    private void loadListItem(String categoryId){
        adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(Item.class, R.layout.item_list, ItemViewHolder.class, items.orderByChild("MenuId").equalTo(categoryId)) {
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
                        Intent intent = new Intent(ItemList.this, ItemInfo.class);
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

        recyclerView.setAdapter(adapter);
    }
}
