package com.akashali.eusaserviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.BreakIterator;
import java.util.concurrent.TimeUnit;

public class LoginReceiveVerificationCode extends AppCompatActivity {
    MaterialButton verifycodelogin;
    ImageView loginverifybackbutton;
    String verCode;
    String phno;
    EditText CodeByUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_receive_verification_code);
        CodeByUser=findViewById(R.id.verificationcode);
        phno = getIntent().getStringExtra("phno");

        //FUNCTION TO SEND CODE TO USER PHONE
        sendCode(phno);

        loginverifybackbutton=findViewById(R.id.loginverifybackbutton);
        verifycodelogin=findViewById(R.id.verifycodelogin);
        verifycodelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = CodeByUser.getText().toString();
                if(code.isEmpty() || code.length()<6){
                    CodeByUser.setError("Wrong OTP");
                    CodeByUser.requestFocus();
                    return;
                }
                //FUNCTION TO SEE IF CODE ENTERED BY USER IS CORRECT
                verifyCode(code);

            }
        });
        loginverifybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //FIREBASE FUNCTIONALITY
    private void sendCode(String phno) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92" + phno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    //GETTING CODE
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                //FUNCTION TO SEE IF CODE IS CORRECT
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginReceiveVerificationCode.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential cred = PhoneAuthProvider.getCredential(verCode,code);
        //SIGN IN IF CODE IS CORRECT
        signIn(cred);
    }

    private void signIn(PhoneAuthCredential cred) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(cred).addOnCompleteListener(LoginReceiveVerificationCode.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                    intent.putExtra("phno",phno);
                    SessionManager sessionManager = new SessionManager(LoginReceiveVerificationCode.this);
                    sessionManager.createSession(mAuth.getUid().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}