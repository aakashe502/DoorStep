package com.hadIt.doorstep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    public Button createNewAccount, loginButton;
    public TextView resetPassword;
    public EditText email, password;
    public FirebaseFirestore db;
    public PasswordGeneratorMd5 md5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(getApplicationContext());

        md5 = new PasswordGeneratorMd5();
        email=findViewById(R.id.etemail);
        password=findViewById(R.id.mypass);
        resetPassword = findViewById(R.id.resetPassword);
        createNewAccount=findViewById(R.id.createnewac);
        loginButton=findViewById(R.id.btnlogin);

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SaveDetailsToFirestore.class);
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
        if(email.getText().toString()!=null&&password.getText().toString()!=null){
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
                                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}
