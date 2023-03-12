package com.example.pokemonqrcode;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class FireStoreAuthentication {
    private boolean isUsernameValid;
    private boolean correctPass;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Use for first time signup
     */
    public FireStoreAuthentication(){}


    /**
     * This method validates the username to see if it already exists in the database
     * @param username username user wishes to go by
     * @return true if username does not already exist in database, else otherwise
     */
    public boolean validUsername(String username){
        DocumentReference docRef = db.collection("Users").document(username);
                docRef
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                Log.d("Working", "Username already exists");
                                isUsernameValid = false;
                            } else {
                                Log.d("Working", "Username does not exist");
                                isUsernameValid = true;
                            }
                        } else {
                            Log.d("Working", "Get failed with", task.getException());
                        }
                    }
                });

        return isUsernameValid;
    }

    /**
     * Allows user to set his password for his account
     * @param username this is the users username
     * @param password this is the users password
     */
    public void setPassword(String username, String password){
        HashMap<String, Object> data = new HashMap<>();

        data.put("Password",password);

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
     * @return true if the usser gave the correct password for his username, returns false otherwise
     */
    public boolean checkPassword(String username, String password){
        CollectionReference innerCollectionRef = db.collection("Users");
        innerCollectionRef.document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.get("Password", String.class).equals(password)){
                            Log.d("Working", "valid password");
                            correctPass = true;
                        } else {
                            Log.d("Working", "invalid password");
                            correctPass = false;
                            }
                        }
                });
        return correctPass;
    }
}
