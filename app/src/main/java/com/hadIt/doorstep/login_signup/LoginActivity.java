package com.hadIt.doorstep.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    public Button createNewAccount, loginButton;
//    public TextView resetPassword;
    public EditText email, password;
    public FirebaseFirestore firebaseFirestore;
    public PasswordGeneratorMd5 md5;
    public String Tag = "LoginActivity";
    public String msg_subscribed = "Subscribed";
    public String msg_subscribe_failed = "Subscription Failed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(getApplicationContext());

        md5 = new PasswordGeneratorMd5();
        email=findViewById(R.id.etemail);
        password=findViewById(R.id.mypass);
//        resetPassword = findViewById(R.id.resetPassword);
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
                gotonewActivity();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("basicTopic")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(Tag, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(LoginActivity.this,"Email and password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}
