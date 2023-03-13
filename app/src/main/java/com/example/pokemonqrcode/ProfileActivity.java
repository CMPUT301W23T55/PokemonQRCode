package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {


    //initialize views
    Button returnHomeBtn;

//    private List<PlayerCode> codeDataList = new ArrayList<>();
//    ArrayAdapter<PlayerCode> codeAdapter;
//    CustomList customList;
//    TextView codeName;
    TextView totalCode;
    ArrayList<PlayerCode> playerCodes = new ArrayList<>();


    private ArrayAdapter<PlayerCode> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // find views
        totalCode = findViewById(R.id.total_codes);
        returnHomeBtn = findViewById(R.id.home_btn);
        ListView codeListView = findViewById(R.id.code_list);
//        adapter = new ArrayAdapter<PlayerCode>(
//                this,
//                android.R.layout.simple_list_item_1,
//                new ArrayList<PlayerCode>()
//        );
        adapter = new PlayerCodeAdapter(this, new ArrayList<PlayerCode>());
        codeListView.setAdapter(adapter);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference docReference = db.collection("Users/Admin/QRCodes");
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

        /*
        gets the total number of qr codes
         */
        docReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    totalCode.setText("Total codes: "+Integer.toString(task.getResult().size()));
//                    Log.d("TAG",  + "");
                } else {
                    Log.d("ProfileActivity", "Error getting documents: ", task.getException());
                }
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
                HashCode=playerCodes.get(i).getHashCode();
                db.collection("Users/Admin/QRCodes")
                        .whereEqualTo(FieldPath.documentId(),HashCode)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Intent intent = new Intent(ProfileActivity.this, SelectCodeActivity.class );
                                intent.putExtra("HashCode",HashCode);
                                startActivity(intent);
                            }
                        });


            }
        });



    }

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
            TextView codeName = convertView.findViewById(R.id.itemName);
            TextView codeScore = convertView.findViewById((R.id.itemScore));
            TextView capturedDate = convertView.findViewById(R.id.itemDate);

            PlayerCode p = playerCodes.get(position);
            codeName.setText(p.getName());
            codeScore.setText(Integer.toString(p.getScore()));

            String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(p.getDate());
            capturedDate.setText((CharSequence) formattedDate);

            return convertView;

        }
    }
}