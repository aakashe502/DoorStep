package com.hadIt.doorstep.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.dao.PaperDb;
import com.hadIt.doorstep.progressBar.CustomProgressBar;
import com.hadIt.doorstep.roomDatabase.address.AddressDataTransfer;
import com.hadIt.doorstep.roomDatabase.address.AddressRepository;
import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

public class EditAddress extends AppCompatActivity implements AddressDataTransfer {

    private ImageButton backBtn;
    private String addressUid, latitude, longitude;
    private final String Tag = "Edit Address";
    private EditText firstNameEt, lastNameEt, mobileNumberEt, houseNumberEt, apartmentNameEt,
            landmarkEt, areaDetailsEt, cityName, pincodeEt;
    private Button saveAddress;
    private FirebaseFirestore firebaseFirestore;
    private PaperDb paperDb;
    private AddressRepository addressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

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
        addressRepository = new AddressRepository(getApplication());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addressUid = getIntent().getStringExtra("AddressUid");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        firstNameEt.setText(getIntent().getStringExtra("firstName"));
        lastNameEt.setText(getIntent().getStringExtra("lastName"));
        mobileNumberEt.setText(getIntent().getStringExtra("contactNumber"));
        houseNumberEt.setText(getIntent().getStringExtra("houseNumber"));
        apartmentNameEt.setText(getIntent().getStringExtra("apartmentName"));
        landmarkEt.setText(getIntent().getStringExtra("landmark"));
        areaDetailsEt.setText(getIntent().getStringExtra("areaDetails"));
        cityName.setText(getIntent().getStringExtra("city"));
        pincodeEt.setText(getIntent().getStringExtra("pincode"));

        firebaseFirestore = FirebaseFirestore.getInstance();
        paperDb = new PaperDb();

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveAddress();
            }
        });
    }

    private void handleSaveAddress() {
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

        final AddressModel addressModelClass = new AddressModel(firstNameEt.getText().toString(), lastNameEt.getText().toString(),
                mobileNumberEt.getText().toString(), houseNumberEt.getText().toString(),
                TextUtils.isEmpty(apartmentNameEt.getText()) ? "":apartmentNameEt.getText().toString(),
                landmarkEt.getText().toString(), areaDetailsEt.getText().toString(), cityName.getText().toString(),
                pincodeEt.getText().toString(), latitude, longitude, addressUid);

        Users users = paperDb.getUserFromPaperDb();

        final CustomProgressBar customProgressBar = new CustomProgressBar(EditAddress.this);
        customProgressBar.show();
        firebaseFirestore.collection("users").document(users.emailId).collection("address").document(addressUid)
                .set(addressModelClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(EditAddress.this, SelectAddress.class));
                        onSetValues(addressModelClass);
                        customProgressBar.dismiss();
                        Toast.makeText(EditAddress.this, "Address Added Successfully...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(Tag, e.getStackTrace().toString());
                        customProgressBar.dismiss();
                        Toast.makeText( EditAddress.this, "Error in saving the address", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSetValues(AddressModel addressModel) {
        addressRepository.insert(addressModel);
    }

    @Override
    public void onDelete(AddressModel addressModel) {
        addressRepository.delete(addressModel.getAddressUid());
    }
}