package com.example.yummypoint;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_guest , btnSignUp;
    TextView textslogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_guest= (Button) findViewById(R.id.button_Guest);
        btnSignUp = (Button) findViewById(R.id.button_SignUp);

        textslogan= (TextView) findViewById(R.id.textSlogan);

        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/Newstyle.ttf");
        textslogan.setTypeface(typeface);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this,SignIn.class);
                startActivity(signUp);
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guest = new Intent(MainActivity.this,Home.class);
                guest.putExtra("UserName", "Guest");
                startActivity(guest);
            }
        });
    }
    public void onBackPressed()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Exist", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.show();


    }

}
