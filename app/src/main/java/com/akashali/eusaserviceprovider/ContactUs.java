package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUs extends AppCompatActivity {
    ImageView imageViewBackArrowContactus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        imageViewBackArrowContactus=findViewById(R.id.imageViewBackArrowContactus);
        imageViewBackArrowContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}