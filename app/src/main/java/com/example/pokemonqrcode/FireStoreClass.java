package com.example.pokemonqrcode;

import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;

public class FireStoreClass {

    public static int totalSum;
    private final String userName;
    private FirebaseFirestore db;

    //needs username as that is the key to getting data from database
    public FireStoreClass(String userName){
        this.userName = userName;
    }


    /**
     * Adds a scanned QR code to the users collection
     * @param name name of scanned QR code
     * @param score score of the scanned QR code
     * @param location location where QR code was scanned
     * @param hashcode Scanned hashcode
     */
    public void addAQRCode(String name, int score, Pair<Integer, Integer> location,
                    int hashcode){

        db = FirebaseFirestore.getInstance();

        HashMap<String, Object> data = new HashMap<>();

        data.put("Name",name);
        data.put("Score",score);
        data.put("Location",location);
        data.put("HashCode",hashcode);

        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(name)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Working", "Data added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Working", "data not added" + e.toString());
                    }
                });

    }

    /**
     * Returns the total score off all the QR codes
     */
    public void totalScore(){
        db.collection("Users/"+userName+"/QRCodes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("Working", document.getId() + " => " + document.getLong("Score"));
                                //totalSum += document.getLong("Score").intValue();
                            }
                        } else {
                            Log.d("Working", "Query Unsuccessful");
                        }
                    }
                });
    }


}