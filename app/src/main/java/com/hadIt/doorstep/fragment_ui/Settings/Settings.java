package com.hadIt.doorstep.fragment_ui.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.utils.Constants;

public class Settings extends Fragment {

    private SwitchCompat fcmSwitch;
    private TextView notificationStatusTv;

    private boolean isChecked = false;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private static final String enabledNotification = "Notifications are enabled";
    private static final String disabledNotification = "Notifications are disabled";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_settings,container,false);

        getActivity().setTitle("Settings");
        fcmSwitch = root.findViewById(R.id.fcmSwitch);
        notificationStatusTv = root.findViewById(R.id.notificationStatusTv);

        //init shared preference
        sp = this.getActivity().getSharedPreferences("SETTINGS_SP", Context.MODE_PRIVATE);
        //check last selected option true/false
        isChecked = sp.getBoolean("FCM_ENABLED", false);
        fcmSwitch.setChecked(isChecked);
        if(isChecked){
            //was enabled
            notificationStatusTv.setText(enabledNotification);
        } else{
            //was disabled
            notificationStatusTv.setText(disabledNotification);
        }

        //add switch listener
        fcmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //checked, enable notification
                    subscribeToTopic();
                } else {
                    //unchecked, enable notification
                    unSubscribeToTopic();
                }
            }
        });

        return root;
    }

    public void subscribeToTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Subscribed Successfully
                    //save settings in shared preference
                    spEditor = sp.edit();
                    spEditor.putBoolean("FCM_ENABLED", true);
                    spEditor.apply();

                    Toast.makeText(getActivity(), enabledNotification, Toast.LENGTH_SHORT).show();
                    notificationStatusTv.setText(enabledNotification);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //failed to subscribe
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void unSubscribeToTopic(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.FCM_TOPIC)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Subscribed Successfully
                        //save settings in shared preference
                        spEditor = sp.edit();
                        spEditor.putBoolean("FCM_ENABLED", false);
                        spEditor.apply();

                        Toast.makeText(getActivity(), disabledNotification, Toast.LENGTH_SHORT).show();
                        notificationStatusTv.setText(disabledNotification);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to subscribe
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
