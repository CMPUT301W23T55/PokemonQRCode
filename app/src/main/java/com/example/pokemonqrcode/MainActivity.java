package com.example.pokemonqrcode;

import static com.example.pokemonqrcode.FireStoreClass.totalSum;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

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

        Pair<Integer, Integer> location = new Pair<>(1,1);
        FireStoreClass f = new FireStoreClass("Kyle");
        f.addAQRCode("Magnemite", 200, location, 234567);
        f.addAQRCode("Bulbasaur", 340, location, 987654);
        f.addAQRCode("charizard", 470, location, 101010);
        f.totalScore();
        Toast.makeText(getApplicationContext(), Long.toString(totalSum), Toast.LENGTH_SHORT).show();

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
                //builder.setMessage(result.getContents());
                builder.setMessage(code.getHashAsString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    });
}