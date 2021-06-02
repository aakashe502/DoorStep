package com.hadIt.doorstep.homePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hadIt.doorstep.CheckoutActivity;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.OrderDetails;
import com.hadIt.doorstep.fragment_ui.Settings.Settings;
import com.hadIt.doorstep.login_signup.LoginActivity;
import com.hadIt.doorstep.order_details.OrdersActivity;
import com.hadIt.doorstep.search.SearchActivity;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.fragment_ui.Admin.AddGrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    public CircleImageView profilePhoto;
    public TextView userName;
    View header;
    NavController navController;
    public Users userData;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        drawerLayout=findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.close();
        toggle.syncState();
        navigationView = findViewById(R.id.nav_get);

        header = navigationView.getHeaderView(0);
        profilePhoto = header.findViewById(R.id.admin_profile_pic);
        userName = header.findViewById(R.id.name);

        PaperDb paperDb=new PaperDb();
        if(paperDb.getUserFromPaperDb() == null){
            try {
                paperDb.saveUserInPaperDb();
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        userData = paperDb.getUserFromPaperDb();
        userName.setText(userData.userName);

        if(userData.profilePhoto != null){
            setProfilePhoto(userData.profilePhoto);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                Fragment fragment=null;
                switch (id)
                {
                    case R.id.search:
                        startActivity(new Intent(HomePage.this, SearchActivity.class));
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomePage.this, LoginActivity.class));
                        break;
                    case R.id.basket:
                        startActivity(new Intent(HomePage.this, AddGrocery.class));
                        break;
                    case  R.id.cart:
                        startActivity(new Intent(HomePage.this, CheckoutActivity.class));
                        break;
                    case R.id.orders:
                        startActivity(new Intent(HomePage.this, OrdersActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void setProfilePhoto(String uri){
        Glide.with(this)
            .load(uri) // Uri of the picture
            .into(profilePhoto);
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId() == R.id.navigation_home){
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
        }
        else{
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
}