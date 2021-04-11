package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;

public class ProfileActivity extends AppCompatActivity {
    private String Tag = "ProfileActivity";

    public TextView title;
    public TextView email;
    public TextView mobile;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private PaperDb paperDb;
    private Users userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        paperDb = new PaperDb();

        title = findViewById(R.id.title);
        email = findViewById(R.id.emailid);
        mobile = findViewById(R.id.mobileNo);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userData = paperDb.getFromPaperDb();
        title.setText(userData.userName);
        email.setText(userData.emailId);
        mobile.setText(userData.mobile);
    }
}