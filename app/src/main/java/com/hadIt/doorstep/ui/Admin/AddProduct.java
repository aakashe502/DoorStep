package com.hadIt.doorstep.ui.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hadIt.doorstep.HomePage;
import com.hadIt.doorstep.R;
import com.hadIt.doorstep.SaveDetailsToFirestore;

import java.util.HashMap;

public class AddProduct extends AppCompatActivity {
    public ImageView image;
    public EditText name,rate;
    public Button add;
    public static final int PICK_IMAGE = 1;
    Uri selectedImageURI;
    FirebaseStorage storage;
    StorageReference storageReference;
    public FirebaseFirestore db;
     String groceryid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        image=findViewById(R.id.prodimage);
        name=findViewById(R.id.prodname);
        rate=findViewById(R.id.prodrate);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db=FirebaseFirestore.getInstance();
        add=findViewById(R.id.addprod);

        groceryid = getIntent().getStringExtra("name");
        Toast.makeText(this,"id is"+groceryid,Toast.LENGTH_SHORT).show();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getinfo(name.getText().toString(),rate.getText().toString(),selectedImageURI.toString())==1){
                    LoadToDatabase();
                }
                else{
                    Toast.makeText(AddProduct.this,"Complete INFO NOT provided",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void LoadToDatabase() {

       // Toast.makeText(this,"it came",Toast.LENGTH_SHORT).show();
        //FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        //db.setFirestoreSettings(settings);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference(selectedImageURI.toString());
        storageReference.putFile(selectedImageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadImageUri= uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            Log.i("ha kaam kr rha","yup ");
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("productname",""+name.getText().toString());
                            hashMap.put("productimage",""+downloadImageUri.toString());
                            hashMap.put("productrate",""+rate.getText().toString());
                            db.collection("Products").document("products").collection(groceryid).document(name.getText().toString()).set(hashMap).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddProduct.this,"Successful",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddProduct.this,"Failed",Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    


                        }
                        else{
                            Toast.makeText(AddProduct.this,"inside error",Toast.LENGTH_SHORT).show();
                        }
                        }


                });



    }
    public  int getinfo(String name,String rate,String imageuri){
        if(imageuri==null)
            return 0;
        if(name==null)
            return 0;
        if(rate==null)
            return 0;
        return 1;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE&&data!=null) {
            //TODO: action
            selectedImageURI = data.getData();
            Glide.with(this)
                    .load(selectedImageURI) // Uri of the picture
    .into(image);

        }
    }
}