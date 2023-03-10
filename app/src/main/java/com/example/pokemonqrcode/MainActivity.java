package com.example.pokemonqrcode;


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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date = new Date();
        ArrayList<String> comments = new ArrayList<>();
        comments.add("hi");
        comments.add("bye");

        FireStoreClass f = new FireStoreClass("Kyle");

        PlayerCode pc = new PlayerCode("Charizard", 255, date, "123456",
                "Picture of Charizard", comments);
        PlayerCode pc1 = new PlayerCode("Blastoise", 355, date, "12783456",
                "Picture of Blastoise", comments);
        PlayerCode pc2 = new PlayerCode("Venausaur", 755, date, "1574686",
                "Picture of Venausaur", comments);
        f.addAQRCode(pc);
        f.addAQRCode(pc1);
        f.addAQRCode(pc2);
        f.RetrievePlayerCodeList();
        ArrayList<PlayerCode> codes = f.getPlayerCodes();
        PlayerCode a = codes.get(0);
        //Toast.makeText(this, a.getPlayerCodeName(), Toast.LENGTH_SHORT).show();

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