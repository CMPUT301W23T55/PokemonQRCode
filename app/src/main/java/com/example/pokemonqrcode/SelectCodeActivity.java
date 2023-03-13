package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectCodeActivity extends AppCompatActivity {

    private PlayerCode plCode;
    private String docHashCode;
    private ArrayList<String> commentsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_code);
        Intent getIntent = getIntent();
        String Hashcode = getIntent.getStringExtra("HashCode");
        Log.d("SelectCodeActivity",Hashcode);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final CollectionReference docReference = db.collection("Users/Admin/QRCodes");
//        docReference.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
//                            docHashCode=plCode.getHashCode();
//                            if (docHashCode==Hashcode) {
//                                commentsArray = plCode.getComments();
//
//                            }
////                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
//                        }
//                    }
//                });
    }
}