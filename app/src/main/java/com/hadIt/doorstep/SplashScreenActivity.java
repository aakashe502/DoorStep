package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.login_signup.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 2000;
    Animation topAnim, bottomAnim;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED);
        setContentView(R.layout.activity_splash_screen);
       
        Paper.init(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imageView = findViewById(R.id.logo);
        imageView.setAnimation(topAnim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    startActivity(new Intent(SplashScreenActivity.this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            }
        }, SPLASH_SCREEN);
    }
}