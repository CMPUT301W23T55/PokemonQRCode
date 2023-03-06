package com.example.pokemonqrcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CodeFoundFragment extends DialogFragment  {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.code_found_dialog, null);
        TextView titleText = view.findViewById(R.id.found_title);
        TextView imgText = view.findViewById(R.id.found_picture_text);
        ImageButton imgButton = view.findViewById(R.id.found_image_button);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                //.setTitle("Code found!")
                .setPositiveButton("Capture", (dialog, which) -> {

                })
                .create();


    }
}
