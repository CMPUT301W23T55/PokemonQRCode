package com.example.pokemonqrcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
// Adapted from https://guides.codepath.com/android/using-dialogfragment#things-to-note
// Accessed Mar 11 2023
// cc-wiki

/**
 * Dialog fragment that allows a new user to register for the app
 */
public class RegisterFragment extends DialogFragment {

    private EditText editUserName;
    private EditText editPassword;
    private EditText editEmail;
    private Button submit;
    private Button cancel;

    /**
     * Used to create a new registerfragment object for use in an activity
     * @param title
     * @return RegisterFragment object
     */
    public static RegisterFragment newInstance(String title) {
        RegisterFragment reg_frag = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        reg_frag.setArguments(args);
        return reg_frag;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container);
    }

    /**
     * When the view of the fragment is created instantiates all of the necessary fields
     * and sets the title for the fragment
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editUserName = view.findViewById(R.id.new_username_edit_text);
        editPassword = view.findViewById(R.id.new_password_edit_text);
        editEmail = view.findViewById(R.id.new_email_edit_text);
        submit = view.findViewById(R.id.submit_register);
        cancel = view.findViewById(R.id.cancel_register);
        String title = getArguments().getString("title", "Register");
        getDialog().setTitle(title);

        submit.setOnClickListener(v -> {
            attemptRegistration();
        });

        cancel.setOnClickListener(v -> {
            this.dismiss();
        });
    }

    public void attemptRegistration() {
        FireStoreAuthentication authentication = new FireStoreAuthentication();
        String newEmail = editEmail.getText().toString();
        String newPass = editPassword.getText().toString();
        String newUser = editUserName.getText().toString();
        if(newEmail.equals("") || newUser.equals("") || newPass.equals("")) {
            Toast.makeText(getActivity(), "Ensure all fields have text", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!authentication.validUsername(newUser)) {
            authentication.createUser(newUser, newPass, newEmail);
            this.dismiss();
            return;
        }
        else {
            Toast.makeText(getActivity(), "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
