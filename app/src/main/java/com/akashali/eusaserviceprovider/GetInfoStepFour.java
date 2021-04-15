package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

//LOGIN ACTIVITY STEP 4

public class GetInfoStepFour extends AppCompatActivity {
    MaterialButton prevbuttonstep4,donebuttonstep4;
    AutoCompleteTextView type;
    FirebaseDatabase rootnode;
    DatabaseReference myref;
    private FirebaseAuth mAuth;
    Random rand = new Random();
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

                //GETTING EXTRAS

                String Type = type.getText().toString();
                String fname = getIntent().getStringExtra("fname");
                String lname = getIntent().getStringExtra("lname");
                String email = getIntent().getStringExtra("email");
                String cnic = getIntent().getStringExtra("cnic");
                String city = getIntent().getStringExtra("city");
                String addr = getIntent().getStringExtra("addr");
                String phno = getIntent().getStringExtra("phno");
                String loc = getIntent().getStringExtra("loc");
                String rat = "2.5";
                String prat = "2.5";
                Log.d("second", email);
                Log.d("second", cnic);
                Log.d("second", fname);
                Log.d("second", lname);
                Log.d("second", city);
                Log.d("second", addr);
                Log.d("second", Type);
                Log.d("second", phno);
                Log.d("second", loc);

                //INIT ACTIVITY
                Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                rootnode = FirebaseDatabase.getInstance();

                //CREATING THE NEW SERVICE PROVIDER

                Contact newSP = new Contact(loc,cnic,fname,lname,phno,email,addr,city,Type,rat,prat,mAuth.getInstance().getCurrentUser().getUid(),"No");
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());
                myref.setValue(newSP);
                //START
                startActivity(intent);
            }
        });

        //DESIGNATED WORK TYPES FOR NOW
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
