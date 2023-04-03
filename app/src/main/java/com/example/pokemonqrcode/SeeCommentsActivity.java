package com.example.pokemonqrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

import java.text.DateFormat;
import java.util.ArrayList;




public class SeeCommentsActivity extends AppCompatActivity {

    private ArrayList<String> comments = new ArrayList<>();;
    private Button return_btn;
    private ArrayAdapter<String> adapterComments;

    String authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_comments);

        Intent getIntent = getIntent();
        this.comments.clear();
        Bundle args = getIntent.getBundleExtra("BUNDLE");
        this.comments = args.getStringArrayList("ARRAYLIST");
        String hashcode = args.getString("hashcode");
        this.authentication = args.getString("name");

        Log.d("SeeCommentsActivity", String.valueOf(comments));
        FireStoreClass f = new FireStoreClass(authentication);

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

        commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (authentication.equals(Globals.username)){
                    final AlertDialog.Builder builder = new AlertDialog.Builder((SeeCommentsActivity.this));
                    builder
                            .setTitle("Delete Comment")
                            .setMessage("Are you sure you want to delete this comment?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String comment = (String) commentsListView.getItemAtPosition(position);
                                    f.deleteComment(comment, hashcode);
                                    comments.remove(position);
                                    adapterComments.notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        });
    }
}