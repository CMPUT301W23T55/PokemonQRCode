package com.example.pokemonqrcode;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Pair;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CodeFoundFragment.CodeFoundDialogListener {

    FloatingActionButton cameraButton;

    public PlayerCode generateCode(ScannedCode code, Bitmap image, Location location) {
        return new PlayerCode(code, image, location);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cameraButton = findViewById(R.id.open_camera_button);
        cameraButton.setOnClickListener(v->
        {
            scanCode();
        });

    }

    /*
    https://www.cambotutorial.com/article/10-minutes-build-bar-code-and-qr-code-scanner-in-android-app
    Retrieved February 22, 2023
    Licensed under MIT License
    https://choosealicense.com/licenses/mit/
     */

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to turn on flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        //we can use getContents to get the reading of the code to calculate the score
        if(result.getContents() !=null) {

            //i dont think we need this class, do it with normal alert dialog
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.CAMERA
                }, 100);
            }

            new CodeFoundFragment().show(getSupportFragmentManager(), "Code Found");


            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            try {
                ScannedCode code = new ScannedCode(result);
                PlayerCode pCode = new PlayerCode(code.getHashAsString(), code.getName(),
                                    code.getScore(), code.getPicture());
                builder.setMessage(pCode.getName());
                /*
                builder.setNegativeButton("Don't Collect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();*/
                builder.setPositiveButton("Collect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Add the player code to the database in here
                        dialog.dismiss();
                    }
                }).show();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

             */
        }
    });
}