package com.hadIt.doorstep.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hadIt.doorstep.cache.model.Users;
import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;

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

    public Users getUserFromPaperDb(){
        userData = Paper.book().read(firebaseAuth.getCurrentUser().getUid());
        if(userData==null){
            saveUserInPaperDb();
        }
        return userData;
    }

    public void saveUserInPaperDb(){
        String currentUser = firebaseAuth.getCurrentUser().getEmail();

        try {
            Task<DocumentSnapshot> documents = firebaseFirestore.collection("users").document(currentUser).get();
            sleep(2000);
            DocumentSnapshot userObject = documents.getResult();
            if(userObject!=null){
                userData = userObject.toObject(Users.class);
                Paper.book().write(firebaseAuth.getCurrentUser().getUid(), userData);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public AddressModel getAddressFromPaperDb(){
        AddressModel userAddress = Paper.book().read(firebaseAuth.getCurrentUser().getUid()+"address");
        return userAddress;
    }

    public void saveAddressInPaperDb(AddressModel addressModelClass) throws InterruptedException {
        Paper.book().write(firebaseAuth.getCurrentUser().getUid()+"address", addressModelClass);
        sleep(1000);
    }
}
