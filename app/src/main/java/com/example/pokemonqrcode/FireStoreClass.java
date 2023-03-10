package com.example.pokemonqrcode;

import android.util.Log;
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

    private ArrayList<PlayerCode> codes = new ArrayList<PlayerCode>();

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

        String name = pC.getName();
        int score = pC.getScore();
        Date date = pC.getDate();
        String hashcode = pC.getHashCode();
        String picture = pC.getPicture();
        ArrayList<String> comments = pC.getComments();

        data.put("Name",name);
        data.put("Score",score);
        data.put("Date", date);
        data.put("HashCode",hashcode);
        data.put("Picture",picture);
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
        CollectionReference innerCollectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        innerCollectionRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){

                            ArrayList<String> s = new ArrayList<String>();
                            String name = document.get("Name", String.class);
                            int score = (int) document.get("Score");
                            Date date = document.get("Date", Date.class);
                            String hashCode = document.get("HashCode", String.class);
                            String picture = document.get("Picture", String.class);
                            ArrayList<String> comments = (ArrayList<String>) document.get("Comments");
                            //PlayerCode pc = new PlayerCode(name, 0, date, hashCode, picture);
                            //codes.add(pc);

                            Log.d("Working", document.getId() + " => " + document.get("Score"));
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
                .document(String.valueOf(pC.getHashCode()))
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