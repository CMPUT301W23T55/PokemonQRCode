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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class SeeCommentsActivity extends AppCompatActivity {

    private ArrayList<String> comments;
    private Button return_btn;
    private ArrayAdapter<String> adapterComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_comments);
        Intent getIntent = getIntent();

        Bundle args = getIntent.getBundleExtra("BUNDLE");
        comments = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        Log.d("SeeCommentsActivity", String.valueOf(comments));

        return_btn = findViewById(R.id.return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ListView commentsListView = findViewById(R.id.comment_list);
        adapterComments = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);
        commentsListView.setAdapter(adapterComments);

    }
}

//class CommentsAdapter extends ArrayAdapter<PlayerCode> {
//
//    ArrayList<String> comments;
//    CommentsAdapter(Context context, ArrayList<String> comments) {
//        super(context,0,comments);
//        this.comments = comments;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content,parent,false);
//        }
//
//            /*
//            Initialize the views
//             */
//        TextView codeName = convertView.findViewById(R.id.itemName);
//        TextView codeScore = convertView.findViewById((R.id.itemScore));
//        TextView capturedDate = convertView.findViewById(R.id.itemDate);
//        TextView codeImage = convertView.findViewById(R.id.itemImage);
//
//        codeName.setText(p.getName());
//        codeScore.setText(Integer.toString(p.getScore()));
//        codeImage.setText(p.getPicture());
//
//        return convertView;
//
//    }
//}