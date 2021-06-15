package com.hadIt.doorstep.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.hadIt.doorstep.address.AddNewAddress;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.homePage.HomePage;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.progressBar.CustomProgressBar;

import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;

public class SignUpActivity extends AppCompatActivity {

    public EditText userName, emailId, password, phoneNumber;
    private TextView areaDetails;
    private String city, state, country, pinCode;
    public Button saveDetails;
    public FirebaseFirestore firebaseFirestore;
    public FirebaseAuth firebaseAuth;
    public String Tag = "SignUpActivity";
    public PasswordGeneratorMd5 md5;
    public boolean phoneNumberExists = false;
    public boolean emailExists = false;
    public boolean isNewUser;
    private CustomProgressBar customProgressBar;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    private PaperDb paperDb;

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
        areaDetails = findViewById(R.id.areaDetails);
        paperDb = new PaperDb();

        areaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int status = checkInput(userName, emailId, password, phoneNumber, areaDetails);
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
        user.setUserName(userName.getText().toString());
        user.setCity(city);
        user.setState(state);
        user.setCountry(country);
        user.setUid(firebaseAuth.getUid());
        user.setPinCode(pinCode);

        Log.i("Id", "DocumentSnapshot added with ID: HELLO");
        firebaseFirestore.collection("users").document(user.getEmailId())
            .set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(Tag, "Success");
                    paperDb.saveUserInPaperDb();
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

    protected int checkInput(EditText userName, EditText emailId, EditText password, EditText phoneNumber, TextView areaDetails){
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

        if(city == null){
            Toast.makeText(SignUpActivity.this, "Please provide area details", Toast.LENGTH_SHORT).show();
            return 0;
        }

        return 1;
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(
                SignUpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                SignUpActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this);

            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Log.i("HEY", ""+location);
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());
                        setAddress();
                    }
                    else{
                        //TODO(Open Map here and save current location)
                        String address = "https://maps.google.com/maps?saddr=" + 27 + "," + 84 + "&daddr=" + 27 + "," + 84;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                        startActivity(intent);
                    }
                }
            });
            locationTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("HEY", e.getMessage());
                }
            });
        }
    }

    private void setAddress() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
            //complete address
            String address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            pinCode = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();
            areaDetails.setText(city+"("+pinCode+")"+", "+state+", "+country);
        } catch (Exception e){
            Toast.makeText(SignUpActivity.this,"Unable to fetch customers address. " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
