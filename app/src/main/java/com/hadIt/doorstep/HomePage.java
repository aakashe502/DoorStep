package com.hadIt.doorstep;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hadIt.doorstep.ui.Admin.AddGrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    public CircleImageView profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration= new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_dashboard,R.id.navigation_notifications,R.id.Settings,R.id.profile)
                .build();
       // View v=navView.getChildAt(3);
       // View badge = LayoutInflater.from(this)
        //        .inflate(R.layout.notification_badge, navView, true);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
//       NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navView,navController);

        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.close();
        toggle.syncState();
        navigationView=findViewById(R.id.nav_get);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                Fragment fragment=null;
                switch (id)
                {
                    case R.id.search:
                        startActivity(new Intent(HomePage.this,SearchActivity.class));
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomePage.this,LoginActivity.class));
                        break;
                    case R.id.basket:
                        startActivity(new Intent(HomePage.this,AddGrocery.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
    }
}