package com.hadIt.doorstep.dao;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hadIt.doorstep.md5.PasswordGeneratorMd5;

public class GetFirebaseInstance {
    public FirebaseFirestore db;
    public PasswordGeneratorMd5 md5;
    public String Tag = "Firebase Instance Class";

    public GetFirebaseInstance(){
        db = FirebaseFirestore.getInstance();
        md5 = new PasswordGeneratorMd5();
    }

    public boolean emailIdInDatabase(EditText emailId, boolean exists) {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("emailId", emailId.getText().toString());
        Log.i("HEY BOY", usersRef.toString());
        final boolean[] userExists = {false};
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            String userName = document.getString("emailId");
                            userExists[0] = true;
                        }
                    }
                }
            }
        });
        return userExists[0];
    }
}
