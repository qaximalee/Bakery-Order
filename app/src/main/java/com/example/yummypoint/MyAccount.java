package com.example.yummypoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yummypoint.Model.User;

public class MyAccount extends AppCompatActivity {

    TextView name, email, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        name = findViewById(R.id.acc_name);
        email = findViewById(R.id.acc_email);


        User user = new User();

        String name1 = user.getName();
        String email1 = user.getEmail();

        name.setText(user.getName());
        name.setText(user.getEmail());

    }
}
