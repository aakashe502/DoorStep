package com.hadIt.doorstep.chooser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.login_signup.LoginActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.admin.AdminLoginActivity;
import com.hadIt.doorstep.admin.home.AdminHomePage;

import io.paperdb.Paper;

public class WhoYouAreActivity extends AppCompatActivity {

    public CardView customer, admin, deliveryBoy;
    public FirebaseFirestore firebaseFirestore;
    public FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_you_are);

        Paper.init(getApplicationContext());

        customer = findViewById(R.id.customer);
        admin = findViewById(R.id.admin);
        deliveryBoy = findViewById(R.id.deliveryBoy);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WhoYouAreActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WhoYouAreActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();

        if(user != null){
            DocumentReference customers = firebaseFirestore.collection("users").document(user.getEmail());
            customers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.getResult().exists()){
                        startActivity(new Intent(WhoYouAreActivity.this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                }
            });

            DocumentReference admin = firebaseFirestore.collection("admin").document(user.getEmail());
            admin.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.getResult().exists()){
                        startActivity(new Intent(WhoYouAreActivity.this, AdminHomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                }
            });
        }
    }
}