package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class GetInfoStepOne extends AppCompatActivity {
    MaterialButton nextbuttonstep1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_one);
        nextbuttonstep1=findViewById(R.id.nextbuttonstep1);
        nextbuttonstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetInfoStepOne.this,GetInfoStepTwo.class);
                startActivity(intent);
            }
        });
    }
}