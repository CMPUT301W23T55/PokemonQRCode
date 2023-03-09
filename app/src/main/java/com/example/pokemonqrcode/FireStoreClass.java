package com.example.pokemonqrcode;

import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Create an instance of the Firestore database
 */
//for all query operations, https://firebase.google.com/docs/firestore/
//was used as a reference
public class FireStoreClass {

    private final String userName;
    private FirebaseFirestore db;

    private ArrayList<PlayerCode> codes;

    //needs username as that is the key to getting data from database
    public FireStoreClass(String userName){
        this.userName = userName;
    }


    /**
     *
     * @param pC needs a player code to add it to the database
     */
    public void addAQRCode(@NonNull PlayerCode pC){

        db = FirebaseFirestore.getInstance();

        HashMap<String, Object> data = new HashMap<>();

        String name = pC.getPlayerCodeName();
        int score = pC.getPlayerCodeScore();
        Date date = pC.getPlayerCodeDate();
        int hashcode = pC.getPlayerCodeHashCode();
        String picture = pC.getPlayerCodePicture();
        Pair<Integer, Integer> location = pC.getPlayerCodeLocation();
        ArrayList<String> comments = pC.getPlayerCodeComments();

        data.put("Name",name);
        data.put("Score",score);
        data.put("Date", date);
        data.put("HashCode",hashcode);
        data.put("Picture",picture);
        data.put("Location",location);
        data.put("Comments",comments);

        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(String.valueOf(hashcode))
                .set(data)
                .addOnSuccessListener(unused -> Log.d("Working", "Data added successfully"))
                .addOnFailureListener(e -> Log.d("Working", "data not added" + e));
    }

    /**
     * Retrieves all the PlayerCodes from the database and stores them into the codes list
     */
    public void RetrievePlayerCodeList(){

        db = FirebaseFirestore.getInstance();
        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            PlayerCode pc = document.get("CodeData", PlayerCode.class);
                            Log.d("Working", document.getId() + " => " + document.get("CodeData"));
                        }
                    } else {
                        Log.d("Working", "Query Unsuccessful");
                    }
                });
    }

    public void deleteCode(PlayerCode pC){

        db = FirebaseFirestore.getInstance();
        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(String.valueOf(pC.getPlayerCodeHashCode()))
                .delete()
                .addOnSuccessListener(unused -> Log.d("Working", "Document successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error deleting document", e));
    }

    /**
     *
     * @return an array of PlayerCodes from the firestore database
     */
    public ArrayList<PlayerCode> getPlayerCodes(){
        return this.codes;
    }


}