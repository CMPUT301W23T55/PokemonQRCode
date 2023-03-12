package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
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

    private ArrayAdapter<PlayerCode> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // find views
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
                                ArrayList<PlayerCode> playerCodes = new ArrayList<>();
                                for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                                    PlayerCode plCode = document.toObject(PlayerCode.class);
                                    playerCodes.add(plCode);
//                                    Log.d("ProfileActivity",plCode.getName() + " => " + plCode.getPicture());
                                }
                                adapter.clear();
                                adapter.addAll(playerCodes);
                            }
                        });


        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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