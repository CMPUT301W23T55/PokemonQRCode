package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LeaderboardAdapter leaderboardAdapter;
    String username;
    ArrayList<Map<String, Object>> leaderboardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // retrieve username
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.username = extras.getString("key");
        }

        Button homeBtn = findViewById(R.id.return_home);
        // https://developer.android.com/develop/ui/views/components/spinner#java
        Spinner sortSpinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(adapter);
        // recycler view
        leaderboardData = new ArrayList<>();
        RecyclerView leaderboardRecycler = (RecyclerView) findViewById(R.id.rec_view);
        leaderboardRecycler.setLayoutManager(new LinearLayoutManager(this));
        leaderboardAdapter = new LeaderboardAdapter(leaderboardData,this);
        leaderboardRecycler.setAdapter(leaderboardAdapter);

        // set LeaderboardData sort style (default: Highest)
        sortSpinner.setOnItemSelectedListener(this);
        // return to MainActivity
        homeBtn.setOnClickListener(view -> finish());
    }

    private void setStyle(String SortStyle) {
        leaderboardData.clear();
        // set leaderboardData
        FirebaseFirestore Database = FirebaseFirestore.getInstance();
        CollectionReference UsersRef = Database.collection("Users/");
        UsersRef.orderBy(SortStyle, Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                Log.d("Working", document.getId() + " => " + data);
                                // append user instance
                                leaderboardData.add(data);
                            }
                            leaderboardAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("Err", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String text = adapterView.getItemAtPosition(pos).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        // switch sort styles
        switch(text) {
            case "Total Score":
                setStyle("Total_Score");
                break;
            case "Total Codes":
                setStyle("Total_Codes");
                break;
            default:
                setStyle("Highest");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}