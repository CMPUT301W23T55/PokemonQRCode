package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectCodeActivity extends AppCompatActivity {

    private PlayerCode plCode;
    private String docHashCode;
    private String commentsString;
    private EditText commentField;
    private FireStoreClass fireStoreClass;
    private String userName;
    private Button del_btn;
    private Button save_com_btn;
    private Button return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_code);
        Intent getIntent = getIntent();
        String Hashcode = getIntent.getStringExtra("HashCode");
        Log.d("SelectCodeActivity",Hashcode);
        commentField = findViewById(R.id.comments);

        userName = "Admin";
        fireStoreClass = new FireStoreClass(userName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference docReference = db.collection("Users/"+userName+"/QRCodes");
        docReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {

                            docHashCode= (String) document.get("HashCode");
                            Log.d("SelectCodeActivity", "Intent "+Hashcode);
                            Log.d("SelectCodeActivity", "Code "+docHashCode);
                            if (docHashCode.equals(Hashcode)) {
                                PlayerCode plCode = document.toObject(PlayerCode.class);
                                commentsString= (String) document.get("Comments");
                                Log.d("SelectCodeActivity", "Comment "+commentsString);
                                Log.d("SelectCodeActivity","editText"+commentField);
                                Log.d("SelectCodeActivity","PlCODE"+plCode.getHashCode());
                                commentField.setText(commentsString);
//                                if (commentField.getText().equals(commentsString) == false) {
//                                    fireStoreClass.deleteComment(Hashcode);
//                                    fireStoreClass.addComment(commentField.getText(), Hashcode);

//                                }

                            }
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                        }
                    }
                });

        return_btn = findViewById(R.id.return_to_profile_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        del_btn = findViewById(R.id.delete_btn);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docReference
                        .whereEqualTo(FieldPath.documentId(),Hashcode)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();
                                    docReference
                                            .document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("Working","Data Successfully Deleted!");
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Working","Data Not Deleted!");
                                                }
                                            });
                                }
                                else{
                                    Log.d("Failed","Error!");
                                }
                            }
                        });

            }
        });



//        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            private String city;
//            private String province;
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                city=cityDataList.get(i).getCityName();
//                province=cityDataList.get(i).getProvinceName();
//                db.collection("Cities")
//                        .whereEqualTo(FieldPath.documentId(),city)
//                        .whereEqualTo("Province Name", province)
//                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful() && !task.getResult().isEmpty()){
//                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                                    String documentID = documentSnapshot.getId();
//                                    db.collection("Cities")
//                                            .document(documentID)
//                                            .delete()
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    Log.d("Working","Data Successfully Deleted!");
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("Working","Data Not Deleted!");
//                                                }
//                                            });
//                                }
//                                else{
//                                    Log.d("Failed","Error!");
//                                }
//                            }
//                        });
//
//                return false;
//            }
//        });

        save_com_btn = findViewById(R.id.save_comment_btn);

        save_com_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docReference
                        .document(Hashcode)
                        .update("Comments", commentField.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Working","Data Added Successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Working","Data not added!" + e.toString());
                            }
                        });

            }
        });
    };
}