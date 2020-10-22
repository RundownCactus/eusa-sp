package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class GetInfoStepOne extends AppCompatActivity {
    MaterialButton nextbuttonstep1;
    TextInputEditText fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_one);
        fname = findViewById(R.id.getfirstname);
        lname = findViewById(R.id.getlastname);
        nextbuttonstep1=findViewById(R.id.nextbuttonstep1);
        nextbuttonstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = fname.getText().toString();
                String last = lname.getText().toString();
                String phno = getIntent().getStringExtra("phno");
                Intent intent=new Intent(GetInfoStepOne.this,GetInfoStepTwo.class);
                intent.putExtra("fname",first);
                intent.putExtra("lname",last);
                intent.putExtra("phno",phno);
                startActivity(intent);
            }
        });
    }
}