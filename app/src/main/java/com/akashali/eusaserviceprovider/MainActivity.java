package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    Button usersignup;
    TextView textViewLogin;
    ImageView imageViewBackArrow;

    TextInputEditText userfirstnameedit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersignup=findViewById(R.id.usersignup);
        textViewLogin=findViewById(R.id.textViewLogin);
        imageViewBackArrow=findViewById(R.id.imageViewBackArrow);

        userfirstnameedit=findViewById(R.id.userfirstnameedit);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });
        usersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,BasicSearch.class);
                startActivity(intent);
                //String text = userfirstnameedit.getText().toString();
                //Log.d("TAG", "onClick: "+text);
            }
        });
        imageViewBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}