package com.hadIt.doorstep.cache.model;

import androidx.annotation.NonNull;

public class AddressModelClass {
    public String firstName, lastName, contactNumber, houseNumber, apartmentName, landmark,
            areaDetails, city, pincode, latitude, longitude, addressUid;

    public AddressModelClass() {
    }

    public AddressModelClass(String firstName, String lastName, String contactNumber, String houseNumber, String apartmentName, String landmark, String areaDetails, String city, String pincode, String latitude, String longitude, String addressUid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.houseNumber = houseNumber;
        this.apartmentName = apartmentName;
        this.landmark = landmark;
        this.areaDetails = areaDetails;
        this.city = city;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressUid = addressUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAreaDetails() {
        return areaDetails;
    }

    public void setAreaDetails(String areaDetails) {
        this.areaDetails = areaDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddressUid() {
        return addressUid;
    }

    public void setAddressUid(String addressUid) {
        this.addressUid = addressUid;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + "-" + lastName + ", " + contactNumber + ", " + houseNumber + "-" + apartmentName + ", " + landmark + ", "
                + areaDetails + ", " + city + "-" + pincode;
    }
}
