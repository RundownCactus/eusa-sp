package com.akashali.eusaserviceprovider;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ServiceProviderPhoneVerification extends AppCompatActivity {
    MaterialButton sendverificationcode;
    TextInputEditText phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_phone_verification);
        sendverificationcode=findViewById(R.id.sendverificationcode);
        phno = findViewById(R.id.verifyphonenumber);

        sendverificationcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = phno.getText().toString();
                Log.d("WAH","+92"+ph);
                Intent intent=new Intent(ServiceProviderPhoneVerification.this,ServiceProviderReceiveVerificationCode.class);
                intent.putExtra("phno",ph);
                startActivity(intent);
            }
        });
    }
}