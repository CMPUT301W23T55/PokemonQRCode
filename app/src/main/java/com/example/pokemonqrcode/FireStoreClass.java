package com.example.pokemonqrcode;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create an instance of the Firestore database
 */
//for all query operations, https://firebase.google.com/docs/firestore/
//was used as a reference
public class FireStoreClass implements Serializable {

    final private String userName;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final ArrayList<PlayerCode> codes = new ArrayList<>();
    private final ArrayList<Map<String, Object>> leaderboardData = new ArrayList<>();

    private int totalScore, count;
    private PlayerCode pCode, highestCode;

    private final ArrayList<String> usersScannedIdenticalCode = new ArrayList<>();
    private final ArrayList<Users> usersArrayList = new ArrayList<>();

    //needs username as that is the key to getting data from database

    /**
     * Used when they log back into an existing account, or when device auto-identifies user
     * @param userName unique identifier
     */
    public FireStoreClass(@NonNull String userName){
        this.userName = userName;
    }


    /**
     * This method adds a PlayerCode to the database
     * @param pC needs a player code to add it to the database
     */
    public void addAQRCode(@NonNull PlayerCode pC){
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

        CollectionReference innerCollectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        innerCollectionRef
                .document(String.valueOf(hashcode))
                .set(data)
                .addOnSuccessListener(unused -> Log.d("Working", "Data added successfully under "+userName))
                .addOnFailureListener(e -> Log.d("Working", "error exception occurred" + e));

        // get user data, update highest if highest < pc score
        DocumentReference userRef = db.collection("Users").document(this.userName);
        userRef.get().addOnSuccessListener(ds -> {
            Users user = ds.toObject(Users.class);
            assert user != null;
            if (score > user.getHighest()) {
                // update db
                user.setHighest(score);
                userRef.update("Highest",score)

                        .addOnSuccessListener(unused -> Log.d("Working", "Data added successfully under "+userName))
                        .addOnFailureListener(e -> Log.d("Working", "error exception occurred" + e));
            }
        });
    }

    /**
     * Given a PlayerCode, this method will delete that associated record from the database
     * @param hashCode that should be deleted from the database
     */
    public void deleteCode(String hashCode){
        CollectionReference innerCollectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        innerCollectionRef
                .document(hashCode)
                .delete()
                .addOnSuccessListener(unused -> Log.d("Working", "Document successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));


        // update highest by iterating through code list
        // modified from firebase docs : https://firebase.google.com/docs/firestore/query-data/get-data#java_10
        innerCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int  Highest = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    int Score = document.get("Score", int.class);
                    if (Highest <= Score) {
                        Highest = Score;
                    }
                }
                // update db
                DocumentReference userRef = db.collection("Users").document(Globals.username);
                userRef.update("Highest", Highest);
            } else {
                Log.d("Err", "Error getting documents: ", task.getException());
            }
        });
    }

    /**
     * this method will delete a selected comment from the PlayerCode in the database
     * @param comment the comment that will be removed
     * @param pc the pc that the comment is associated with
     */
    public void deleteComment(String comment, PlayerCode pc) {
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayRemove(comment))
                .addOnCompleteListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));

    }

    /**
     * this method will add a comment to a PlayerCode
     * @param comment the comment that should be added
     * @param pc the PlayerCode that the comment is associated with
     */
    public void addComment(String comment, PlayerCode pc){
        DocumentReference docRef = db.collection("Users/" + this.userName + "/QRCodes").document(pc.getHashCode());
        docRef.update("Comments", FieldValue.arrayUnion(comment))
                .addOnSuccessListener(unused -> Log.d("Working", "Comment successfully deleted"))
                .addOnFailureListener(e -> Log.w("Working", "Error exception occurred", e));
    }

    /**
     * This function will refresh the total score and total count of the scanned codes associated with the user
     * @param fireStoreResults this waits until the database query runs and then gets the result(total score, and #codes)
     */
    public void refreshCodes(FireStoreResults fireStoreResults){
        CollectionReference collectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        collectionRef
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int score = document.get("Score", int.class);

                            totalScore += score;
                            count++;
                            Log.d("Working", document.getId() + " is in the database");
                        }
                        fireStoreResults.onResultGet();
                        setUserAttributes();
                    }
                });
    }

    /**
     * This method will query the database until it finds a record associated with the passed hashcode
     * @param hashcode to find the correct PlayerCode in the database
     * @param fireStorePlayerCodeResults this waits until the database query runs and then gets the result(Player code)
     */
    public void getSpecificCode(String hashcode, FireStorePlayerCodeResults fireStorePlayerCodeResults){
        CollectionReference collectionRef = db.collection("Users/"+this.userName+"/QRCodes");
        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {

                        String docHashCode= (String) document.get("HashCode");
                        if (docHashCode.equals(hashcode)) {
                            pCode = document.toObject(PlayerCode.class);

//                                }

                        }
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                    }
                    fireStorePlayerCodeResults.onResultGetPlayerCode(pCode);
                });
    }

    /**
     * Gets the codes associated to an account
     * @param fireStoreLIstResults An interface used to deal with firestore's asynchronous behaviour
     */
    public void getCodesList(FireStoreLIstResults fireStoreLIstResults){
        CollectionReference docReference = db.collection("Users/"+this.userName+"/QRCodes");

        docReference.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        pCode = document.toObject(PlayerCode.class);
                        codes.add(pCode);
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                    }
                    fireStoreLIstResults.onResultGetList(codes);
                });
    }

    /**
     * This method will update the users total score and total codes field in
     * the firestore database when they add or remove a code
     */
    private void setUserAttributes(){
        HashMap<String, Object> data = new HashMap<>();

        data.put("Total_Score",this.totalScore);
        data.put("Total_Codes",this.count);


        CollectionReference innerCollectionRef = db.collection("Users");
        innerCollectionRef
                .document(this.userName)
                .update(data)
                .addOnSuccessListener(unused -> Log.d("Working", "Data added successfully"))
                .addOnFailureListener(e -> Log.d("Working", "error exception occurred" + e));
    }

    /**
     * This method returns the summed score of all the scanned codes
     * @return the sum of all the scanned codes
     */
    public int getTotalScore(){
        return this.totalScore;
    }

    /**
     * This method returns the amount of scanned codes of the selected user
     * @return the number of total scanned codes
     */
    public int getTotalCount(){
        return this.count;
    }

    public ArrayList<String> getUsersScannedIdenticalCode() {
        return usersScannedIdenticalCode;
    }

    /**
     * This method creates a list of users that have scanned the same code as the user selected
     * @param tempHashcode the Hashcode of the scanned code
     * @param fireStoreResults An interface used to handle firestore's asynchronous behaviour
     */
    public void getUsersScannedSameCode(String tempHashcode, FireStoreResults fireStoreResults){
        CollectionReference docReference = db.collection("Users");

        docReference.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        String tempName = document.get("Username",String.class);

                        CollectionReference collectionReference =
                                db.collection( "Users/"+ tempName +"/QRCodes");

                        collectionReference.whereEqualTo("HashCode",tempHashcode)
                                .get().addOnCompleteListener(task -> {
                                    if (!task.getResult().isEmpty()){
                                        usersScannedIdenticalCode.add(tempName);
                                        Log.d("Working", tempName + " has been added to the list");
                                    }
                                    usersScannedIdenticalCode.remove(userName);
                                    fireStoreResults.onResultGet();
                                });
                    }
                });
    }

    /**
     * This method creates a list of every user that has an account
     * @param fireStoreResults An interface used to deal with firestore's asynchronous behaviour
     */
    public void getSearchList(FireStoreResults fireStoreResults){
        CollectionReference col = db.collection("Users");
        col.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d:list) {
                        Log.d("SearchUserActivity", String.valueOf(d.getData()));
                        Users user = d.toObject(Users.class);
                        Log.d("SearchUserActivity", " Query was successful");
                        if(!(user.getUsername().equals(userName))) {
                            usersArrayList.add(user);
                        }
                    }
                    fireStoreResults.onResultGet();
                });
    }

    /**
     * This method returns an array of all the users that have an account
     * @return the list of users
     */
    public ArrayList<Users> getUsersArrayList(){
        return this.usersArrayList;
    }

    public void getLeaderboards(String sortStyle, FireStoreResults fireStoreResults) {
        leaderboardData.clear();
        CollectionReference UsersRef = db.collection("Users");
        UsersRef.orderBy(sortStyle, Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            Log.d("Working", document.getId() + " => " + data);
                            // append user instance
                            leaderboardData.add(data);
                        }
                        fireStoreResults.onResultGet();
                    } else {
                        Log.d("Err", "Error getting documents: ", task.getException());
                    }
                });
    }
    public ArrayList<Map<String, Object>> getLeaderboardData(){
        return this.leaderboardData;
    }

    public void getHighest(FireStoreResults fireStoreResults){
        CollectionReference UsersRef = db.collection("Users/"+this.userName+"/QRCodes");
        UsersRef.orderBy("Score", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        highestCode = document.toObject(PlayerCode.class);
                    }
                    fireStoreResults.onResultGet();
                });


    }
    public PlayerCode getHighestCode(){
        return this.highestCode;
    }
}

// Query query = collectionRef.orderBy("amount", descending: true).limit(1);