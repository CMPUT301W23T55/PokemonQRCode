package com.example.pokemonqrcode;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Create an instance of the Firestore database
 */
//for all query operations, https://firebase.google.com/docs/firestore/
//was used as a reference
public class FireStoreClass implements Serializable {

    final private String userName;
    private FirebaseFirestore db;

    private ArrayList<PlayerCode> codes = new ArrayList<PlayerCode>();
    private int totalScore;
    private int count;

    //needs username as that is the key to getting data from database

    /**
     * Used when they log back into an existing account, or when device auto-identifies user
     * @param userName unique identifier
     */
    public FireStoreClass(@NonNull String userName){
        this.userName = userName;
    }


    /**
     *
     * @param pC needs a player code to add it to the database
     */
    public void addAQRCode(@NonNull PlayerCode pC){
        db = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();


        String name = pC.getName();
        int score = pC.getScore();
        Date date = pC.getDate();
        String hashcode = pC.getHashCode();
        String picture = pC.getPicture();
        String comments = pC.getComments();


        data.put("Name",name);
        data.put("Score",score);
        data.put("Date", date);
        data.put("HashCode",hashcode);
        data.put("Picture",picture);
        data.put("Comments",comments);

        this.codes.add(pC);

        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(String.valueOf(hashcode))
                .set(data)
                .addOnSuccessListener(unused -> Log.d("Working", "Data added successfully"))
                .addOnFailureListener(e -> Log.d("Working", "error exception occurred" + e));
    }

    /**
     * Given a PlayerCode, this method will delete that associated record from the database
     * @param pC PlayerCode that should be deleted from the database
     */
    public void deleteCode(PlayerCode pC){
        db = FirebaseFirestore.getInstance();
        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(pC.getHashCode())
                .delete()
                .addOnSuccessListener(unused -> Log.d("Working", "Document successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));
    }

    /**
     *
     * @return an array of PlayerCodes from the firestore database
     */
    public ArrayList<PlayerCode> getPlayerCodes(){
        return this.codes;
    }

    public int getPlayerScore(){
        return this.totalScore;
    }
    /**
     * sets a snapshot listener, so that whenever a record is added or deleted, it will
     * automatically update the total scores and the list of scanned player codes
     */

    public void autoUpdate(){
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                codes.clear();
                totalScore = 0;
                for (QueryDocumentSnapshot document : value){

                    ArrayList<String> s = new ArrayList<String>();
                    String name = document.get("Name", String.class);
                    int score = document.get("Score", int.class);
                    Date date = document.get("Date", Date.class);
                    String hashCode = document.get("HashCode", String.class);
                    String picture = document.get("Picture", String.class);
                    if(((ArrayList<String>) document.get("Comments")) == null){
                        PlayerCode pc = new PlayerCode(hashCode, name, score, picture);
                        codes.add(pc);
                    } else {
                        String comments = (String) document.get("Comments");
                        PlayerCode pc = new PlayerCode(hashCode, name, score, picture, date, comments);
                        codes.add(pc);
                    }
                    totalScore += score;
                    count ++;

                    Log.d("Working", document.getId() + " is in the database");
                }
            }
        });
    }

    public void deleteComment(String comment, PlayerCode pc) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayRemove(comment))
                .addOnCompleteListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));

    }

    public void addComment(String comment, PlayerCode pc){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayUnion(comment))
                .addOnSuccessListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));
    }

    public void refreshCodes(FireStoreIntegerResults fireStoreIntegerResults){
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        collectionRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int score = document.get("Score", int.class);

                                totalScore += score;
                                count++;
                                Log.d("Working", document.getId() + " is in the database");
                            }
                            fireStoreIntegerResults.onResultGetInt(totalScore, count);
                        }
                    }
                });
    }
}