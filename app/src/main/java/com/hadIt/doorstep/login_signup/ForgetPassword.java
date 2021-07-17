package com.hadIt.doorstep.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hadIt.doorstep.R;

public class ForgetPassword extends AppCompatActivity {
    Button reset;
    EditText mail;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mail=findViewById(R.id.mail);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait.");
        progressDialog.setCanceledOnTouchOutside(false);
        reset=findViewById(R.id.reset);
        firebaseAuth= FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetfun();

            }

            private void resetfun() {
                String email= mail.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(ForgetPassword.this,"Invalid Email..",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Sending Instructions to reset Password.");
                progressDialog.show();

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(ForgetPassword.this,LoginActivity.class));
                                    Toast.makeText(ForgetPassword.this,"Check your gmail",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgetPassword.this,"Failed sending mail",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}