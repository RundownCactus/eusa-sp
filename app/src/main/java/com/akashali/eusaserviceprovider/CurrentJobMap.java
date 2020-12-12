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
import android.widget.ImageView;
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
    DatabaseReference myref,userref;
    String spLatLngStart;
    TextView currentjobuserfullname;
    ImageView currentjobusercall;
    MaterialButton booking_complete;
    String userrating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job_map);
        currentjobuserfullname=findViewById(R.id.currentjobuserfullname);
        currentjobusercall=findViewById(R.id.currentjobusercall);
        booking_complete=findViewById(R.id.booking_complete);
        spid = getIntent().getStringExtra("spid");
        uid = getIntent().getStringExtra("uid");
        key = getIntent().getStringExtra("key");
        userLatLng=getIntent().getStringExtra("userLatLng");
        String [] loc = userLatLng.split(",",2);
        lat = Double.parseDouble(loc[0]);
        lon = Double.parseDouble(loc[1]);
        Log.d("TAGAR",spid);
        Log.d("TAGAR",uid);
        Log.d("TAGAR",key);
        Log.d("TAGAR",userLatLng);
        booking_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseReference comref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("status");
                //comref.setValue("Complete");
                final AlertDialog.Builder job_complete_alert_dialog=new AlertDialog.Builder(CurrentJobMap.this);
                View jobCompleteView=getLayoutInflater().inflate(R.layout.job_complete_dialog_box,null);
                final MaterialButton back=(MaterialButton)jobCompleteView.findViewById(R.id.booking_back);
                final MaterialButton complete=(MaterialButton)jobCompleteView.findViewById(R.id.booking_complete);

                final ImageView oneStar=(ImageView)jobCompleteView.findViewById(R.id.onestar);
                final ImageView twoStar=(ImageView)jobCompleteView.findViewById(R.id.twostar);
                final ImageView threeStar=(ImageView)jobCompleteView.findViewById(R.id.threestar);
                final ImageView fourStar=(ImageView)jobCompleteView.findViewById(R.id.fourstar);
                final ImageView fiveStar=(ImageView)jobCompleteView.findViewById(R.id.fivestar);
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
                final AlertDialog alertDialog=job_complete_alert_dialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String jobCompleteTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        DatabaseReference jobCompleteTime1=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobCompletionTime");
                        jobCompleteTime1.setValue(jobCompleteTime);
                        DatabaseReference comref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("status");
                        comref.setValue("Complete");
                        DatabaseReference ratref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobUserRating");
                        ratref.setValue(userrating);
                        Intent intent=new Intent(CurrentJobMap.this,BasicSearch.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.show();
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
                    .apiKey(getString(R.string.google_maps_api_key))
                    .build();
        }
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
                Log.d("nope", "onResult: routes: " + result.routes[0].toString());
                Log.d("nope", "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e("nope", "onFailure: " + e.getMessage() );

            }
        });
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
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
                calculateDirections(marker);
                spLatLngStart="";
                spLatLngStart+=latLng.latitude;
                spLatLngStart+=",";
                spLatLngStart+=latLng.longitude;
                myref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("spLatlngStart");
                myref.setValue(spLatLngStart);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mLocationListener);
    }



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