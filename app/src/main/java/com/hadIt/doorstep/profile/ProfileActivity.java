package com.hadIt.doorstep.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.fragment_ui.Profile.Profile;
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
    public StorageReference storageReference;

    private PaperDb paperDb;
    private Users userData;
    private HomePage homePage;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        paperDb = new PaperDb();
        homePage = new HomePage();
        profile = new Profile();

        title = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        mobile = findViewById(R.id.mobileEditText);
        profilePhoto = findViewById(R.id.admin_profile_pic);
        camera = findViewById(R.id.iv_camera);
        toolbar = findViewById(R.id.toolBar);

        toolbar.setTitle("Edit Profile");
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
        storageReference = FirebaseStorage.getInstance().getReference();

        userData = paperDb.getUserFromPaperDb();
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


        if(userData.profilePhoto!=null){
            Glide.with(this)
                    .load(userData.profilePhoto) // Uri of the picture
                    .into(profilePhoto);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                selectedImageURI=resultUri;
                Glide.with(this)
                        .load(selectedImageURI) // Uri of the picture
                        .into(profilePhoto);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

            Log.i(Tag, "Le dp ..." + selectedImageURI);
            if(selectedImageURI!=null){
                uploadToFireStore();
            }
        }
    }

    private void uploadToFireStore() {
        Log.i(Tag, "Uploading to uploadToFireStore...");
        String createPath = "profilePhoto/" + userData.emailId;

        storageReference.child(createPath).putFile(selectedImageURI)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    Log.i(Tag, "Uploading your dp...");
                    while(!uriTask.isSuccessful());
                    Uri downloadImageUri= uriTask.getResult();
                    if (uriTask.isSuccessful()) {
                        Users user = paperDb.getUserFromPaperDb();
                        user.setProfilePhoto(downloadImageUri.toString());
                        db.collection("users").document(user.emailId).set(user).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.i(Tag, "Successfully uploaded your dp...");
                                        Toast.makeText(ProfileActivity.this,"Successfully uploaded profile pic: ",Toast.LENGTH_SHORT).show();
                                        paperDb.saveUserInPaperDb();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(Tag, "Failed to upload your dp...");
                                    Toast.makeText(ProfileActivity.this,"Failed to upload profile pic: ",Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                    else{
                        Log.i(Tag, "inside error to upload profile dp...");
                        Toast.makeText(ProfileActivity.this,"inside error to upload profile pic: ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}