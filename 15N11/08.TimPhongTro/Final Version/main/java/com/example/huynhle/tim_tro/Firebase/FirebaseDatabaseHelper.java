package com.example.huynhle.tim_tro.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUserInFirebaseDatabase(String userId, FirebaseUserEntity firebaseUserEntity){
        Map<String, FirebaseUserEntity> user = new HashMap<String, FirebaseUserEntity>();
        user.put(userId, firebaseUserEntity);
        databaseReference.child("users").setValue(user);
    }
}
