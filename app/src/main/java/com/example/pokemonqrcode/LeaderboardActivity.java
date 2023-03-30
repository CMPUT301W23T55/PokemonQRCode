package com.example.pokemonqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    String firebaseUsername;
    ArrayList<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // retrieve db id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.firebaseUsername = extras.getString("key");
        }
    }
}