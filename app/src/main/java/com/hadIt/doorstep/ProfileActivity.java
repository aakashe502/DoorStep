package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private String Tag = "ProfileActivity";

    public TextView title;
    public TextView email;
    public TextView mobile;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        title = findViewById(R.id.title);
        email = findViewById(R.id.emailid);
        mobile = findViewById(R.id.mobileNo);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String currentUser = auth.getCurrentUser().getEmail();
        db.collection("users").document(currentUser).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        title.setText(documentSnapshot.getData().get("userName").toString());
                        email.setText(documentSnapshot.getData().get("emailId").toString());
                        mobile.setText(documentSnapshot.getData().get("mobile").toString());
                    }
                });
    }
}