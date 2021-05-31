package com.akashali.eusaserviceprovider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentJobMap extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnInfoWindowClickListener {

    //GOOGLE MAPS VARIABLES START
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private LatLng latLng,bookerLatLng;
    private GeoApiContext mGeoApiContext=null;
    //GOOGLE MAPS VARIABLES END
    String spid,uid,key,userLatLng,userphno;
    Double lat,lon;
    DatabaseReference myref,userref,jobcancelref,jobref,services,services1;
    String spLatLngStart;
    TextView currentjobuserfullname;
    RelativeLayout currentjobusercall,currentjobuserchat;
    MaterialButton booking_complete,booking_cancel1;
    String userrating;
    String myChatKey;
    String username;
    AlertDialog alertDialog;

    LinearLayout service1,service2,service3;
    TextView s1_title,s2_title,s3_title;
    TextView s1_price,s2_price,s3_price;
    TextView s1_description,s2_description,s3_description;
    List<ServiceDetails> myList;

    RelativeLayout loadingBackground;
    ProgressBar maps_progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job_map);
        loadingBackground=findViewById(R.id.loadingBackground);
        loadingBackground.setVisibility(View.VISIBLE);
        maps_progressbar=findViewById(R.id.maps_progressbar);
        maps_progressbar.setVisibility(View.VISIBLE);
        myList=new ArrayList<>();
        service1=findViewById(R.id.service1);
        service2=findViewById(R.id.service2);
        service3=findViewById(R.id.service3);
        s1_title=findViewById(R.id.s1_title);
        s2_title=findViewById(R.id.s2_title);
        s3_title=findViewById(R.id.s3_title);
        s1_price=findViewById(R.id.s1_price);
        s2_price=findViewById(R.id.s2_price);
        s3_price=findViewById(R.id.s3_price);
        s1_description=findViewById(R.id.s1_description);
        s2_description=findViewById(R.id.s2_description);
        s3_description=findViewById(R.id.s3_description);

        //Init MAP/POP UP variables

        currentjobuserfullname=findViewById(R.id.currentjobuserfullname);
        currentjobusercall=findViewById(R.id.currentjobusercall);
        currentjobuserchat=findViewById(R.id.currentjobuserchat);
        booking_complete=findViewById(R.id.booking_complete);
        booking_cancel1=findViewById(R.id.booking_cancel1);
        spid = getIntent().getStringExtra("spid");
        uid = getIntent().getStringExtra("uid");
        key = getIntent().getStringExtra("key");
        myChatKey=getIntent().getStringExtra("chat");
        userLatLng=getIntent().getStringExtra("userLatLng");
        String [] loc = userLatLng.split(",",2);
        lat = Double.parseDouble(loc[0]);
        lon = Double.parseDouble(loc[1]);
        Log.d("TAGAR",spid);
        Log.d("TAGAR",uid);
        Log.d("TAGAR",key);
        Log.d("TAGAR",userLatLng);


    }

    @Override
    protected void onResume() {
        super.onResume();
        currentjobuserchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CurrentJobMap.this,Chat.class);
                intent.putExtra("myChat",myChatKey);
                intent.putExtra("username",currentjobuserfullname.getText().toString());
                startActivity(intent);
            }
        });

        services=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key);
        services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services1=FirebaseDatabase.getInstance().getReference().child("Services").child(snapshot.child("orderedService").getValue().toString());
                services1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myList.clear();
                        for (DataSnapshot snap : snapshot.getChildren())
                        {
                            Log.d("ABCDE",snap.getValue().toString());
                            if(myList.size()==3) {
                                myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString()));
                                service3.setVisibility(View.VISIBLE);
                                s3_title.setText(snap.child("title").getValue().toString());
                                s3_description.setText(snap.child("description").getValue().toString());
                                s3_price.setText("Rs. "+snap.child("price").getValue().toString());
                            }
                            if(myList.size()==2) {
                                myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString()));
                                service2.setVisibility(View.VISIBLE);
                                s2_title.setText(snap.child("title").getValue().toString());
                                s2_description.setText(snap.child("description").getValue().toString());
                                s2_price.setText("Rs. "+snap.child("price").getValue().toString());
                            }
                            if(myList.size()==1) {
                                myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString()));
                                service1.setVisibility(View.VISIBLE);
                                s1_title.setText(snap.child("title").getValue().toString());
                                s1_description.setText(snap.child("description").getValue().toString());
                                s1_price.setText("Rs. "+snap.child("price").getValue().toString());
                            }
                            if(myList.size()==0) {
                                service1.setVisibility(View.GONE);
                                service2.setVisibility(View.GONE);
                                service3.setVisibility(View.GONE);
                                myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString()));
                            }
                        }
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
        //BOOKING CANCELLED BY SERVICE PROVIDER
        booking_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobCancelTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                DatabaseReference jobCancelTime1=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobCancelTime");
                jobCancelTime1.setValue(jobCancelTime);
                jobcancelref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("status");
                jobcancelref.setValue("Cancel by SP");
                Intent intent=new Intent(CurrentJobMap.this,BasicSearch.class);
                startActivity(intent);
                finish();
            }
        });
        jobref= FirebaseDatabase.getInstance().getReference().child("Jobs").child(key);
        jobref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("status").getValue().toString().equals("Cancel by user after accept") && !(snapshot.child("jobCancelTime").getValue().toString().equals("")))
                {
                    Intent intent=new Intent(CurrentJobMap.this,BasicSearch.class);
                    startActivity(intent);
                    finish();
                }
                //if the job is completed by service provider.
                if(!(snapshot.child("jobSPRating").getValue().toString().equals("")) && !(snapshot.child("jobCompletionTime").getValue().toString().equals("")) && (snapshot.child("status").getValue().toString().equals("Complete")))
                {
                    //Log.d("TAGA",snapshot.getKey());
                    final AlertDialog.Builder job_complete_alert_dialog=new AlertDialog.Builder(CurrentJobMap.this);
                    View jobCompleteView=getLayoutInflater().inflate(R.layout.job_complete_dialog_box,null);
                    final MaterialButton complete=(MaterialButton)jobCompleteView.findViewById(R.id.booking_complete);

                    final ImageView oneStar=(ImageView)jobCompleteView.findViewById(R.id.onestar);
                    final ImageView twoStar=(ImageView)jobCompleteView.findViewById(R.id.twostar);
                    final ImageView threeStar=(ImageView)jobCompleteView.findViewById(R.id.threestar);
                    final ImageView fourStar=(ImageView)jobCompleteView.findViewById(R.id.fourstar);
                    final ImageView fiveStar=(ImageView)jobCompleteView.findViewById(R.id.fivestar);
                    final EditText userFeedback=(EditText)jobCompleteView.findViewById(R.id.spfeedback);
                    final TextView completejobusername=(TextView) jobCompleteView.findViewById(R.id.completejobusername);
                    final TextView myrating=(TextView) jobCompleteView.findViewById(R.id.myrating);
                    final TextView jobprice=(TextView) jobCompleteView.findViewById(R.id.jobprice);

                    final LinearLayout paymentStatus=(LinearLayout) jobCompleteView.findViewById(R.id.paymentStatus);
                    final TextView paymentText=(TextView) jobCompleteView.findViewById(R.id.paymentText);
                    jobprice.setText("Rs. "+snapshot.child("totalPrice").getValue().toString()+".00");

                    if (snapshot.child("paymentStatus").getValue().toString().equals("Cash Payment Confirmed."))
                    {
                        paymentText.setText("You can collect Rs. "+snapshot.child("totalPrice").getValue().toString()+".00"+" from customer.");
                    }
                    if (snapshot.child("paymentStatus").getValue().toString().equals("JazzCash Payment Confirmed."))
                    {
                        paymentText.setText("Payment Rs. "+snapshot.child("totalPrice").getValue().toString()+".00"+" paid through JazzCash.");
                    }
                    userref=FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(uid);
                    userref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            completejobusername.setText(snapshot.child("fname").getValue().toString() + " "+ snapshot.child("lname").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    userrating="0";
                    oneStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            userrating="1";
                        }
                    });
                    twoStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            userrating="2";
                        }
                    });
                    threeStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            userrating="3";
                        }
                    });
                    fourStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            userrating="4";
                        }
                    });
                    fiveStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            userrating="5";
                        }
                    });

                    job_complete_alert_dialog.setView(jobCompleteView);
                    alertDialog=job_complete_alert_dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseReference ratref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobUserRating");
                            ratref.setValue(userrating);
                            String feedback=userFeedback.getText().toString();
                            DatabaseReference userFeedback1=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("userFeedback");
                            userFeedback1.setValue(feedback);
                            alertDialog.dismiss();
                            Intent intent=new Intent(CurrentJobMap.this,BasicSearch.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userref=FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(uid);
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentjobuserfullname.setText(snapshot.child("fname").getValue().toString() + " "+ snapshot.child("lname").getValue().toString());
                userphno=snapshot.child("phone").getValue().toString();
                currentjobusercall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //CONTACT USER THROUGH DIALER

                        Uri u = Uri.parse("tel:" + userphno);
                        Intent i = new Intent(Intent.ACTION_DIAL, u);
                        try {
                            startActivity(i);
                        } catch (SecurityException s) {
                            Toast.makeText(CurrentJobMap.this, "An error occurred", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void calculateDirections(Marker marker){
        Log.d("nope", "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        latLng.latitude,
                        latLng.longitude
                )
        );
        Log.d("nope", "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d("nope2", "onResult: routes: " + result.routes[0].toString());
                Log.d("nope2", "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("nope2", "onFailure: " + e.getMessage() );

            }
        });
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    //ADDS NAVIGATION LINES TO MAP ACCORDING TO GOOGLE DIRECTIONS

    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("nope1", "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d("nope1", "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){
//                        Log.d(TAG, "run: latlng: " + latLng.toString());
                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(CurrentJobMap.this, R.color.BlueColor));
                    polyline.setClickable(true);

                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //INIT MAP VARIABLES

        mMap = googleMap;
        getPermission();

        LatLng userLocation = new LatLng(lat, lon);
        Marker marker=mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
        mMap.setOnInfoWindowClickListener(this);

        // LatLng Pakistan = null;
        // Pakistan=getLocationFromAddress(this,"House 609, Main Double Road, E11/4, Islamabad");
        // googleMap.addMarker(new MarkerOptions()
        //       .position(Pakistan)
        //     .title("Marker in Pakistan")
        //   .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_carpentermapicon)));
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                spLatLngStart="";
                spLatLngStart+=latLng.latitude;
                spLatLngStart+=",";
                spLatLngStart+=latLng.longitude;
                myref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("spLatlngStart");
                myref.setValue(spLatLngStart);
                calculateDirections(marker);
                loadingBackground.setVisibility(View.GONE);
                maps_progressbar.setVisibility(View.GONE);

                // Add a marker in Sydney and move the camera
                //CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                //LatLng myLocation = new LatLng(33.699989, 73.001916);
                //mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng());
                //mMap.animateCamera(zoom);
            }



            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        //CHECKING PERMISSIONS FOR INTERNET AND GPS

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //GETTING LIVE LOCATION

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mLocationListener);
    }


    //CONVERT VECTOR TO BITMAP

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //GETTING PERMISSIONS

    private  void getPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            mMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},600);
        }

    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==600)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

            }
        }
    }


}