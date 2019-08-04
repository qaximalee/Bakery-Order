package com.example.yummypoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummypoint.Common.Common;
import com.example.yummypoint.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
        EditText editphone , editpassword;
        TextView register_button;
        Button  btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editphone = findViewById(R.id.edt_phone);
        editpassword= findViewById(R.id.edt_password);
        register_button= findViewById(R.id.button_SignUp);
        btnSignIn= findViewById(R.id.button_SignIn);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(SignIn.this, SignUp.class);
                startActivity(intent_register);
            }
        });
        //init Firebase
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(SignIn.this);
                mdialog.setMessage("Please waiting...");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get user information
                        //check if user is not exist in database
                        if(editpassword.getText().toString().equals("") && editphone.getText().toString().equals("")) {
                            mdialog.dismiss();
                            Toast.makeText(SignIn.this, "Please Enter Phone No & Password", Toast.LENGTH_SHORT).show();
                        }else{
                            if (dataSnapshot.child(editphone.getText().toString()).exists()) {
                                mdialog.dismiss();
                                User user = dataSnapshot.child(editphone.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(editpassword.getText().toString())) {

                                    Common.currentUser = user;
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    intent.putExtra("UserName", "User");
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mdialog.dismiss();

                                Toast.makeText(SignIn.this, "User not exist ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
