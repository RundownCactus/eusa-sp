package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class GetInfoStepTwo extends AppCompatActivity {
    MaterialButton prevbuttonstep2,nextbuttonstep2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_two);
        prevbuttonstep2=findViewById(R.id.prevbuttonstep2);
        nextbuttonstep2=findViewById(R.id.nextbuttonstep2);
        prevbuttonstep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextbuttonstep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetInfoStepTwo.this,GetInfoStepThree.class);
                startActivity(intent);
            }
        });
    }
}