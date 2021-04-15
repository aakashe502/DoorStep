package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private String Tag = "ProfileActivity";

    public TextView title;
    public TextView email;
    public TextView mobile;
    public CircleImageView profilePhoto, camera;
    public static final int PICK_IMAGE = 1;
    Uri selectedImageURI;
    public Toolbar toolbar;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private PaperDb paperDb;
    private Users userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        paperDb = new PaperDb();

        title = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        mobile = findViewById(R.id.mobileEditText);
        profilePhoto = findViewById(R.id.imageview_profile);
        camera = findViewById(R.id.iv_camera);
        toolbar = findViewById(R.id.toolBar);

        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userData = paperDb.getFromPaperDb();
        title.setText(userData.userName);
        email.setText(userData.emailId);
        mobile.setText(userData.mobile);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start cropping activity for pre-acquired image saved on the device
                CropImage.activity(selectedImageURI).setAspectRatio(1,1)
                      .start(ProfileActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                selectedImageURI=resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
            Glide.with(this)
                    .load(selectedImageURI) // Uri of the picture
                    .into(profilePhoto);

        }
    }
}