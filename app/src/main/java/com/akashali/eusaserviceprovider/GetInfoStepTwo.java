package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class GetInfoStepTwo extends AppCompatActivity {
    MaterialButton prevbuttonstep2,nextbuttonstep2;
    TextInputEditText email,cnic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_two);
        email = findViewById(R.id.getemailaddress);
        cnic = findViewById(R.id.getcnic);

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
                String fname = getIntent().getStringExtra("fname");
                String lname = getIntent().getStringExtra("lname");
                String phno = getIntent().getStringExtra("phno");
                String Email = email.getText().toString();
                String Cnic = cnic.getText().toString();
                Intent intent=new Intent(GetInfoStepTwo.this,GetInfoStepThree.class);
                intent.putExtra("email",Email);
                intent.putExtra("cnic",Cnic);
                intent.putExtra("fname",fname);
                intent.putExtra("lname",lname);
                intent.putExtra("phno",phno);
                startActivity(intent);
            }
        });
    }
}