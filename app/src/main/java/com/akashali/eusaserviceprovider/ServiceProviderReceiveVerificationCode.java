package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class ServiceProviderReceiveVerificationCode extends AppCompatActivity {
    ImageView verifybackbutton;
    MaterialButton verifycode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_receive_verification_code);
        verifybackbutton=findViewById(R.id.verifybackbutton);
        verifycode=findViewById(R.id.verifycode);
        verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceProviderReceiveVerificationCode.this,GetInfoStepOne.class);
                startActivity(intent);
            }
        });
        verifybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}