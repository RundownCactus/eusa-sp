package com.akashali.eusaserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    DatabaseReference myref,jobref,recentjobref;
    private FirebaseAuth mAuth;
    private String Addr;
    private String Loc;
    TextView name;
    String key;


    //Job Alertbox start
    MaterialCardView current_job_card;
    MaterialCardView card4,card1;


    //Job Alertbox end
    //homepage
    TextView cardUsername,completedJobsRate,avgRating,cancelledJobRate;
    MaterialCardView settings,history,promote;
    DatabaseReference completedJobRate;
    List<JobHistory> jobHistoryList;
    MaterialCardView cardrecentjob,cardongoingjob;
    TextView recentendusername,recentjobtime;
    SwitchMaterial availableforWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_search);
        availableforWork=findViewById(R.id.availableswitch);
        availableforWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if (isChecked)
                {
                    DatabaseReference isAvl=FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("isAvailable");
                    isAvl.setValue("Yes");
                }
                else
                {
                    DatabaseReference isAvl=FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("isAvailable");
                    isAvl.setValue("No");
                }
            }
        });
        recentendusername=findViewById(R.id.recentendusername);
        recentjobtime=findViewById(R.id.recentjobtime);
        completedJobsRate=findViewById(R.id.completedJobsRate);
        cancelledJobRate=findViewById(R.id.cancelledJobRate);
        avgRating=findViewById(R.id.avgRating);
        jobHistoryList=new ArrayList<>();
        completedJobRate= FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Jobs");
        completedJobRate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int[] completedJobs={0};
                final int[] cancelledJobs={0};
                final int[] totalRatings={0};
                final int[] ratings={0};
                for (DataSnapshot snap : snapshot.getChildren()) {
                    DatabaseReference jobref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(snap.getKey());
                    jobref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("status").getValue().toString().equals("Complete"))
                            {
                                if(!(snapshot.child("jobSPRating").getValue().toString().equals("")))
                                {
                                    totalRatings[0]++;
                                    String temp=snapshot.child("jobSPRating").getValue().toString();
                                    int myNum = 0;

                                    try {
                                        myNum = Integer.parseInt(temp);
                                    } catch(NumberFormatException nfe) {

                                    }
                                    ratings[0]+=myNum;

                                }
                                completedJobs[0]++;
                                jobHistoryList.add(new JobHistory(snapshot.child("jobBookTime").getValue().toString(), "PKR " + snapshot.child("totalPrice").getValue().toString(),
                                        "Job ID: "+snapshot.getKey(), snapshot.child("status").getValue().toString()));
                            }
                            else if(snapshot.child("status").getValue().toString().equals("Cancel by SP"))
                            {
                                cancelledJobs[0]++;
                                jobHistoryList.add(new JobHistory(snapshot.child("jobBookTime").getValue().toString(), "PKR " + snapshot.child("totalPrice").getValue().toString(),
                                        "Job ID: "+snapshot.getKey(), snapshot.child("status").getValue().toString()));
                            }
                            Log.d("Total List", Integer.toString(completedJobs[0]));
                            float com=((float)completedJobs[0]/(completedJobs[0]+cancelledJobs[0]))*100;
                            float can=((float)cancelledJobs[0]/(completedJobs[0]+cancelledJobs[0]))*100;
                            String canDecimal = String.format("%.2f", can);
                            cancelledJobRate.setText(canDecimal+"%");
                            String comDecimal = String.format("%.2f", com);
                            completedJobsRate.setText(comDecimal+"%");
                            if(totalRatings[0]!=0) {
                                float rat = ((float) ratings[0] / (totalRatings[0]));
                                String ratDecimal = String.format("%.2f", rat);
                                avgRating.setText(ratDecimal);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Jobs");
        Query lastQuery = databaseReference.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] arrayString = dataSnapshot.getValue().toString().split("=");
                String str1=arrayString[0];
                String str2=str1.substring(1);

                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Jobs").child(str2);
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!(snapshot.child("jobCompletionTime").getValue().toString().equals("")) && snapshot.child("status").getValue().toString().equals("Complete"))
                        {
                            String[] str1=snapshot.child("jobCompletionTime").getValue().toString().split(" ");
                            String str2=str1[1];
                            String str3=str2.substring(0,5);
                            recentjobtime.setText(str3);
                        }
                        else if(!(snapshot.child("jobRejectTime").getValue().toString().equals("")) && snapshot.child("status").getValue().toString().equals("Job Rejected by SP"))
                        {
                            String[] str1=snapshot.child("jobRejectTime").getValue().toString().split(" ");
                            String str2=str1[1];
                            String str3=str2.substring(0,5);
                            recentjobtime.setText(str3);
                        }
                        else if(!(snapshot.child("jobCancelTime").getValue().toString().equals("")))
                        {
                            String[] str1=snapshot.child("jobCancelTime").getValue().toString().split(" ");
                            String str2=str1[1];
                            String str3=str2.substring(0,5);
                            recentjobtime.setText(str3);
                        }

                        DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(snapshot.child("uid").getValue().toString());
                        databaseRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("Total List 2", snapshot.getValue().toString() );
                                recentendusername.setText(snapshot.child("fname").getValue().toString()+" "+snapshot.child("lname").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });




        /*card1=findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,History.class);
                startActivity(intent);
            }
        });
        card4=findViewById(R.id.card4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,Settings.class);
                startActivity(intent);
            }
        });*/
        Log.d("basicsearchCalled", "onCreate Called");
        contacts=new ArrayList<>();
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        mainmenu=findViewById(R.id.mainmenu);
        settings=findViewById(R.id.settings);
        promote=findViewById(R.id.promote);
        history=findViewById(R.id.history);



        jobref = FirebaseDatabase.getInstance().getReference().child("Jobs");
        jobref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Checks To See If a Job Request is made
               CheckJobs(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckJobs(DataSnapshot snapshot) {

        //Traverses Through Firebase Database to find a Job in Current Users Name
        for (DataSnapshot jobs : snapshot.getChildren()) {
            if(jobs.child("spid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){

                //Make The Alert

                final AlertDialog.Builder job_alert_dialog=new AlertDialog.Builder(BasicSearch.this);
                View jobView=getLayoutInflater().inflate(R.layout.job_receive_dialog_box,null);
                final MaterialButton reject=(MaterialButton)jobView.findViewById(R.id.booking_reject);
                final MaterialButton accept=(MaterialButton)jobView.findViewById(R.id.booking_accept);
                job_alert_dialog.setView(jobView);
                final AlertDialog alertDialog=job_alert_dialog.create();

                //Extra Functions with Job Status
                if(jobs.child("status").getValue().toString().equals("New"))
                {
                    DatabaseReference uref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(jobs.child("uid").getValue().toString());
                    uref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //SendRequest(snapshot);
                            //Log.d("TAG",jobs.getKey());
                            alertDialog.setCanceledOnTouchOutside(false);
                            name = jobView.findViewById(R.id.servicebooker);
                            name.setText(snapshot.child("fname").getValue().toString() + " "+ snapshot.child("lname").getValue().toString() );
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(jobs.getKey()).child("status");
                            DatabaseReference jobAcceptTime=FirebaseDatabase.getInstance().getReference().child("Jobs").child(jobs.getKey()).child("jobAcceptTime");
                            DatabaseReference jobRejectTime=FirebaseDatabase.getInstance().getReference().child("Jobs").child(jobs.getKey()).child("jobRejectTime");

                            //JOB ACCEPTED ACTIONS

                            accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String jobAcceptTime1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                    ref.setValue("Accept");
                                    jobAcceptTime.setValue(jobAcceptTime1);
                                    Intent intent=new Intent(BasicSearch.this,CurrentJobMap.class);
                                    intent.putExtra("spid",jobs.child("spid").getValue().toString());
                                    intent.putExtra("uid",jobs.child("uid").getValue().toString());
                                    intent.putExtra("key",jobs.getKey());
                                    key=jobs.getKey();
                                    intent.putExtra("userLatLng",jobs.child("userLatLng").getValue().toString());
                                    myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());
                                    myref.child("Jobs").child(key).setValue("true");
                                    alertDialog.dismiss();
                                    startActivity(intent);
                                }
                            });

                            //JOB REJECTED ACTIONS

                            reject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //String jobARejectTime1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                    //jobRejectTime.setValue(jobARejectTime1);
                                    key=jobs.getKey();
                                    ref.setValue("Job Rejected by SP");
                                    myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());
                                    myref.child("Jobs").child(key).setValue("rejected");
                                    alertDialog.dismiss();
                                }
                            });

                            //CHECK IF USER CANCELLED

                            if(jobs.child("status").getValue().toString().equals("New"))
                            {
                                alertDialog.show();
                                DatabaseReference jobcancelref = FirebaseDatabase.getInstance().getReference().child("Jobs").child(jobs.getKey());
                                jobcancelref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child("status").getValue().toString().equals("Cancel by user"))
                                        {
                                            alertDialog.dismiss();
                                            Toast.makeText(BasicSearch.this,"Job cancelled by user",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();


        //NAV BAR STARTED IN ON RESUME

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Updating The Nav Bar According to User

        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.username);
        TextView categoy= (TextView) header.findViewById(R.id.category);
        cardUsername=findViewById(R.id.cardUsername);
        profileImage=(ImageView) header.findViewById(R.id.circleImageView);
        rootnode = FirebaseDatabase.getInstance();
        myref = rootnode.getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid());

        Log.d("BC", mAuth.getInstance().getCurrentUser().getUid());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text.setText(snapshot.child("fname").getValue().toString()+ " "+ snapshot.child("lname").getValue().toString());
                cardUsername.setText(snapshot.child("fname").getValue().toString());
                categoy.setText(snapshot.child("type").getValue().toString());
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
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,Settings.class);
                startActivity(intent);
            }
        });
        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,PromoteService.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,History.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //NAV BAR SELECTION
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
            case R.id.nav_help:
                Intent intent4=new Intent(BasicSearch.this,ContactUs.class);
                startActivity(intent4);
                break;
            case R.id.nav_applyforwork:
                Intent intent5=new Intent(BasicSearch.this,ApplyForWork.class);
                startActivity(intent5);
                break;
            case R.id.nav_SignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent6=new Intent(BasicSearch.this,LetsGetStarted.class);
                startActivity(intent6);
                //Intent intent6=new Intent(BasicSearch.this,ApplyForWork.class);
                //startActivity(intent6);
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