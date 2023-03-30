package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class LeaderboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String Username;
    ArrayList<Map<String, Object>> LeaderboardData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

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

        // retrieve db id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.Username = extras.getString("key");
        }

        // set LeaderboardData (default: Highest)
        setStyle("Highest");

        sortSpinner.setOnItemSelectedListener(this);
        // return to MainActivity
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void appendData(Map<String, Object> data) {
        LeaderboardData.add(data);
    }

    private void setStyle(String SortStyle) {
        LeaderboardData.clear();
        // set LeaderboardArray
        FirebaseFirestore Database = FirebaseFirestore.getInstance();
        CollectionReference UsersRef = Database.collection("Users");
        UsersRef.orderBy(SortStyle, Query.Direction.DESCENDING)
                .limit(50)
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}