package com.example.pokemonqrcode;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class FireStoreAuthentication {
    private boolean isUsernameValid;
    private boolean correctPass;

    private FirebaseFirestore db;


    /**
     * Use for first time signup
     */
    public FireStoreAuthentication(){}


    /**
     * This method validates the username to see if it already exists in the database
     * @param username username user wishes to go by
     * @return true if username does not already exist in database, else otherwise
     */
    public void validUsername(String username, FireStoreResults fireStoreResults){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(username);
                docRef
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                isUsernameValid = false;
                                Log.d("Working", "Username already exists");
                            } else {
                                isUsernameValid = true;
                                Log.d("Working", "Username does not exist");
                            }
                            fireStoreResults.onResultGet(isUsernameValid);
                        } else {
                            Log.d("Working", "Get failed with", task.getException());
                        }
                    }
                });
    }

    /**
     * Allows user to set his password for his account
     * @param username this is the users username
     * @param password this is the users password
     */
    public void createUser(String username, String password, String email){
        HashMap<String, Object> data = new HashMap<>();

        data.put("Password",password);
        data.put("Email", email);

        CollectionReference innerCollectionRef = db.collection("Users");
        innerCollectionRef
                .document(username)
                .set(data)
                .addOnSuccessListener(unused -> Log.d("Working", "Password successfully set"))
                .addOnFailureListener(e -> Log.d("Working", "password unsuccessful" + e));
    }

    /**
     * Checks the users password when they login
     * @param username the users username
     * @param password the users password
     * @return true if the user gave the correct password for his username, returns false otherwise
     */

    public void checkPassword(String username, String password, FireStoreResults fireStoreResults){
        db = FirebaseFirestore.getInstance();
        CollectionReference innerCollectionRef = db.collection("Users");
        innerCollectionRef.document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    if (document.get("Password", String.class).equals(password)) {
                        Log.d("Working", "valid password");
                        correctPass = true;
                    } else {
                        Log.d("Working", "invalid password");
                        correctPass = false;
                    }
                } else {
                    Log.d("Working", "Username not found");
                    correctPass = false;
                }
                fireStoreResults.onResultGet(correctPass);
            }
        });
    }
}

