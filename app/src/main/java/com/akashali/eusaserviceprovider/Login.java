package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    /*Button userlogin;
    TextView textViewSignup1;
    ImageView imageViewBackArrow1;*/

    MaterialButton sendverificationcodelogin;
    ImageView verifybackbuttonlogin;
    TextInputEditText phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phno = findViewById(R.id.verifyphonenumberlogin);

        verifybackbuttonlogin=findViewById(R.id.verifybackbuttonlogin);
        sendverificationcodelogin=findViewById(R.id.sendverificationcodelogin);
        sendverificationcodelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validatePhno()){
                    return;
                }
                String ph = phno.getText().toString();
                Intent intent=new Intent(Login.this,LoginReceiveVerificationCode.class);
                intent.putExtra("phno",ph);
                startActivity(intent);
            }
        });
        verifybackbuttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*userlogin=findViewById(R.id.userlogin);
        textViewSignup1=findViewById(R.id.textViewSignup1);
        imageViewBackArrow1=findViewById(R.id.imageViewBackArrow1);
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,BasicSearch.class);
                startActivity(intent);
            }
        });

        textViewSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imageViewBackArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
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