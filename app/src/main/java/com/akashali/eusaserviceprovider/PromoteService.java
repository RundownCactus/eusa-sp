package com.akashali.eusaserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PromoteService extends AppCompatActivity {
    ImageView imageViewBackArrowPromoteService,addService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_service);
        imageViewBackArrowPromoteService=findViewById(R.id.imageViewBackArrowPromoteService);
        addService=findViewById(R.id.addService);

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    protected void onResume() {
        super.onResume();
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder promote_service_dialog=new AlertDialog.Builder(PromoteService.this);
                View promoteView=getLayoutInflater().inflate(R.layout.job_receive_dialog_box,null);
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