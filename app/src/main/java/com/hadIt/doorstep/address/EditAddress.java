package com.hadIt.doorstep.address;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hadIt.doorstep.R;

public class EditAddress extends AppCompatActivity {

    private ImageButton backBtn;
    private String addressUid, latitude, longitude;
    private String TAG = "Edit Address";
    private EditText firstNameEt, lastNameEt, mobileNumberEt, houseNumberEt, apartmentNameEt,
            landmarkEt, areaDetailsEt, cityName, pincodeEt;
    private Button saveAddress;

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
    }
}