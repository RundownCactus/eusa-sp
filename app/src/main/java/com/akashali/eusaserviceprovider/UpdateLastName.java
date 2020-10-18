package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateLastName extends AppCompatActivity {
    Button updateuserlastnamebutton;
    ImageView imageViewBackArrowUpdateLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_last_name);
        updateuserlastnamebutton=findViewById(R.id.updateuserlastnamebutton);
        updateuserlastnamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewBackArrowUpdateLastName=findViewById(R.id.imageViewBackArrowUpdateLastName);
        imageViewBackArrowUpdateLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}