package com.hadIt.doorstep.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.hadIt.doorstep.R;

import java.util.Arrays;

public class AddNewAddress extends AppCompatActivity {

    private String TAG = "Add New Address";
    private ImageButton backBtn;
    private EditText firstNameEt, lastNameEt, mobileNumberEt, houseNumberEt, apartmentNameEt,
            landmarkEt, areaDetailsEt, cityName, pincodeEt;
    private Button saveAddress;

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }
}