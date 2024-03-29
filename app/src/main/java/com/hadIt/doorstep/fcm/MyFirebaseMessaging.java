package com.hadIt.doorstep.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.order_details.OrderDetailsActivity;
import com.hadIt.doorstep.roomDatabase.address.AddressViewModel;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsRepository;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsTransfer;
import com.hadIt.doorstep.roomDatabase.orders.details.OrderDetailsViewModel;
import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;
import com.hadIt.doorstep.roomDatabase.shopProducts.DatabaseRoom;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductTransfer;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductViewModel;
import com.hadIt.doorstep.roomDatabase.shopProducts.ProductsRepository;
import com.hadIt.doorstep.roomDatabase.shopProducts.model.ProductsTable;

import java.util.Random;

public class MyFirebaseMessaging extends FirebaseMessagingService implements OrderDetailsTransfer, ProductTransfer {

    private static final String NOTIFICATION_CHANNEL_ID = "MY_NOTIFICATION_CHANNEL_ID"; //FOR ANDROID 0 AND ABOVE

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private OrderDetailsRepository orderDetailsRepository;
    private ProductsRepository productsRepository;

    //all notifications will be received here
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Log.i("HEY", "DoorStep Message Rec");
        //get data from notification
        String notificationType = remoteMessage.getData().get("notificationType");

        Gson gson = new Gson();
        if(notificationType.equals("Order Status Changed") || notificationType.equals("Delivery Boy Assigned") || notificationType.equals("Delivery Status Changed")){
            OrderDetailsRoomModel orderDetailsRoomModel = gson.fromJson(remoteMessage.getData().get("orderDetailsObj"), OrderDetailsRoomModel.class);

            orderDetailsRepository = new OrderDetailsRepository(getApplication(), firebaseAuth.getUid());
            setOrderDetails(orderDetailsRoomModel);

            String notificationTitle = remoteMessage.getData().get("notificationTitle");
            String notificationMessage = remoteMessage.getData().get("notificationMessage");

            if(firebaseUser != null && firebaseAuth.getUid().equals(orderDetailsRoomModel.getBuyerUid())){
                showNotification(orderDetailsRoomModel, notificationTitle, notificationMessage, notificationType);
            }
        }

        if(notificationType.equals("Added New Product")){
            ProductsTable productsTable = gson.fromJson(remoteMessage.getData().get("product"), ProductsTable.class);
            productsRepository = new ProductsRepository(getApplication(), productsTable.getShopUid());
            saveProductDetailsIfShopRead(productsTable);
        }

        if(notificationType.equals("Delete Product")){
            ProductsTable productsTable = gson.fromJson(remoteMessage.getData().get("product"), ProductsTable.class);
            productsRepository = new ProductsRepository(getApplication(), productsTable.getShopUid());
            deleteProductDetailsIfShopRead(productsTable);
        }

        if(notificationType.equals("Refresh db")){
            String shopUid = remoteMessage.getData().get("shopUid");
            productsRepository = new ProductsRepository(getApplication(), shopUid);

            String refresh = remoteMessage.getData().get("refresh");
            if(refresh.equals("true")){
                productsRepository.refreshDb();
            }
            else{
                productsRepository.deleteProductUsingShopId(shopUid);
            }
        }
    }

    private void deleteProductDetailsIfShopRead(final ProductsTable productsTable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int countShopUid = DatabaseRoom.getInstance(getApplicationContext())
                        .Daodata()
                        .checkIfShopExists(productsTable.getShopUid());
                if(countShopUid>0){
                    deleteProductsTable(productsTable);
                }
            }
        }).start();
    }


    private void saveProductDetailsIfShopRead(final ProductsTable productsTable) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int countShopUid = DatabaseRoom.getInstance(getApplicationContext())
                        .Daodata()
                        .checkIfShopExists(productsTable.getShopUid());
                if(countShopUid>0){
                    setProductsTable(productsTable);
                }
            }
        }).start();
    }

    private void showNotification(OrderDetailsRoomModel orderDetailsRoomModel, String notificationTitle, String notificationMessage, String notificationType){
        //notification
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //id for notification
        int notificationId = new Random().nextInt(3000);

        //check if android version is Oreo/O or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setupNotificationChannel(notificationManager);
        }

        Intent intent = null;
        if(notificationType.equals("Order Status Changed") || notificationType.equals("Delivery Boy Assigned") || notificationType.equals("Delivery Status Changed")) {
            //open order details seller activity
            intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("orderDetailsObj", orderDetailsRoomModel);
            intent.putExtra("orderId", orderDetailsRoomModel.getOrderId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Large icon
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.lyptus);

        //sound notification
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.lyptus)
                .setLargeIcon(largeIcon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSound(notificationSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        //show notification
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupNotificationChannel(NotificationManager notificationManager) {
        CharSequence channelName = "Some sample text";
        String channelDescription = "Channel description here";

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(R.color.colorRed);
        notificationChannel.enableVibration(true);
        Log.i("TAG got the message", notificationManager.toString());
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void setOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.insert(orderDetailsRoomModel);
    }

    @Override
    public void deleteOrderDetails(OrderDetailsRoomModel orderDetailsRoomModel) {
        orderDetailsRepository.deleteProductUsingOrderId(orderDetailsRoomModel);
    }

    @Override
    public void setProductsTable(ProductsTable productsTable) {
        productsRepository.insert(productsTable);
    }

    @Override
    public void deleteProductsTable(ProductsTable productsTable) {
        productsRepository.delete(productsTable.getProductId());
    }
}
