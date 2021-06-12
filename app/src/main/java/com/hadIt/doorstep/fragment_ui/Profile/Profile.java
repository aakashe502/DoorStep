package com.hadIt.doorstep.fragment_ui.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.address.SelectAddress;
import com.hadIt.doorstep.login_signup.LoginActivity;
import com.hadIt.doorstep.order_details.OrdersActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment  {

    public TextView tv;
    public TextView title;
    public TextView profile;
    public TextView orders;
    public TextView share;
    public TextView email;
    public TextView address;
    public Button logout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private PaperDb paperDb;
    private Users userData;
    public CircleImageView profilePhoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile,container,false);

        getActivity().setTitle("Profile");

        title = root.findViewById(R.id.title);
        profile = root.findViewById(R.id.profile);
        orders = root.findViewById(R.id.orders);
        share = root.findViewById(R.id.share);
        logout = root.findViewById(R.id.logout);
        email = root.findViewById(R.id.email);
        profilePhoto = root.findViewById(R.id.admin_profile_pic);
        address = root.findViewById(R.id.address);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /*
        For Title
         */
        paperDb = new PaperDb();
        userData = paperDb.getUserFromPaperDb();
        title.setText(userData.userName);
        email.setText(userData.emailId);

         /*
        For Profile
         */
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrdersActivity.class));
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SelectAddress.class));
            }
        });

        if(userData.profilePhoto != null){
            setProfilePhoto(userData.profilePhoto);
        }

        return root;
    }

    public void setProfilePhoto(String uri){
        Glide.with(this)
                .load(uri) // Uri of the picture
                .into(profilePhoto);
    }
}
