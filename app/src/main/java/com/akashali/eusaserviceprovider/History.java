package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {
    ImageView imageViewBackArrowHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        imageViewBackArrowHistory=findViewById(R.id.imageViewBackArrowHistory);
        imageViewBackArrowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}