package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This is a class that creates and maintains an activity which shows detailed info
 * and extra functionalities for each scanned code from the list of codes.
 * @author Araf
 * @see ProfileActivity, FireStoreClass, PlayerCode
 * @version 1.3
 */
public class SelectCodeActivity extends AppCompatActivity {


    private PlayerCode plCode;
    private String HashCode, fireStoreUserName;
    private String commentsString;
    private EditText commentField;
    private FireStoreClass fireStoreClass;
    private Button del_btn;
    private Button save_com_btn;
    private Button return_btn;
    private Button view_other_btn;

    private Boolean access;
    TextView codeName;
    TextView codeImage;
    TextView codeScore;
    ImageView codePhoto;



    /**
     * Takes cares of commenting, deleting code, returning to previous activites and other
     * additional functionalities using buttons and edit texts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_code);
        Intent getIntent = getIntent();

        this.HashCode = getIntent().getStringExtra("HashCode");
        this.fireStoreUserName = getIntent().getStringExtra("UserName");
        this.access = getIntent().getExtras().getBoolean("access");
        Log.d("SelectCodeActivity",HashCode);
        commentField = findViewById(R.id.comments);

        if (commentField.getText()==null || commentField.getText().equals("")) {
            commentField.setText("No Comment");
        }

        Log.d("SelectCodeActivity", fireStoreUserName);

        this.fireStoreClass = new FireStoreClass(fireStoreUserName);

        //initialises the list off users who have scanned this selected code



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fireStoreClass.getSpecificCode(HashCode, new FireStorePlayerCodeResults() {
            @Override
            public void onResultGetPlayerCode(PlayerCode pCode) {
                plCode = pCode;
                codeName = findViewById(R.id.select_code_name);
                codeName.setText(pCode.getName());
                codeImage = findViewById(R.id.itemImage);
                codeImage.setText(pCode.getPicture());
                codeScore = findViewById(R.id.select_code_score);
                codeScore.setText(pCode.getScore() + " Pts");
                commentField = findViewById(R.id.comments);
                commentField.setText(pCode.getComments());
                //so it is regenerating the playerCode from the firestore which
                //doesnt have the image on it, need to work w/ someone to fix that
                codePhoto = findViewById(R.id.select_code_photo);
                codePhoto.setImageBitmap(pCode.getPhoto());

            }
        });

        return_btn = findViewById(R.id.return_to_profile_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        del_btn = findViewById(R.id.delete_btn);
        if (access) {

            del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder aDB = new AlertDialog.Builder(SelectCodeActivity.this);
                    aDB.setTitle("Delete?");
                    aDB.setMessage("Are you sure you want to delete " + plCode.getName());
                    aDB.setNegativeButton("Cancel", null);
                    aDB.setPositiveButton("OK", (dialog, which) -> {
                        fireStoreClass.deleteCode(HashCode);
                        finish();
                        Toast.makeText(SelectCodeActivity.this, "You have successfully deleted "
                                        + plCode.getName()
                                , Toast.LENGTH_SHORT).show();
                    });
                    aDB.show();
                }
            });
        } else {
            del_btn.setVisibility(View.INVISIBLE);
        }

        save_com_btn = findViewById(R.id.save_comment_btn);

        save_com_btn.setOnClickListener(new View.OnClickListener() {
            final CollectionReference docReference = db.collection("Users/"+fireStoreUserName+"/QRCodes");
            @Override
            public void onClick(View view) {
                docReference
                        .document(HashCode)
                        .update("Comments", commentField.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Working","Data Added Successfully!");
                                Toast.makeText(SelectCodeActivity.this,"Comment saved!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Working","Data not added!" + e);
                            }
                        });

            }
        });


        view_other_btn = findViewById(R.id.view_other_players_button);
        view_other_btn.setOnClickListener(v -> {
            displayOtherPlayersFragment();
        });
        fireStoreClass.getUsersScannedSameCode(HashCode, new FireStoreResults() {
            @Override
            public void onResultGet() {

            }
        });
    }

    /**
     * Displays fragment containing list of other players who have caught the same code
     */
    private void displayOtherPlayersFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("key",fireStoreClass.getUsersScannedIdenticalCode());
        OtherPlayersCaughtFragment other_caught = new OtherPlayersCaughtFragment();//.newInstance("other_caught_fragment");
        other_caught.setArguments(bundle);
        other_caught.show(fragmentManager, "other_caught_fragment");
    }
}
