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

                Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                rootnode = FirebaseDatabase.getInstance();



                Contact newSP = new Contact(loc,cnic,fname,lname,phno,email,addr,city,Type,rat,prat,mAuth.getInstance().getCurrentUser().getUid());
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());
                myref.setValue(newSP);

              /*  Contact newSP = new Contact("4231906883047","Asil","Abidi","+923331214567","ServiceProvider@hotmail.com","Beverly Centre, f6","Islamabad","Electrician",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("3427607556341","Salib","Raza","+923335649870","ServiceProvider@hotmail.com","f8/1 Markaz","Islamabad","Electrician",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("6754887990645","Omer","Tayyar","+923098756445","ServiceProvider@hotmail.com","Shop No 3 F11/1 Markaz Olympus Mall","Islamabad","Electrician",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("5643209878906","Khilat","Naqvi","+923125647778","ServiceProvider@hotmail.com","G-9/2 Markaz","Islamabad","Electrician",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);

                newSP = new Contact("7676756889789","Hamza","Mukhtar","+923333245456","ServiceProvider@hotmail.com","Shop no 4 E11/4","Islamabad","Plumber",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("4234156448796","Jilani","Ghulam","+923339807656","ServiceProvider@hotmail.com","Kohsar Market Shop no 65 Basement F-6","Islamabad","Plumber",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("3298054483096","Salman","Hayat","+923337658111","ServiceProvider@hotmail.com","House No 37 Street 8 Sector F11/1","Islamabad","Plumber",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);

                newSP = new Contact("4289705778096","Noman","Shaikh","+923471210807","ServiceProvider@hotmail.com","WellMart E11/4 ","Islamabad","Carpenter",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("6234165774987","Jalil","Memon","+923126447774","ServiceProvider@hotmail.com","Vostro World, f11 ","Islamabad","Carpenter",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("6451090886375","Ahsan","Buriro","+923547786665","ServiceProvider@hotmail.com","Shop No 56 Commercial market G-7","Islamabad","Carpenter",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("2398765008975","Salik","Soomro","+923124557776","ServiceProvider@hotmail.com","Shop No 12 E-7 Markaz","Islamabad","Carpenter",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);

                newSP = new Contact("3256467889076","Bilawal","Chandio","+923473124445","ServiceProvider@hotmail.com","Shop no 54 G-7 Markaz","Islamabad","Cleaner",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("5467788776654","Asif","Rana","+923335654789","ServiceProvider@hotmail.com","Shop No 21 F-15 Markaz","Islamabad","Cleaner",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("9854432556476","Virat","Kohli","+923335463423","ServiceProvider@hotmail.com","Shop No 43 Near Moon Market D-12","Islamabad","Cleaner",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);

                newSP = new Contact("5643234221654","Khalid","Channa","+923124998708","ServiceProvider@hotmail.com","Shop No 32 F11/1 Markaz","Islamabad","Car Mechanic",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("9806584332548","Mosab","Ayaz","+923097765554","ServiceProvider@hotmail.com","Shop No 2  Sulaiman Market F11/1","Islamabad","Car Mechanic",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);
                newSP = new Contact("2132378665488","Ali","Akbar","+923330908976","ServiceProvider@hotmail.com","Shaheen Chemist, F11","Islamabad","Car Mechanic",(float)2.5,(float)2.5);
                myref = rootnode.getReference().child("Users").child("ServiceProviders").child(newSP.getPhone());
                myref.setValue(newSP);*/
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
