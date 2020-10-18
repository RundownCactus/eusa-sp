package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Notifications extends AppCompatActivity {
    ImageView imageViewBackArrowNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        imageViewBackArrowNotifications=findViewById(R.id.imageViewBackArrowNotifications);
        imageViewBackArrowNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}