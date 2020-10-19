package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;

public class GetInfoStepFour extends AppCompatActivity {
    MaterialButton prevbuttonstep4,donebuttonstep4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_four);
        prevbuttonstep4=findViewById(R.id.prevbuttonstep4);
        donebuttonstep4=findViewById(R.id.donebuttonstep4);
        prevbuttonstep4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        donebuttonstep4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetInfoStepFour.this,BasicSearch.class);
                startActivity(intent);
            }
        });

        String[] workType = new String[] {"Plumber", "Electrician", "Carpenter", "Cleaner","Mechanic"};

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        workType);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.applyworkdropdown);
        editTextFilledExposedDropdown.setText(workType[1]);
        editTextFilledExposedDropdown.setAdapter(adapter);

    }
}