package com.hadIt.doorstep.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hadIt.doorstep.HomePage;
import com.hadIt.doorstep.LoginActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.SaveDetailsToFirestore;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;

public class AdminLoginActivity extends AppCompatActivity {

    public Button createNewAccount, loginButton;
    public EditText email, password;
    public PasswordGeneratorMd5 md5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        email=findViewById(R.id.adminEmail);
        password=findViewById(R.id.adminPass);
//        resetPassword = findViewById(R.id.resetPassword);
        createNewAccount=findViewById(R.id.adminNewAccount);
        loginButton=findViewById(R.id.adminLogin);

        md5 = new PasswordGeneratorMd5();

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminLoginActivity.this, AdminSignupActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotonewActivity();
            }
        });
    }

    private void gotonewActivity() {
        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            //logging in the user
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), md5.btnMd5(password))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //start the profile activity
                        startActivity(new Intent(getApplicationContext(), HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                    else{
                        Toast.makeText(AdminLoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
        }
        else{
            Toast.makeText(AdminLoginActivity.this,"Email and password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
    }
}