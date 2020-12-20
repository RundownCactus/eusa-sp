package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                //if(sessionManager.checkLogin()){
                  //  Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                  //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  //  startActivity(intent);
               // }
               // else{
                    Intent intent=new Intent(SplashScreen.this,LetsGetStarted.class);
                    startActivity(intent);
                    finish();
                //}

            }
        },1000);
    }
}