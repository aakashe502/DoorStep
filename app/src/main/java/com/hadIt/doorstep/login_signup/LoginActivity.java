package com.hadIt.doorstep.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.progressBar.CustomProgressBar;

public class LoginActivity extends AppCompatActivity {
    public Button createNewAccount, loginButton;
    public EditText email, password;
    private CustomProgressBar customProgressBar;
    private PaperDb paperDb;
    TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paperDb = new PaperDb();

        customProgressBar = new CustomProgressBar(LoginActivity.this);

        email=findViewById(R.id.etemail);
        password=findViewById(R.id.mypass);
        createNewAccount=findViewById(R.id.createnewac);
        loginButton=findViewById(R.id.btnlogin);

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        forget=findViewById(R.id.forgotPassword);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
            }
        });
    }

    private void goToNewActivity() {
        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            loginUser();
        }
        else{
            Toast.makeText(LoginActivity.this,"Email and password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        customProgressBar.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //start the profile activity
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            firebaseFirestore.collection("users").whereEqualTo("emailId", email.getText().toString().trim())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                if(task.getResult().getDocuments().size()>0){
                                                    paperDb.saveUserInPaperDb();
                                                    startActivity(new Intent(getApplicationContext(), HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                    customProgressBar.dismiss();
                                                }
                                                else{
                                                    Toast.makeText( LoginActivity.this, "This email does not exist", Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    customProgressBar.dismiss();
                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText( LoginActivity.this, "This email does not exist", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            customProgressBar.dismiss();
                                        }
                                    });

                        }
                        else{
                            customProgressBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
