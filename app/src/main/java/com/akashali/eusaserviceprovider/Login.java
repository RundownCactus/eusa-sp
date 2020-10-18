package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button userlogin;
    TextView textViewSignup1;
    ImageView imageViewBackArrow1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userlogin=findViewById(R.id.userlogin);
        textViewSignup1=findViewById(R.id.textViewSignup1);
        imageViewBackArrow1=findViewById(R.id.imageViewBackArrow1);
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,BasicSearch.class);
                startActivity(intent);
            }
        });

        textViewSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imageViewBackArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}