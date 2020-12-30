package com.akashali.eusaserviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

//SIGN UP STEP 1

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
                if(!validateLname() | !validateFname()){
                    return;
                }

                //GET INFO AND TRANSFER TO NEXT STEP

                String first = fname.getText().toString();
                String last = lname.getText().toString();
                String phno = getIntent().getStringExtra("phno");
                Intent intent = new Intent(getApplicationContext(),GetInfoStepTwo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("fname",first);
                intent.putExtra("lname",last);
                intent.putExtra("phno",phno);
                startActivity(intent);
            }
        });
    }

    //VALIDATION FUNCTIONS
    private boolean validateFname(){
        String fn = fname.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(fn.isEmpty()){
            fname.setError("Please Enter a Valid Name");
            return false;
        }
        else if(fn.matches(nowhitespace)){
            fname.setError("Name Cannot Have WhiteSpace");
            return false;
        }else{
            fname.setError(null);
            return true;
        }
    };
    private boolean validateLname(){
        String fn = lname.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(fn.isEmpty()){
            lname.setError("Please Enter a Valid Name");
            return false;
        }
        else if(fn.matches(nowhitespace)){
            lname.setError("Name Cannot Have WhiteSpace");
            return false;
        }else{
            lname.setError(null);
            return true;
        }
    };

}