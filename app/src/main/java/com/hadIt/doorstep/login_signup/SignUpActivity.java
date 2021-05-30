package com.hadIt.doorstep.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.progressBar.CustomProgressBar;

public class SignUpActivity extends AppCompatActivity {

    public EditText userName, emailId, password, phoneNumber;
    public Button saveDetails;
    public FirebaseFirestore firebaseFirestore;
    public FirebaseAuth firebaseAuth;
    public String Tag = "SaveDetailsToFirestore Activity";
    public PasswordGeneratorMd5 md5;
    public boolean phoneNumberExists = false;
    public boolean emailExists = false;
    public boolean isNewUser;
    private CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        firebaseFirestore = FirebaseFirestore.getInstance();
        md5 = new PasswordGeneratorMd5();
        firebaseAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.editName);
        emailId = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPass);
        phoneNumber = findViewById(R.id.editMobile);
        saveDetails = findViewById(R.id.buttonAcount);

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int status = checkInput(userName, emailId, password, phoneNumber);
            if(status == 0)
                return;
            customProgressBar = new CustomProgressBar(SignUpActivity.this);
            customProgressBar.show();
            firebaseAuth.fetchSignInMethodsForEmail(emailId.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            setSignUp();
                            Log.e("TAG", "Is New User!");
                        } else {
                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });
            }
        });
    }

    public void setSignUp(){
        firebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString(), md5.btnMd5(password))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Tag, "createUserWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        createAccount(userName, emailId, password, phoneNumber);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(Tag, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                    }
                });
    }

    public void createAccount(EditText userName, EditText emailId, EditText password, EditText phoneNumber){

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        firebaseFirestore.setFirestoreSettings(settings);

        Users user=new Users();
        user.setEmailId(emailId.getText().toString());
        user.setMobile(phoneNumber.getText().toString());
        user.setPassword(md5.btnMd5(password));
        user.setUserName(userName.getText().toString());

        Log.i("Id", "DocumentSnapshot added with ID: HELLO");
        firebaseFirestore.collection("users").document(user.getEmailId())
            .set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(Tag, "Success");
                    Intent intent=new Intent(SignUpActivity.this, HomePage.class);
                    startActivity(intent);
                    customProgressBar.dismiss();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onFailure(@NonNull Exception e) {
                    String error = e.getMessage();
                    Toast.makeText( SignUpActivity.this,"Error " + error, Toast.LENGTH_SHORT).show();
                    customProgressBar.dismiss();
                    Log.i(Tag, "Error adding document", e);
                }
            });
    }

    protected int checkInput(EditText userName, EditText emailId, EditText password, EditText phoneNumber){
        if(userName == null || userName.getText().toString().isEmpty()){
            Toast.makeText(SignUpActivity.this, "UserName cannot be empty", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(password == null || password.getText().toString().length()<4){
            Toast.makeText(SignUpActivity.this, "Password must be of minimum length 4", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(emailId == null){
            Toast.makeText(SignUpActivity.this, "Email Id can't be null", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(phoneNumber == null || phoneNumber.getText().toString().length() != 10){
            Toast.makeText(SignUpActivity.this, "Phone number should be of 10 digits", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
