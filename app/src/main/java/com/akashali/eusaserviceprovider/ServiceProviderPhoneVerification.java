package com.akashali.eusaserviceprovider;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

//SIGN UP VERIFY PHONE ACITIVITY

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
                if(!validatePhno()){
                    return;
                }
                String ph = phno.getText().toString();
                Intent intent=new Intent(ServiceProviderPhoneVerification.this,ServiceProviderReceiveVerificationCode.class);
                intent.putExtra("phno",ph);
                startActivity(intent);
            }
        });
    }

    private boolean validatePhno(){
        String ph = phno.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(ph.isEmpty()){
            phno.setError("Please Enter a Valid Phone Number");
            return false;
        }
        else if(ph.length()>11) {
            phno.setError("Please Enter a Valid Phone Number");
            return false;
        }else if(ph.length()<10){
            phno.setError("Please Enter a Valid Phone Number");
            return false;
        }else if(ph.matches(nowhitespace)){
            phno.setError("Phone Number Cannot be Null");
            return false;
        }else{
            phno.setError(null);
            return true;
        }
    };

}