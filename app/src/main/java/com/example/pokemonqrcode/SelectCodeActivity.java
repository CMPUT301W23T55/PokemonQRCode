package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    private String docHashCode;
    private String commentsString;
    private EditText commentField;
    private FireStoreClass fireStoreClass;
    private Button del_btn;
    private Button save_com_btn;
    private Button return_btn;
    TextView codeName;
    TextView codeImage;
    TextView codeScore;



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
        String Hashcode = getIntent.getStringExtra("HashCode");
        Log.d("SelectCodeActivity",Hashcode);
        commentField = findViewById(R.id.comments);
        if (commentField.getText()==null || commentField.getText().equals("")) {
            commentField.setText("No Comment");
        }

        FireStoreClass fireStoreClass;
        Log.d("SelectCodeActivity", Globals.username);

        fireStoreClass = new FireStoreClass(Globals.username);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference docReference = db.collection("Users/"+Globals.username+"/QRCodes");
        fireStoreClass.getSpecificCode(Hashcode, new FireStorePlayerCodeResults() {
            @Override
            public void onResultGetPlayerCode(PlayerCode pCode) {
                plCode = pCode;
                codeName = findViewById(R.id.select_code_name);
                codeName.setText(pCode.getName());
                codeImage = findViewById(R.id.itemImage);
                codeImage.setText(pCode.getPicture());
                codeScore = findViewById(R.id.select_code_score);
                codeScore.setText(Integer.toString(pCode.getScore()) + " Pts");
                commentField = findViewById(R.id.comments);
                commentField.setText(pCode.getComments());

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

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aDB = new AlertDialog.Builder(SelectCodeActivity.this);
                aDB.setTitle("Delete?");
                aDB.setMessage("Are you sure you want to delete " + plCode.getName());
                aDB.setNegativeButton("Cancel", null);
                aDB.setPositiveButton("OK", (dialog, which) -> {
                    fireStoreClass.deleteCode(Hashcode);
                    finish();
                });
                aDB.show();
            }
        });

        save_com_btn = findViewById(R.id.save_comment_btn);

        save_com_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docReference
                        .document(Hashcode)
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
                                Log.d("Working","Data not added!" + e.toString());
                            }
                        });

            }
        });
    };
}

