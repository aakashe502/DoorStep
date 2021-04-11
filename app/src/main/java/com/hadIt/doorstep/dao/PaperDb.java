package com.hadIt.doorstep.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.ui.Admin.InfoData;

import java.util.logging.Handler;

import io.paperdb.Paper;

public class PaperDb {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    public Users userData;

    public PaperDb(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public Users getFromPaperDb(){
        userData = Paper.book().read(auth.getCurrentUser().getUid());
        if(userData==null){
            saveInPaperDb();
        }
        return userData;
    }

    public void saveInPaperDb(){
        final String currentUser = auth.getCurrentUser().getEmail();
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("state", "CA");


//        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot dc=task.getResult().
//                    for(DocumentSnapshot dc:task.getResult().g){
//                        Users user=dc.toObject(Users.class);
//                        Paper.book().write(auth.getCurrentUser().getUid(), user);
//                    }
//
//                }
//            }
//        });
    }
}
