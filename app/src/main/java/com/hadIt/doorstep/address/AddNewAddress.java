package com.hadIt.doorstep.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.AddressModelClass;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;

import java.util.List;
import java.util.Locale;

public class AddNewAddress extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private ImageButton backBtn, gpsBtn;
    private EditText firstNameEt, lastNameEt, mobileNumberEt, houseNumberEt, apartmentNameEt,
            landmarkEt, areaDetailsEt, cityName, pincodeEt;
    private Button saveAddress;
    private FirebaseFirestore firebaseFirestore;
    private PaperDb paperDb;
    private static final String Tag = "Add New Address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        backBtn = findViewById(R.id.backBtn);
        firstNameEt = findViewById(R.id.firstNameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        mobileNumberEt = findViewById(R.id.mobileNumberEt);
        houseNumberEt = findViewById(R.id.houseNumberEt);
        apartmentNameEt = findViewById(R.id.apartmentNameEt);
        landmarkEt = findViewById(R.id.landmarkEt);
        areaDetailsEt = findViewById(R.id.areaDetailsEt);
        cityName = findViewById(R.id.cityName);
        pincodeEt = findViewById(R.id.pincodeEt);
        saveAddress = findViewById(R.id.saveAddress);
        firebaseFirestore = FirebaseFirestore.getInstance();
        paperDb = new PaperDb();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveAddress();
            }
        });

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        gpsBtn = findViewById(R.id.gpsBtn);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
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
        if (ActivityCompat.checkSelfPermission(
                AddNewAddress.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddNewAddress.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        //TODO(Open Map here and open location)
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
            String addressList[] = address.split(",");
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<addressList.length-3; i++)
                sb.append(addressList[i]+",");
            areaDetailsEt.setText(sb.delete(sb.length()-1, sb.length()).toString());
            cityName.setText(addressList[addressList.length-3].trim());
            String state[] = addressList[addressList.length-2].split(" ");
            pincodeEt.setText(state[state.length-1].trim());
        } catch (Exception e){
            Toast.makeText(AddNewAddress.this,"Unable to fetch customers address. " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleSaveAddress() {

        if(latitude == null || longitude == null){
            Toast.makeText(this,"Please press the gps button on the top right 3 times. Thanks!!", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(firstNameEt.getText()) || TextUtils.isEmpty(lastNameEt.getText())){
            Toast.makeText(this,"Please Enter Your Full Name...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobileNumberEt.getText().length() != 10){
            Toast.makeText(this,"Please Enter Valid Mobile Number...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(houseNumberEt.getText())){
            Toast.makeText(this,"Please provide Your House number...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(landmarkEt.getText())){
            Toast.makeText(this,"Please provide nearby known location...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(areaDetailsEt.getText())){
            Toast.makeText(this,"Please provide your Area Name...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pincodeEt.getText())){
            Toast.makeText(this,"Please provide Postal code...", Toast.LENGTH_SHORT).show();
            return;
        }

        String addressUid = "" + System.currentTimeMillis();
        AddressModelClass addressModelClass = new AddressModelClass(firstNameEt.getText().toString(), lastNameEt.getText().toString(),
                mobileNumberEt.getText().toString(), houseNumberEt.getText().toString(),
                TextUtils.isEmpty(apartmentNameEt.getText()) ? "":apartmentNameEt.getText().toString(),
                landmarkEt.getText().toString(), areaDetailsEt.getText().toString(), cityName.getText().toString(),
                pincodeEt.getText().toString(), latitude, longitude, addressUid);

        Users users = paperDb.getUserFromPaperDb();
        firebaseFirestore.collection("users").document(users.emailId).collection("address").document(addressUid)
            .set(addressModelClass)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddNewAddress.this, "Address Added Successfully...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddNewAddress.this, SelectAddress.class));
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(Tag, e.getStackTrace().toString());
                    Toast.makeText( AddNewAddress.this, "Error in saving the address", Toast.LENGTH_SHORT).show();
                }
            });
    }
}