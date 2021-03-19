package com.hadIt.doorstep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity
{
    EditText t2;
    Button b2;
    String phonenumber;
    String otpid;
    FirebaseAuth mAuth;
    public FirebaseFirestore firestore;
    private final String TAG = "CAPTCHA ";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        phonenumber=getIntent().getStringExtra("mobile").toString();
        t2=findViewById(R.id.t2);
        b2=findViewById(R.id.b2);

        mAuth=FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        initiateotp();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(t2.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(t2.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                otpid=s;
                            }
                            @Override
                            public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential)
                            {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NotNull FirebaseException e) {
                                Log.i("Error", e.getMessage());
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        })
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            checkIfUserExistsInFireStore();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Signin Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void checkIfUserExistsInFireStore() {
        String getSignedInUser = Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber();
        Log.i(TAG, getSignedInUser);
        checkIfUserInDB(getSignedInUser);
    }

    private void checkIfUserInDB(String getSignedInUser) {
        DocumentReference usersRef = firestore.collection("users").document(getSignedInUser);
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    startActivity(new Intent(ManageOtp.this, HomePage.class));
                }
                else{
                    startActivity(new Intent(ManageOtp.this, SaveDetailsToFirestore.class));
                }
            }
        });
    }
}