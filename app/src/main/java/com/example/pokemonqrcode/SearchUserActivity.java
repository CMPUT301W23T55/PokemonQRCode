package com.example.pokemonqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity for searching users by username
 * @see FireStoreClass,ProfileActivity
 * @author Jawad, Carter
 */
public class SearchUserActivity extends AppCompatActivity implements RecyclerViewInterface{

    /*
    Initialize views
     */
    Button homeBtn;
    SearchAdapter mySearchAdapter;
    RecyclerView recView;
    /*
    Initialize variables
     */
    private List<Users> filteredList = new ArrayList<>();
    private SearchView searchView;
    ArrayList<Users> usersList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.username = extras.getString("key");
            //The key argument here must match that used in the other activity
        }
        /*
        Initialize the views
         */
        homeBtn = findViewById(R.id.return_home);
        recView = (RecyclerView) findViewById(R.id.rec_view);
        recView.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
        usersList = new ArrayList<>();
        mySearchAdapter = new SearchAdapter(usersList, this);
        recView.setAdapter(mySearchAdapter);

        FireStoreClass f = new FireStoreClass(username);
        f.getSearchList(new FireStoreResults() {
            @Override
            public void onResultGet() {
                usersList.clear();
                usersList = f.getUsersArrayList();
                mySearchAdapter.notifyDataSetChanged();
                filterList("");
            }
        });
//        Toast.makeText(this, Integer.toString(usersList.size()), Toast.LENGTH_SHORT).show();


        // find views by id
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        /*
        To query based on username
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        /*
        Returns to MainActivity/Home screen
         */
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /*
    The main logic to filter users based on keywords entered for searching
     */
    private void filterList(String text) {
        filteredList.clear();
        for (Users user : usersList) {
            if ((user.getUsername()).toLowerCase().contains((text.toLowerCase()))) {
                filteredList.add(user);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();

        } else {
            mySearchAdapter.setFilteredList(filteredList);
        }
    }


    @Override
    public void onItemClick(int pos) {
        String username = filteredList.get(pos).getUsername();
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SearchUserActivity.this, ProfileActivity.class);
        intent.putExtra("key",username);
        intent.putExtra("access",false);
        startActivity(intent);
    }
}