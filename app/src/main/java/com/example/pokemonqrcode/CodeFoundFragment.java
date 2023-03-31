package com.example.pokemonqrcode;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/**
 * This is the fragment that appears after the camera detects a QR code from the MainActivity
 * Here, the user can take a picture of the code with their camera, and decide whether to tag
 * the code with a geolocation
 */
public class CodeFoundFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //View view = LayoutInflater.from(getContext()).inflate(R.layout.code_found_dialog, null);
    //ImageView image = view.findViewById(R.id.found_user_image);
    /*
    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 100) {
                        Log.d("test", "hello camera");
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(bitmap);

                    }
                }
            });

     */
    interface CodeFoundDialogListener {
        void onDataPass(Bitmap bitmap, String setting);
        void onDataPass(Bitmap bitmap);
        void onDataPass(String setting);
    }
    private CodeFoundDialogListener listener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (CodeFoundDialogListener) context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.code_found_dialog, null);
        TextView titleText = view.findViewById(R.id.found_title);
        ImageView image = view.findViewById(R.id.found_user_image);
        Button cameraButton = view.findViewById(R.id.found_open_camera);
        Spinner locationSpinner = view.findViewById(R.id.edit_text_location_text);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(this);


        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        Log.d("test", "hello camera");
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(bitmap);
                        listener.onDataPass(bitmap);



                    }
                });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLaunch.launch(intent);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                //.setTitle("Code found!")
                .setPositiveButton("Capture", (dialog, which) -> {
                        listener.onDataPass((locationSpinner.getSelectedItem().toString()));

                })
                .create();

    }
    public void passData(Bitmap bitmap, String setting) {
        listener.onDataPass(bitmap, setting);
    }
    public void passData(Bitmap bitmap) {
        listener.onDataPass(bitmap);
    }
    public void passData(String setting) { listener.onDataPass(setting);}


}
