package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PromoteService extends AppCompatActivity {
    ImageView imageViewBackArrowPromoteService,addService;
    RecyclerView sp_promote_service_RV;
    List<ServiceDetails> serviceDetailsList;
    PromoteServiceAdapter adapter;
    DatabaseReference myref;
    DatabaseReference key;
    FirebaseDatabase rootnode;
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
                final AlertDialog.Builder promote_service_dialog=new AlertDialog.Builder(PromoteService.this);
                View promoteView=getLayoutInflater().inflate(R.layout.promote_service_dialog_box,null);
                final TextView editService=(TextView)promoteView.findViewById(R.id.addServiceTextview);
                final MaterialButton cancel=(MaterialButton)promoteView.findViewById(R.id.promoteServiceCancel);
                final MaterialButton add=(MaterialButton)promoteView.findViewById(R.id.promoteServiceAdd);
                final TextInputEditText getServiceTitle=(TextInputEditText)promoteView.findViewById(R.id.getServiceTitle);
                final TextInputEditText getServiceDescription=(TextInputEditText)promoteView.findViewById(R.id.getServiceDescription);
                final TextInputEditText getServicePrice=(TextInputEditText)promoteView.findViewById(R.id.getServicePrice);
                promote_service_dialog.setView(promoteView);
                final AlertDialog alertDialog=promote_service_dialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                getServiceTitle.setText(serviceDetailsList.get(position).getTitle());
                getServiceDescription.setText(serviceDetailsList.get(position).getDescription());
                String correctPrice = serviceDetailsList.get(position).getPrice().replace("Rs. ","");
                getServicePrice.setText(correctPrice);
                editService.setText("Edit Service");
                add.setText("SAVE");
                alertDialog.show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=getServiceTitle.getText().toString();
                        String desc=getServiceDescription.getText().toString();
                        String price=getServicePrice.getText().toString();
                        Log.d("title", title);

                        myref = FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Services").child(serviceDetailsList.get(position).getKey());

                        myref.setValue(new ServiceDetails(title,price,desc,serviceDetailsList.get(position).getKey()));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(PromoteService.this,"Service edited successfully.",Toast.LENGTH_SHORT).show();
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

        myref = FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Services");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (serviceDetailsList != null && !serviceDetailsList.isEmpty()) {
                    int size = serviceDetailsList.size();
                    serviceDetailsList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()) {
                    DatabaseReference serviceref=FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Services").child(snap.getKey());
                    serviceref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            serviceDetailsList.add(new ServiceDetails(snapshot.child("title").getValue().toString(), "Rs. " + snapshot.child("price").getValue().toString(),
                                    snapshot.child("description").getValue().toString(), snapshot.getKey().toString()));
                            adapter.notifyDataSetChanged();
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

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serviceDetailsList != null)
                {
                    if(serviceDetailsList.size()>=3){
                        Toast.makeText(PromoteService.this,"You cannot add more than 3 services.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        createDialogbox();
                    }
                }
                else
                {
                    createDialogbox();
                }
            }
        });
        imageViewBackArrowPromoteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void createDialogbox(){
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

                myref = FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Services");
                key = myref.push();
                myref.child(key.getKey()).setValue(new ServiceDetails(title,price,desc,key.getKey()));
                adapter.notifyDataSetChanged();
                Toast.makeText(PromoteService.this,"Service added successfully.",Toast.LENGTH_SHORT).show();
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