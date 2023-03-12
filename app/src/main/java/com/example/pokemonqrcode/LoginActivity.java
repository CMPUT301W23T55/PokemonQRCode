package com.example.pokemonqrcode;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Class used to log a user into the app or to register a new user for using the app
 */
public class LoginActivity extends AppCompatActivity {

    Button submit_btn;
    Button register_btn;
    EditText user_name_etxt;
    EditText password_etxt;
    TextView title;

    /**
     * creates a view of the activity and instantiates all of the necessary fields
     * sets up the onClickListeners for buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        submit_btn = findViewById(R.id.submit_button);
        register_btn = findViewById(R.id.register_button);
        user_name_etxt = findViewById(R.id.edit_user_name_text);
        password_etxt = findViewById(R.id.edit_password_text);
        title = findViewById(R.id.app_title);

        submit_btn.setOnClickListener(v -> {
            loginAttempt();
        });

        register_btn.setOnClickListener(v -> {
            registerUser();
        });

    }

    /**
     * Attempts to log the user in
     */
    private void loginAttempt() {
        FireStoreAuthentication authentication = new FireStoreAuthentication();
        String username = user_name_etxt.getText().toString();
        String pass_wrd = password_etxt.getText().toString();
        Context context = getApplicationContext();

        if(username.equals("") || pass_wrd.equals("")) {
            Toast.makeText(context, "Ensure all fields have tex", Toast.LENGTH_SHORT).show();
        } else {
            authentication.checkPassword(username, pass_wrd, new FireStoreResults() {
                @Override
                public void onResultGet(boolean result) {
                    if (!result) {
                        Toast.makeText(context, "Username/Password doesn't exist", Toast.LENGTH_SHORT).show();
                    } else {
                        //https://www.youtube.com/watch?v=8pTcATGRDGM
                        Globals.username = username;

                        SharedPreferences pref = getSharedPreferences("name", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username",username);
                        editor.apply();

                        SharedPreferences preferences = getSharedPreferences("valid", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = preferences.edit();
                        editor1.putString("remember","true");
                        editor1.apply();

                        finish();
                    }
                }
            });
        }
    }

    /**
     * Opens dialog fragment and allows user to register
     * Adapted from https://guides.codepath.com/android/using-dialogfragment#things-to-note
     * Retrieved Mar 11, 2023
     * cc-wiki
     */
    private void registerUser() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RegisterFragment reg_frag = RegisterFragment.newInstance("Register");
        reg_frag.show(fragmentManager, "register_fragment");
    }
}
