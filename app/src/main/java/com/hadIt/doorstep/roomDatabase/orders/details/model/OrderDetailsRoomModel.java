package com.hadIt.doorstep.roomDatabase.orders.details.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orderDetails", indices = @Index(value = {"orderId"}, unique = true))
public class OrderDetailsRoomModel implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String orderId;

    @ColumnInfo(name = "orderDate")
    private String orderDate;
    @ColumnInfo(name = "orderStatus")
    private String orderStatus;
    @ColumnInfo(name = "buyerEmail")
    private String buyerEmail;
    @ColumnInfo(name = "buyerUid")
    private String buyerUid;
    @ColumnInfo(name = "buyerFullName")
    private String buyerFullName;
    @ColumnInfo(name = "buyerMobileNumber")
    private String buyerMobileNumber;
    @ColumnInfo(name = "buyerHouseDetails")
    private String buyerHouseDetails;
    @ColumnInfo(name = "buyerLandmark")
    private String buyerLandmark;
    @ColumnInfo(name = "buyerAreaDetails")
    private String buyerAreaDetails;
    @ColumnInfo(name = "buyerCity")
    private String buyerCity;
    @ColumnInfo(name = "buyerPincode")
    private String buyerPincode;
    @ColumnInfo(name = "buyerLatitude")
    private String buyerLatitude;
    @ColumnInfo(name = "buyerLongitude")
    private String buyerLongitude;
    @ColumnInfo(name = "sellerEmail")
    private String sellerEmail;
    @ColumnInfo(name = "sellerUid")
    private String sellerUid;
    @ColumnInfo(name = "sellerShopName")
    private String sellerShopName;
    @ColumnInfo(name = "sellerMobileNumber")
    private String sellerMobileNumber;
    @ColumnInfo(name = "sellerLatitude")
    private String sellerLatitude;
    @ColumnInfo(name = "sellerLongitude")
    private String sellerLongitude;
    @ColumnInfo(name = "sellerCity")
    private String sellerCity;
    @ColumnInfo(name = "sellerPincode")
    private String sellerPincode;
    @ColumnInfo(name = "sellerAreaDetails")
    private String sellerAreaDetails;
    @ColumnInfo(name = "sellerLandmark")
    private String sellerLandmark;
    @ColumnInfo(name = "totalAmount")
    private double totalAmount;
    @ColumnInfo(name = "itemCount")
    private int itemCount;

    public OrderDetailsRoomModel(String orderDate, @NonNull String orderId, String orderStatus, String buyerEmail, String buyerUid, String buyerFullName, String buyerMobileNumber, String buyerHouseDetails, String buyerLandmark, String buyerAreaDetails, String buyerCity, String buyerPincode, String buyerLatitude, String buyerLongitude, String sellerEmail, String sellerUid, String sellerShopName, String sellerMobileNumber, String sellerLatitude, String sellerLongitude, String sellerCity, String sellerPincode, String sellerAreaDetails, String sellerLandmark, double totalAmount, int itemCount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.buyerEmail = buyerEmail;
        this.buyerUid = buyerUid;
        this.buyerFullName = buyerFullName;
        this.buyerMobileNumber = buyerMobileNumber;
        this.buyerHouseDetails = buyerHouseDetails;
        this.buyerLandmark = buyerLandmark;
        this.buyerAreaDetails = buyerAreaDetails;
        this.buyerCity = buyerCity;
        this.buyerPincode = buyerPincode;
        this.buyerLatitude = buyerLatitude;
        this.buyerLongitude = buyerLongitude;
        this.sellerEmail = sellerEmail;
        this.sellerUid = sellerUid;
        this.sellerShopName = sellerShopName;
        this.sellerMobileNumber = sellerMobileNumber;
        this.sellerLatitude = sellerLatitude;
        this.sellerLongitude = sellerLongitude;
        this.sellerCity = sellerCity;
        this.sellerPincode = sellerPincode;
        this.sellerAreaDetails = sellerAreaDetails;
        this.sellerLandmark = sellerLandmark;
        this.totalAmount = totalAmount;
        this.itemCount = itemCount;
    }

    public OrderDetailsRoomModel() {
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }

    public String getBuyerFullName() {
        return buyerFullName;
    }

    public void setBuyerFullName(String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }

    public String getBuyerMobileNumber() {
        return buyerMobileNumber;
    }

    public void setBuyerMobileNumber(String buyerMobileNumber) {
        this.buyerMobileNumber = buyerMobileNumber;
    }

    public String getBuyerHouseDetails() {
        return buyerHouseDetails;
    }

    public void setBuyerHouseDetails(String buyerHouseDetails) {
        this.buyerHouseDetails = buyerHouseDetails;
    }

    public String getBuyerLandmark() {
        return buyerLandmark;
    }

    public void setBuyerLandmark(String buyerLandmark) {
        this.buyerLandmark = buyerLandmark;
    }

    public String getBuyerAreaDetails() {
        return buyerAreaDetails;
    }

    public void setBuyerAreaDetails(String buyerAreaDetails) {
        this.buyerAreaDetails = buyerAreaDetails;
    }

    public String getBuyerCity() {
        return buyerCity;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerPincode() {
        return buyerPincode;
    }

    public void setBuyerPincode(String buyerPincode) {
        this.buyerPincode = buyerPincode;
    }

    public String getBuyerLatitude() {
        return buyerLatitude;
    }

    public void setBuyerLatitude(String buyerLatitude) {
        this.buyerLatitude = buyerLatitude;
    }

    public String getBuyerLongitude() {
        return buyerLongitude;
    }

    public void setBuyerLongitude(String buyerLongitude) {
        this.buyerLongitude = buyerLongitude;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public String getSellerShopName() {
        return sellerShopName;
    }

    public void setSellerShopName(String sellerShopName) {
        this.sellerShopName = sellerShopName;
    }

    public String getSellerMobileNumber() {
        return sellerMobileNumber;
    }

    public void setSellerMobileNumber(String sellerMobileNumber) {
        this.sellerMobileNumber = sellerMobileNumber;
    }

    public String getSellerLatitude() {
        return sellerLatitude;
    }

    public void setSellerLatitude(String sellerLatitude) {
        this.sellerLatitude = sellerLatitude;
    }

    public String getSellerLongitude() {
        return sellerLongitude;
    }

    public void setSellerLongitude(String sellerLongitude) {
        this.sellerLongitude = sellerLongitude;
    }

    public String getSellerCity() {
        return sellerCity;
    }

    public void setSellerCity(String sellerCity) {
        this.sellerCity = sellerCity;
    }

    public String getSellerPincode() {
        return sellerPincode;
    }

    public void setSellerPincode(String sellerPincode) {
        this.sellerPincode = sellerPincode;
    }

    public String getSellerAreaDetails() {
        return sellerAreaDetails;
    }

    public void setSellerAreaDetails(String sellerAreaDetails) {
        this.sellerAreaDetails = sellerAreaDetails;
    }

    public String getSellerLandmark() {
        return sellerLandmark;
    }

    public void setSellerLandmark(String sellerLandmark) {
        this.sellerLandmark = sellerLandmark;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String usersAddress(){
        return this.buyerFullName + ", " + this.buyerMobileNumber + ", " + this.buyerHouseDetails + ", " +  this.buyerLandmark + ", "
                + this.buyerAreaDetails + ", " + this.buyerCity + "-" + this.buyerPincode;
    }
}
