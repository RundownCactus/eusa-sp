package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;

public class GetInfoStepThree extends AppCompatActivity {
    MaterialButton prevbuttonstep3,nextbuttonstep3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_three);
        prevbuttonstep3=findViewById(R.id.prevbuttonstep3);
        nextbuttonstep3=findViewById(R.id.nextbuttonstep3);
        prevbuttonstep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextbuttonstep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetInfoStepThree.this,GetInfoStepFour.class);
                startActivity(intent);
            }
        });
        String[] city = new String[] {"Islamabad", "Rawalpindi"};

        ArrayAdapter newAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        city);

        AutoCompleteTextView cityAutoCompleteTextView = findViewById(R.id.getcitydropdown);
        cityAutoCompleteTextView.setAdapter(newAdapter);
    }
}