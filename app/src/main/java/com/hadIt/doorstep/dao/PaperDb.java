package com.hadIt.doorstep.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.cache.model.Users;

import io.paperdb.Paper;

import static java.lang.Thread.sleep;

public class PaperDb {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    public Users userData;

    public PaperDb(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public Users getFromPaperDb(){
        userData = Paper.book().read(firebaseAuth.getCurrentUser().getUid());
        if(userData==null){
            saveInPaperDb();
        }
        return userData;
    }

    public void saveInPaperDb(){
        String currentUser = firebaseAuth.getCurrentUser().getEmail();

        try {
            Task<DocumentSnapshot> documents = firebaseFirestore.collection("users").document(currentUser).get();
            sleep(1000);
            DocumentSnapshot userObject = documents.getResult();
            if(userObject!=null){
                userData = userObject.toObject(Users.class);
                Paper.book().write(firebaseAuth.getCurrentUser().getUid(), userData);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
