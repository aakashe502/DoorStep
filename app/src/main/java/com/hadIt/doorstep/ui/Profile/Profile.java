package com.hadIt.doorstep.ui.Profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.LoginActivity;
import com.hadIt.doorstep.ProfileActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.chooser.WhoYouAreActivity;
import com.hadIt.doorstep.dao.PaperDb;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment  {

    public TextView tv;
    public TextView title;
    public TextView profile;
    public TextView orders;
    public TextView share;
    public TextView email;
    public Button logout;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private PaperDb paperDb;
    private Users userData;
    public CircleImageView profilePhoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile,container,false);

        title = root.findViewById(R.id.title);
        profile = root.findViewById(R.id.profile);
        orders = root.findViewById(R.id.orders);
        share = root.findViewById(R.id.share);
        logout = root.findViewById(R.id.logout);
        email = root.findViewById(R.id.email);
        profilePhoto = root.findViewById(R.id.imageview_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /*
        For Title
         */
        paperDb = new PaperDb();
        userData = paperDb.getFromPaperDb();
        title.setText(userData.userName);
        email.setText(userData.emailId);

         /*
        For Profile
         */
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), WhoYouAreActivity.class));
            }
        });

        if(userData.profilePhoto != null){
            Glide.with(this)
                    .load(userData.profilePhoto) // Uri of the picture
                    .into(profilePhoto);
        }

        return root;
    }
}
