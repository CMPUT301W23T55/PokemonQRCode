package com.example.pokemonqrcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This is a class that is an activity containing all of user's code
 * @author Jawad, Carter
 * @see MainActivity,FireStoreClass,PlayerCode,SelectCodeActivity
 * @version 1.3
 */
public class ProfileActivity extends AppCompatActivity {


    //initialize views

    Button returnHomeBtn,infoBtn;

//    private Button returnHomeBtn;


    private Spinner spinner;
    private ArrayList<PlayerCode> playerCodes = new ArrayList<>();
    private TextView totalCode,userName;
    private TextView totalScoreView, totalCodeView;
    private ArrayAdapter<PlayerCode> adapterPlayerCode;

    private String firebaseUsername, email;

    private Boolean access;


    /*
    is called everytime we get back to the activity
     */

    @Override
    protected void onStart() {
        super.onStart();
        FireStoreClass f = new FireStoreClass(this.firebaseUsername);
        f.getCodesList(new FireStoreLIstResults() {
            @Override
            public void onResultGetList(ArrayList<PlayerCode> playerCodeList) {
                adapterPlayerCode.clear();
                playerCodes = playerCodeList;
                playerCodes.sort(PlayerCode.PlayerScoreComparator);
                adapterPlayerCode.addAll(playerCodes);
            }
        });

        /*
        refreshes to get latest codes and get totals,
         */


        f.refreshCodes(new FireStoreResults() {
            @Override
            public void onResultGet() {
                totalScoreView.setText(Integer.toString(f.getTotalScore()));
                totalCodeView.setText(Integer.toString(f.getTotalCount()));
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.firebaseUsername = extras.getString("key");
            //The key argument here must match that used in the other activity
            this.access = extras.getBoolean("access");
        }
        else {
            // These are set as a default for testing
            this.firebaseUsername = "Test";
            this.access = true;
        }
        setContentView(R.layout.activity_profile);
        FireStoreClass f = new FireStoreClass(this.firebaseUsername);

        // find views
        userName=findViewById(R.id.UserName);
        userName.setText(this.firebaseUsername);
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
                switch(value) {
                    case "Date":
                        adapterPlayerCode.clear();
                        playerCodes.sort(PlayerCode.PlayerDateComparator);
                        adapterPlayerCode.addAll(playerCodes);
                        break;
                    case "Score: Low -> High":
                        adapterPlayerCode.clear();
                        playerCodes.sort(PlayerCode.PlayerScoreComparator);
                        Collections.reverse(playerCodes);
                        adapterPlayerCode.addAll(playerCodes);
                        break;
                    default:
                        adapterPlayerCode.clear();
                        playerCodes.sort(PlayerCode.PlayerScoreComparator);
                        adapterPlayerCode.addAll(playerCodes);
                        break;
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
                        intent.putExtra("UserName",firebaseUsername);
                        intent.putExtra("access", access);
                        startActivity(intent);

                    }
                });
                //finish();
            }
        });
        Button infoBtn = findViewById(R.id.info_btn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder((ProfileActivity.this));
                builder
                        .setTitle("View info")
                        .setMessage("User email: "+email)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info).show();
            }
        });

        f.setEmail(firebaseUsername, new FireStoreResults() {
            @Override
            public void onResultGet() {
                email = f.getEmail();
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