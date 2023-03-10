package com.example.pokemonqrcode;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton cameraButton;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            try {
                ScannedCode code = new ScannedCode(result);
                PlayerCode pCode = new PlayerCode(code.getHashedCode(), code.getName(),
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
        }
    });
}