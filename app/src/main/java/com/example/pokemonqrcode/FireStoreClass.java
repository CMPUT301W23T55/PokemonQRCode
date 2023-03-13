package com.example.pokemonqrcode;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    private PlayerCode pCode;

    //needs username as that is the key to getting data from database

    /**
     * Used when they log back into an existing account, or when device auto-identifies user
     * @param userName unique identifier
     */
    public FireStoreClass(@NonNull String userName){
        this.userName = userName;
    }


    /**
     * This method adds a PLayercode to the database
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
     * @param hashCode that should be deleted from the database
     */
    public void deleteCode(String hashCode){
        db = FirebaseFirestore.getInstance();
        CollectionReference innerCollectionRef = db.collection("Users/"+userName+"/QRCodes");
        innerCollectionRef
                .document(hashCode)
                .delete()
                .addOnSuccessListener(unused -> Log.d("Working", "Document successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));
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
//                    if(((ArrayList<String>) document.get("Comments")) == null){
//                        PlayerCode pc = new PlayerCode(hashCode, name, score, picture);
//                        codes.add(pc);
//                    } else {
//                        String comments = (String) document.get("Comments");
//                        PlayerCode pc = new PlayerCode(hashCode, name, score, picture, date, comments);
//                        codes.add(pc);
//                    }
                    totalScore += score;
                    count ++;

                    Log.d("Working", document.getId() + " is in the database");
                }
            }
        });
    }

    /**
     * this method will delete a selected comment from the PlayerCode in the database
     * @param comment the comment that will be removed
     * @param pc the pc that the comment is associated with
     */
    public void deleteComment(String comment, PlayerCode pc) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayRemove(comment))
                .addOnCompleteListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));

    }

    /**
     * this method will add a comment to a playercode
     * @param comment the comment that should be added
     * @param pc the playercode that the comment is associated with
     */
    public void addComment(String comment, PlayerCode pc){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayUnion(comment))
                .addOnSuccessListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));
    }

    /**
     * This function will refresh the total score and total count of the scanned codes associated with the user
     * @param fireStoreIntegerResults this waits until the database query runs and then gets the result(total score, and #codes)
     */
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

    /**
     * This method will query the database until it finds a record associated with the passed hashcode
     * @param hashcode to find the correct Playercode in the database
     * @param fireStorePlayerCodeResults this waits until the database query runs and then gets the result(Player code)
     */
    public void getSpecificCode(String hashcode, FireStorePlayerCodeResults fireStorePlayerCodeResults){
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {

                            String docHashCode= (String) document.get("HashCode");
//                            Log.d("SelectCodeActivity", "Intent "+Hashcode);
//                            Log.d("SelectCodeActivity", "Code "+docHashCode);
                            if (docHashCode.equals(hashcode)) {
                                pCode = document.toObject(PlayerCode.class);
//                                if (commentField.getText().equals(commentsString) == false) {
//                                    fireStoreClass.deleteComment(Hashcode);
//                                    fireStoreClass.addComment(commentField.getText(), Hashcode);

//                                }

                            }
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                        }
                        fireStorePlayerCodeResults.onResultGetPlayerCode(pCode);
                    }
                });
    }
}