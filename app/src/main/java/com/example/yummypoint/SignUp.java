package com.example.yummypoint;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yummypoint.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

        MaterialEditText editphone , editName, editPassword , editEmail;
        Button btnSignUp;


    //init Firebase
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    final DatabaseReference table_user= database.getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName= findViewById(R.id.edt_name);

        editPassword= findViewById(R.id.edt_password);

        editphone= findViewById(R.id.edt_phone);
        editEmail = findViewById(R.id.edt_email);

        btnSignUp = findViewById(R.id.button_SignUp);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidName(editName.getText().toString()) && editPassword.getText().length() >= editPassword.getMinCharacters() && editphone.getText().length() >= editphone.getMinCharacters() && isEmailValid(editEmail.getText().toString())) {
                    loadData();
                }else{
                    if(editphone.getText().length() < editphone.getMinCharacters()) {
                        Toast.makeText(SignUp.this, "Phone No must 11 digits long", Toast.LENGTH_LONG).show();
                    }else if(editPassword.getText().length() < editPassword.getMinCharacters()){
                        Toast.makeText(SignUp.this, "Password Must Be 8 characters long", Toast.LENGTH_LONG).show();
                    }else if(!isEmailValid(editEmail.getText().toString())){
                        Toast.makeText(SignUp.this, "Please enter correct email address", Toast.LENGTH_LONG).show();
                    }else if(!isValidName(editName.getText().toString())){
                        Toast.makeText(SignUp.this, "Please enter a valid name", Toast.LENGTH_LONG).show();
                    }else{
                        loadData();

                    }
                }
            }
        });
    }

    public boolean isValidName(String txt) {

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public void loadData(){
        final ProgressDialog mdialog = new ProgressDialog(SignUp.this);
        mdialog.setMessage("Please waiting...");
        mdialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // check if already user phone exist
                if (dataSnapshot.child(editphone.getText().toString()).exists()) {
                    Toast.makeText(SignUp.this, "Already registered on this phone no", Toast.LENGTH_SHORT).show();
                    mdialog.dismiss();

                } else {

                    mdialog.dismiss();
                    User user = new User(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());
                    table_user.child(editphone.getText().toString()).setValue(user);
                    Toast.makeText(SignUp.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isEmailValid(String email)
        {
            String regExpn =
                    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                            +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                            +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                            +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);

            return matcher.matches();
        }
}
