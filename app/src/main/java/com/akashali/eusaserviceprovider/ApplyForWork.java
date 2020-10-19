package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class ApplyForWork extends AppCompatActivity {
    ImageView imageViewApplyForWork;
    MaterialButton savebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_work);
        imageViewApplyForWork=findViewById(R.id.imageViewApplyForWork);
        savebutton=findViewById(R.id.savebutton);
        imageViewApplyForWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String[] workType = new String[] {"Plumber", "Electrician", "Carpenter", "Cleaner","Mechanic"};

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        workType);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.exposeddropdownmenuforapplywork);
        editTextFilledExposedDropdown.setText(workType[1]);
        editTextFilledExposedDropdown.setAdapter(adapter);


        String[] city = new String[] {"Islamabad", "Rawalpindi"};

        ArrayAdapter newAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        city);

        AutoCompleteTextView cityAutoCompleteTextView = findViewById(R.id.exposeddropdownmenuforcity);
        cityAutoCompleteTextView.setAdapter(newAdapter);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}