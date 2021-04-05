package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class PromoteService extends AppCompatActivity {
    ImageView imageViewBackArrowPromoteService,addService;
    RecyclerView sp_promote_service_RV;
    List<ServiceDetails> serviceDetailsList;
    PromoteServiceAdapter adapter;
    DatabaseReference myref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_service);
        serviceDetailsList=new ArrayList<>();
        imageViewBackArrowPromoteService=findViewById(R.id.imageViewBackArrowPromoteService);
        addService=findViewById(R.id.addService);

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    protected void onResume() {
        super.onResume();
        sp_promote_service_RV=findViewById(R.id.sp_promote_service_RV);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        sp_promote_service_RV.setLayoutManager(lm);
        adapter=new PromoteServiceAdapter(serviceDetailsList,this);
        sp_promote_service_RV.setAdapter(adapter);
        adapter.setOnItemClickListener(new PromoteServiceAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {

            }
        });


        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder promote_service_dialog=new AlertDialog.Builder(PromoteService.this);
                View promoteView=getLayoutInflater().inflate(R.layout.promote_service_dialog_box,null);
                final MaterialButton cancel=(MaterialButton)promoteView.findViewById(R.id.promoteServiceCancel);
                final MaterialButton add=(MaterialButton)promoteView.findViewById(R.id.promoteServiceAdd);
                final TextInputEditText getServiceTitle=(TextInputEditText)promoteView.findViewById(R.id.getServiceTitle);
                final TextInputEditText getServiceDescription=(TextInputEditText)promoteView.findViewById(R.id.getServiceDescription);
                final TextInputEditText getServicePrice=(TextInputEditText)promoteView.findViewById(R.id.getServicePrice);
                promote_service_dialog.setView(promoteView);
                final AlertDialog alertDialog=promote_service_dialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=getServiceTitle.getText().toString();
                        String desc=getServiceDescription.getText().toString();
                        String price=getServicePrice.getText().toString();
                        Log.d("title", title);
                        serviceDetailsList.add(new ServiceDetails(title,"PKR "+price,desc));
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        imageViewBackArrowPromoteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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