package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BasicSearch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Integer REQUEST_CODE=1;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView mainmenu;
    TextView text;
    ImageView profileImage,picker;
    List<Contact> contacts;
    FirebaseDatabase rootnode;
    DatabaseReference myref,jobref;
    private FirebaseAuth mAuth;
    private String Addr;
    private String Loc;
    TextView name;

    //Job Alertbox start
    MaterialCardView current_job_card;
    //Job Alertbox end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_search);
        Log.d("basicsearchCalled", "onCreate Called");
        contacts=new ArrayList<>();
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        mainmenu=findViewById(R.id.mainmenu);



        jobref = FirebaseDatabase.getInstance().getReference().child("Jobs");
        jobref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               CheckJobs(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckJobs(DataSnapshot snapshot) {

        Log.d("TAG",snapshot.getValue().toString());
        Log.d("TAG", ""+snapshot.getChildrenCount());
        for (DataSnapshot jobs : snapshot.getChildren()) {
            if(jobs.child("spid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){
                if(jobs.child("status").getValue().toString().equals("New"))
                {
                    DatabaseReference uref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(jobs.child("uid").getValue().toString());
                    uref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SendRequest(snapshot);
                            //Log.d("TAG",snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        }
    }

    private void SendRequest(DataSnapshot snapshot) {
        final AlertDialog.Builder job_alert_dialog=new AlertDialog.Builder(BasicSearch.this);
        View jobView=getLayoutInflater().inflate(R.layout.job_receive_dialog_box,null);
        final MaterialButton reject=(MaterialButton)jobView.findViewById(R.id.booking_reject);
        final MaterialButton accept=(MaterialButton)jobView.findViewById(R.id.booking_accept);

        job_alert_dialog.setView(jobView);
        final AlertDialog alertDialog=job_alert_dialog.create();
        alertDialog.setCanceledOnTouchOutside(false);
        name = jobView.findViewById(R.id.servicebooker);
        name.setText(snapshot.child("fname").getValue().toString() + snapshot.child("lname").getValue().toString() );

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,CurrentJobMap.class);
                startActivity(intent);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        current_job_card=findViewById(R.id.current_job_card);
        current_job_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder job_alert_dialog=new AlertDialog.Builder(BasicSearch.this);
                View jobView=getLayoutInflater().inflate(R.layout.job_receive_dialog_box,null);
                final MaterialButton reject=(MaterialButton)jobView.findViewById(R.id.booking_reject);
                final MaterialButton accept=(MaterialButton)jobView.findViewById(R.id.booking_accept);

                job_alert_dialog.setView(jobView);
                final AlertDialog alertDialog=job_alert_dialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(BasicSearch.this,CurrentJobMap.class);
                        startActivity(intent);
                    }
                });
                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });




        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.username);
        profileImage=(ImageView) header.findViewById(R.id.circleImageView);
        rootnode = FirebaseDatabase.getInstance();
        myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());

        Log.d("BC", mAuth.getInstance().getCurrentUser().getUid());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text.setText(snapshot.child("fname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,EditProfile.class);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,EditProfile.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_history:
                Intent intent=new Intent(BasicSearch.this,History.class);
                startActivity(intent);
                break;
            case R.id.nav_payment:
                Intent intent1=new Intent(BasicSearch.this,Payment.class);
                startActivity(intent1);
                break;
            case R.id.nav_settings:
                Intent intent2=new Intent(BasicSearch.this,Settings.class);
                startActivity(intent2);
                break;
            case R.id.nav_notifications:
                Intent intent3=new Intent(BasicSearch.this,Notifications.class);
                startActivity(intent3);
                break;
            case R.id.nav_contactus:
                Intent intent4=new Intent(BasicSearch.this,ContactUs.class);
                startActivity(intent4);
                break;
            case R.id.nav_applyforwork:
                Intent intent5=new Intent(BasicSearch.this,ApplyForWork.class);
                startActivity(intent5);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}