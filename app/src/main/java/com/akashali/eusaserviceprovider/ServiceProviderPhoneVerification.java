package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class ServiceProviderPhoneVerification extends AppCompatActivity {
    MaterialButton sendverificationcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_phone_verification);
        sendverificationcode=findViewById(R.id.sendverificationcode);
        sendverificationcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceProviderPhoneVerification.this,ServiceProviderReceiveVerificationCode.class);
                startActivity(intent);
            }
        });
    }
}