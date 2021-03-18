package com.hadIt.doorstep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.dao.GetFirebaseInstance;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;
import com.hadIt.doorstep.model.Users;

import java.util.Objects;

public class SaveDetailsToFirestore extends AppCompatActivity {

    public EditText userName, emailId, password, phoneNumber;
    public Button saveDetails;
    public FirebaseFirestore db;
    public GetFirebaseInstance firebaseInstance;
    public FirebaseAuth auth;
    public String Tag = "SaveDetailsToFirestore Activity";
    public PasswordGeneratorMd5 md5;
    public boolean phoneNumberExists = false;
    public boolean emailExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_details_to_firestore);

        db = FirebaseFirestore.getInstance();
        md5 = new PasswordGeneratorMd5();
        auth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.editName);
        emailId = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPass);
        phoneNumber = findViewById(R.id.editMobile);
        saveDetails = findViewById(R.id.buttonAcount);

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = checkInput(userName, emailId, password, phoneNumber);
                if(status == 0 || emailExists)
                    return;
                Log.i(Tag, emailExists+" LKSAHJLKDJSJDA");
                createAccount(userName, emailId, password, phoneNumber);
            }
        });
    }

    public void setSignUp(){
        auth.createUserWithEmailAndPassword(emailId.getText().toString(), md5.btnMd5(password))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Tag, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(Tag, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SaveDetailsToFirestore.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void createAccount(EditText userName, EditText emailId, EditText password, EditText phoneNumber){

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        db.setFirestoreSettings(settings);

        Users user=new Users();
        user.setEmailId(emailId.getText().toString());
        user.setMobile(phoneNumber.getText().toString());
        user.setPassword(md5.btnMd5(password));
        user.setUserName(userName.getText().toString());

        Log.i("Id", "DocumentSnapshot added with ID: HELLO");
        db.collection("users").document(user.getEmailId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(Tag, "Success");
                        Intent intent=new Intent(SaveDetailsToFirestore.this, HomePage.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText( SaveDetailsToFirestore.this,"Error " + error, Toast.LENGTH_SHORT).show();
                        Log.i(Tag, "Error adding document", e);
                    }
                });
        Log.i(Tag,"Done");
    }

    protected int checkInput(EditText userName, EditText emailId, EditText password, EditText phoneNumber){
        if(userName == null || userName.getText().toString().isEmpty()){
            Toast.makeText(SaveDetailsToFirestore.this, "UserName cannot be empty", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(password == null || password.getText().toString().length()<4){
            Toast.makeText(SaveDetailsToFirestore.this, "Password must be of minimum length 4", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(emailId == null){
            Toast.makeText(SaveDetailsToFirestore.this, "Email Id can't be null", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(phoneNumber == null || phoneNumber.getText().toString().length() != 10){
            Toast.makeText(SaveDetailsToFirestore.this, "Phone number should be of 10 digits", Toast.LENGTH_SHORT).show();
            return 0;
        } else if(phoneNumber.getText().toString().equals(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getPhoneNumber()).substring(3))){
            Toast.makeText(SaveDetailsToFirestore.this, "Please Enter the same number as your login Mobile number", Toast.LENGTH_SHORT).show();
            return 0;
        } else if(phoneNumberInDatabase(phoneNumber)){
            Toast.makeText(SaveDetailsToFirestore.this, "Please Enter the same number as your login Mobile number", Toast.LENGTH_SHORT).show();
        }

        if(true){
            emailIdInDatabase(emailId);
            if(emailExists){
                Toast.makeText(SaveDetailsToFirestore.this, "This Email already exists", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        return 1;
    }
    private void emailIdInDatabase(EditText emailId) {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("emailId", emailId.getText().toString());
        Log.i("HEY BOY", usersRef.toString());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.i(Tag, "HEYEHEYEYEYEEYYEYEY");
                        if (document.exists()) {
                            Toast.makeText(SaveDetailsToFirestore.this, "Email Id already exists", Toast.LENGTH_LONG).show();
                            emailExists = true;
                        }
                        else
                            emailExists = false;
                    }
                }
            }
        });
    }

    private boolean phoneNumberInDatabase(EditText phoneNumber) {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("phoneNumber", phoneNumber.getText().toString());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        if (document.exists()) {
                            Toast.makeText(SaveDetailsToFirestore.this, "Mobile Number already exists", Toast.LENGTH_LONG).show();
                            phoneNumberExists();
                        }
                    }
                }
            }
        });
        return false;
    }

    private void phoneNumberExists(){
        phoneNumberExists = true;
    }
    private void emailExists(){
        emailExists = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
