package com.hadIt.doorstep.chooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.HomePage;
import com.hadIt.doorstep.LoginActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.admin.AdminLoginActivity;

import io.paperdb.Paper;

public class WhoYouAreActivity extends AppCompatActivity {

    public CardView customer, admin, deliveryBoy;
    public FirebaseFirestore firebaseFirestore;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(WhoYouAreActivity.this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}