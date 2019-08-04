package com.example.yummypoint;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummypoint.Model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ItemInfo extends AppCompatActivity {

    private TextView mName, mDescription, mDiscount, mPrice;
    private ImageView mImage;
    private Button mCart,mBuy;
    private String name , description , discount , price , image , menuID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        mName = findViewById(R.id.info_item_name);
        mDescription = findViewById(R.id.info_item_description);
        mDiscount = findViewById(R.id.info_item_discount);
        mPrice=findViewById(R.id.info_item_price);
        mImage = findViewById(R.id.info_image);
        mCart = findViewById(R.id.add_to_cart);
        mBuy = findViewById(R.id.buy_now);

        if(getIntent()!=null)
        {
            name=getIntent().getStringExtra("Name");
            description=getIntent().getStringExtra("Description");
            discount=getIntent().getStringExtra("Discount");
            price=getIntent().getStringExtra("Price");
            image=getIntent().getStringExtra("Image");
            menuID=getIntent().getStringExtra("MenuID");
            Toast.makeText(this, ""+image, Toast.LENGTH_SHORT).show();

        }

        mName.setText(name);
        mDescription.setText(description);
        mDiscount.setText("Discount: "+discount);
        mPrice.setText("Rs: "+price);
        Picasso.with(getBaseContext()).load(image).into(mImage);

        mCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemInfo.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference addInDB = db.getReference().child("Cart");

                addInDB.push().setValue(new Item(name, image, description, price, discount, menuID));


            }
        });

        mBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ItemInfo.this, "Buy Now ", Toast.LENGTH_SHORT).show();
                }
            });
    }


}
