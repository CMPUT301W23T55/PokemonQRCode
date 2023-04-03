package com.example.pokemonqrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
        Spinner sortSpinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortSpinner.setAdapter(adapter);
        // recycler view
        leaderboardData = new ArrayList<>();
        RecyclerView leaderboardRecycler = findViewById(R.id.rec_view_leaderboard);
        //leaderboardRecycler = (RecyclerView) findViewById(R.id.rec_view);
        leaderboardRecycler.setLayoutManager(new LinearLayoutManager(this));
        leaderboardAdapter = new LeaderboardAdapter(leaderboardData,this);
        leaderboardRecycler.setAdapter(leaderboardAdapter);

        // set LeaderboardData sort style (default: Highest)
        sortSpinner.setOnItemSelectedListener(this);
        // return to MainActivity
        homeBtn.setOnClickListener(view -> finish());
    }

    private void setStyle(String SortStyle) {
        FireStoreClass f = new FireStoreClass(Globals.username);

        f.getLeaderboards(SortStyle, () -> {
            leaderboardData.clear();
            leaderboardData.addAll(f.getLeaderboardData());
            handleTies(SortStyle);
            leaderboardAdapter.notifyDataSetChanged();
        });
    }


    private void handleTies(String SortStyle) {
        // set rank for first elem
        int prevRank = 1, prevScore = ((Long) leaderboardData.get(0).get(SortStyle)).intValue();
        leaderboardData.get(0).put("rank", prevRank);
        // iter through the remaining list
        for (int i = 1; i<leaderboardData.size(); i++) {
            Map<String, Object> usr = leaderboardData.get(i);

            if (((Long) usr.get(SortStyle)).intValue() == prevScore) {
                usr.put("rank", prevRank);
            } else {
                prevRank++;
                usr.put("rank", prevRank);
                prevScore = ((Long) usr.get(SortStyle)).intValue();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String text = adapterView.getItemAtPosition(pos).toString();
        leaderboardAdapter.setDisplayStyle(text);
        // switch sort styles
        switch(text) {
            case "Total Score":
                setStyle("Total_Score");
                break;
            case "Total Codes":
                setStyle("Total_Codes");
                break;
            case "Highest Score":
                setStyle("Highest");
                break;
        }
        //leaderboardRecycler.scrollToPosition(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}