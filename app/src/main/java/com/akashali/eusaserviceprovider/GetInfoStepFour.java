package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetInfoStepFour extends AppCompatActivity {
    MaterialButton prevbuttonstep4,donebuttonstep4;
    AutoCompleteTextView type;
    FirebaseDatabase rootnode;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_four);
        type = findViewById(R.id.applyworkdropdown);
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
                String Type = type.getText().toString();
                String fname = getIntent().getStringExtra("fname");
                String lname = getIntent().getStringExtra("lname");
                String email = getIntent().getStringExtra("email");
                String cnic = getIntent().getStringExtra("cnic");
                String city = getIntent().getStringExtra("city");
                String addr = getIntent().getStringExtra("addr");
                String phno = getIntent().getStringExtra("phno");
                Log.d("second", email);
                Log.d("second", cnic);
                Log.d("second", fname);
                Log.d("second", lname);
                Log.d("second", city);
                Log.d("second", addr);
                Log.d("second", Type);
                Log.d("second", phno);

                Intent intent=new Intent(GetInfoStepFour.this,BasicSearch.class);

                Contact newSP = new Contact(cnic,fname,lname,phno,email,addr,city,Type);
                //Contact newSP = new Contact("131031234","Salman","last","q03335234","Emailcd","E114","Islamanad","Electrician");

                rootnode = FirebaseDatabase.getInstance();
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(phno);
                myref.setValue(newSP);
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