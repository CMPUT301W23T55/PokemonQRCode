package com.example.pokemonqrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {
    Button homeBtn;
    SearchAdapter mySearchAdapter;
    ArrayList<Users> usersList;
    RecyclerView recView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        // find views by id
        homeBtn = findViewById(R.id.return_home);
        recView =(RecyclerView) findViewById(R.id.rec_view);
        recView.setLayoutManager(new LinearLayoutManager(this));
        usersList = new ArrayList<>();
        mySearchAdapter = new SearchAdapter(usersList);
        recView.setAdapter(mySearchAdapter);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("Users").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list) {
                                    Log.d("SearchUserActivity", String.valueOf(d.getData()));
                                    Users user = d.toObject(Users.class);
                                    Log.d("SearchUserActivity", " => " + user.getTotal_Codes());
                                    usersList.add(user);
                                }
                                mySearchAdapter.notifyDataSetChanged();
                            }
                        });



//        loadUsers();

    }

//    private void loadUsers() {
//        FirebaseFirestore.getInstance()
//                .collection("Users/Araf/QRCodes")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
//                        for (DocumentSnapshot ds:dsList){
//                            Users user = ds.toObject(Users.class);
//
//                        }
//                    }
//                });
//    }

}