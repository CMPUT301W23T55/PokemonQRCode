package com.example.pokemonqrcode;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
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

    Button submit;
    Button register;
    EditText user_name;
    EditText password;
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

        submit = findViewById(R.id.submit_button);
        register = findViewById(R.id.register_button);
        user_name = findViewById(R.id.edit_user_name_text);
        password = findViewById(R.id.edit_password_text);
        title = findViewById(R.id.app_title);

        submit.setOnClickListener(v -> {
            loginAttempt();
        });

        register.setOnClickListener(v -> {
            registerUser();
        });

    }

    /**
     * Attempts to log the user in
     */
    private void loginAttempt() {
        if(user_name.getText().toString().equals("") || password.getText().toString().equals("")) {
            return;
        }
        // If user exists in the databse then log them in
        // If they dont exist in the database then do a username/password does not exist
        Toast.makeText(getApplicationContext(), "Username/Password doesn't exist", Toast.LENGTH_SHORT).show();
    }

    /**
     * Opens dialog fragment and allows user to register
     * Adapted from https://guides.codepath.com/android/using-dialogfragment#things-to-note
     * Retrieved Mar 11, 2023
     */
    private void registerUser() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RegisterFragment reg_frag = RegisterFragment.newInstance("Register");
        reg_frag.show(fragmentManager, "register_fragment");
    }
}
