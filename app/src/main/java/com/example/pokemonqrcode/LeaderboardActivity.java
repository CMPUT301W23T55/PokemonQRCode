package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    String Username;
    ArrayList<Map<String, Object>> LeaderboardData = new ArrayList<>();
    int Limit = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        // buttons
        Button HomeButton = findViewById(R.id.return_home);

        // retrieve db id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.Username = extras.getString("key");
        }

        // set LeaderboardArray
        FirebaseFirestore Database = FirebaseFirestore.getInstance();
        CollectionReference UsersRef = Database.collection("Users/");
        UsersRef.orderBy("Highest", Query.Direction.DESCENDING).limit(Limit)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                Log.d("Working", document.getId() + " => " + data);
                                // append user instance
                                appendData(data);
                            }
                        } else {
                            Log.d("Err", "Error getting documents: ", task.getException());
                        }
                    }
                });

        // return to MainActivity
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void appendData(Map<String, Object> data) {
        LeaderboardData.add(data);
    }
}