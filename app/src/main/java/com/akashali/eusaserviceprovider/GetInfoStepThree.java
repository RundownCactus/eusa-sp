package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

//SIGN UP STEP 3

public class GetInfoStepThree extends AppCompatActivity {
    MaterialButton prevbuttonstep3,nextbuttonstep3;
    ImageView addr;
    AutoCompleteTextView city;
    String Addr;
    String Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_step_three);
        city = findViewById(R.id.getcitydropdown);
        addr = findViewById(R.id.getaddress);
        prevbuttonstep3=findViewById(R.id.prevbuttonstep3);
        nextbuttonstep3=findViewById(R.id.nextbuttonstep3);

        //INITLIATING MAP API TO GET LOCATION FROM MAP
        Places.initialize(getApplicationContext(),"AIzaSyAYE1QHGlPnjvJUxCqqJMQjfkPzN2mCVSQ");

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GetInfoStepThree.this,PlacePicker.class);
                startActivityForResult(intent, 2);
            }
        });
        prevbuttonstep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextbuttonstep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TRANSFERRING ALL DATA TO NEXT STEP

                String fname = getIntent().getStringExtra("fname");
                String lname = getIntent().getStringExtra("lname");
                String email = getIntent().getStringExtra("email");
                String cnic = getIntent().getStringExtra("cnic");
                String phno = getIntent().getStringExtra("phno");
                String City = city.getText().toString();

                Intent intent = new Intent(getApplicationContext(),GetInfoStepFour.class);
                intent.putExtra("email",email);
                intent.putExtra("cnic",cnic);
                intent.putExtra("fname",fname);
                intent.putExtra("lname",lname);
                intent.putExtra("city",City);
                intent.putExtra("addr",Addr);
                intent.putExtra("phno",phno);
                intent.putExtra("phno",phno);
                intent.putExtra("loc",Loc);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    //ACTIVITY RESULT FOR PLACE PICKER ACITIVTY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2 && data != null)
        {

                Addr = data.getStringExtra("addr");
                Loc = data.getStringExtra("loc");
                Log.d("BC", Addr);
        }
    }
}