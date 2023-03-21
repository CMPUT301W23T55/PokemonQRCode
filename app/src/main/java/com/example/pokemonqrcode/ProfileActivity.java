package com.example.pokemonqrcode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is a class that is an activity containing all of user's code
 * @author jawad
 * @see MainActivity,FireStoreClass,PlayerCode,SelectCodeActivity
 * @version 1.3
 */
public class ProfileActivity extends AppCompatActivity {


    //initialize views
    Button returnHomeBtn;

    Spinner spinner;
    ArrayList<PlayerCode> playerCodes = new ArrayList<>();
    TextView totalCode,userName;
    TextView totalScoreView, totalCodeView;
    private ArrayAdapter<PlayerCode> adapterPlayerCode;

    @Override
    protected void onStart() {
        super.onStart();
        FireStoreClass f = new FireStoreClass(Globals.username);
        f.getCodesList(new FireStoreLIstResults() {
            @Override
            public void onResultGetList(ArrayList<PlayerCode> playerCodeList) {
                adapterPlayerCode.clear();
                playerCodes = playerCodeList;
                playerCodes.sort(PlayerCode.PlayerScoreComparator);
                adapterPlayerCode.addAll(playerCodes);
            }
        });
        f.refreshCodes(new FireStoreIntegerResults() {
            @Override
            public void onResultGetInt(int result, int count) {
                totalScoreView.setText(Integer.toString(result));
                totalCodeView.setText(Integer.toString(count));

            }
        });

    }

    /**
     *
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FireStoreClass f = new FireStoreClass(Globals.username);
        // find views
        userName=findViewById(R.id.UserName);
        userName.setText(Globals.username);
        totalCode = findViewById(R.id.total_codes);
        returnHomeBtn = findViewById(R.id.home_btn);
        totalScoreView = findViewById(R.id.total_score_value);
        totalCodeView = findViewById(R.id.total_codes_value);

        /*
        Initialize list view, adapter and set adapter
         */
        ListView codeListView = findViewById(R.id.code_list);
        adapterPlayerCode = new PlayerCodeAdapter(this, new ArrayList<PlayerCode>());
        codeListView.setAdapter(adapterPlayerCode);


        //sets the sorting preference
        spinner = findViewById(R.id.sorting_spinner);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.sort_by, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) adapterView.getItemAtPosition(i);
                if(value.equals("Score Ascending")) {
                    adapterPlayerCode.clear();
                    playerCodes.sort(PlayerCode.PlayerScoreComparator);
                    adapterPlayerCode.addAll(playerCodes);
                }
                if(value.equals("Score Descending")) {
                    adapterPlayerCode.clear();
                    playerCodes.sort(PlayerCode.PlayerScoreComparator);
                    Collections.reverse(playerCodes);
                    adapterPlayerCode.addAll(playerCodes);
                }
                if(value.equals("Date")) {
                    adapterPlayerCode.clear();
                    playerCodes.sort(PlayerCode.PlayerDateComparator);
                    adapterPlayerCode.addAll(playerCodes);
                    Log.d("----------------------------", value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        codeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private String HashCode;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashCode = playerCodes.get(i).getHashCode();
                f.getSpecificCode(HashCode, new FireStorePlayerCodeResults() {
                    @Override
                    public void onResultGetPlayerCode(PlayerCode pCode) {
                        Intent intent = new Intent(ProfileActivity.this, SelectCodeActivity.class );
                        intent.putExtra("HashCode",HashCode);
                        startActivity(intent);

                    }
                });
                //finish();
            }
        });




    }

    /**
     * This is an adapter class responsible for rendering objects into listview
     * @author jawad
     * @version 1.3
     */
    class PlayerCodeAdapter extends ArrayAdapter<PlayerCode> {

        ArrayList<PlayerCode> playerCodes;
        PlayerCodeAdapter(Context context, ArrayList<PlayerCode> playerCodes) {
            super(context,0,playerCodes);
            this.playerCodes = playerCodes;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.content,parent,false);
            }

            /*
            Initialize the views
             */
            TextView codeName = convertView.findViewById(R.id.itemName);
            TextView codeScore = convertView.findViewById((R.id.itemScore));
            TextView capturedDate = convertView.findViewById(R.id.itemDate);
            TextView codeImage = convertView.findViewById(R.id.itemImage);

            PlayerCode p = playerCodes.get(position);
            codeName.setText(p.getName());
            codeScore.setText(Integer.toString(p.getScore()));
            codeImage.setText(p.getPicture());

            String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(p.getDate());
            capturedDate.setText((CharSequence) formattedDate);

            return convertView;

        }
    }
}

/*
                db.collection("Users/"+Globals.username+"/QRCodes")
                        .whereEqualTo(FieldPath.documentId(),HashCode)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
@Override
public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Intent intent = new Intent(ProfileActivity.this, SelectCodeActivity.class );
        intent.putExtra("HashCode",HashCode);
        startActivity(intent);
        }
        });



        final CollectionReference docReference = db.collection("Users/"+Globals.username+"/QRCodes");

        docReference.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                                    PlayerCode plCode = document.toObject(PlayerCode.class);
                                    playerCodes.add(plCode);
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                                }
                                adapter.clear();
                                adapter.addAll(playerCodes);
                            }
                        });
 */