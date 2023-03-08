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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FireStoreClass {

    private final String userName;
    private FirebaseFirestore db;

    //needs username as that is the key to getting data from database
    public FireStoreClass(String userName){
        this.userName = userName;
    }


    /**
     *
     * @param playerCode needs a player code to add it to the database
     */
    public void addAQRCode(PlayerCode playerCode){

        db = FirebaseFirestore.getInstance();

        HashMap<String, Object> data = new HashMap<>();

        String name = playerCode.getPlayerCodeName();
        int score = playerCode.getPlayerCodeScore();
        Date date = playerCode.getPlayerCodeDate();
        int hashcode = playerCode.getPlayerCodeHashCode();
        String picture = playerCode.getPlayerCodePicture();
        Pair<Integer, Integer> location = playerCode.getPlayerCodeLocation();
        ArrayList<String> comments = playerCode.getPlayerCodeComments();

        data.put("Name",name);
        data.put("Score",score);
        data.put("Date", date);
        data.put("HashCode",hashcode);
        data.put("Picture",picture);
        data.put("Location",location);
        data.put("Comments",comments);

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
                            }
                        } else {
                            Log.d("Working", "Query Unsuccessful");
                        }
                    }
                });
    }


}