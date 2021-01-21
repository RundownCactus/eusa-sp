package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    ImageView imageViewBackArrowHistory;
    RecyclerView sp_history_RV;
    List<JobHistory> jobHistoryList;
    JobHistoryAdapter adapter;
    DatabaseReference myref;
    List<String> jobIds;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        jobHistoryList=new ArrayList<>();
        myref= FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(mAuth.getInstance().getCurrentUser().getUid()).child("Jobs");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    DatabaseReference jobref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(snap.getKey());
                    jobref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            jobHistoryList.add(new JobHistory(snapshot.child("jobBookTime").getValue().toString(), "PKR " + snapshot.child("totalPrice").getValue().toString(),
                                    "Job ID: "+snapshot.getKey(), snapshot.child("status").getValue().toString()));
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




        sp_history_RV=findViewById(R.id.sp_history_RV);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        sp_history_RV.setLayoutManager(lm);
        adapter=new JobHistoryAdapter(jobHistoryList,this);
        sp_history_RV.setAdapter(adapter);
        adapter.setOnItemClickListener(new JobHistoryAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
            }
        });



        imageViewBackArrowHistory=findViewById(R.id.imageViewBackArrowHistory);
        imageViewBackArrowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}