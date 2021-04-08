package com.hadIt.doorstep.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.cache.model.Users;

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
        String currentUser = auth.getCurrentUser().getEmail();

        Task<DocumentSnapshot> documents = db.collection("users").document(currentUser).get();
        DocumentSnapshot userObject = documents.getResult();
        if(userObject!=null){
            userData = userObject.toObject(Users.class);
            Paper.book().write(auth.getCurrentUser().getUid(), userData);
        }
    }
}
