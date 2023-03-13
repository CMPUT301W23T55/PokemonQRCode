package com.example.pokemonqrcode;



import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;


import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.os.SystemClock;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Pair;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CodeFoundFragment.CodeFoundDialogListener {

    FloatingActionButton cameraButton;
    Bitmap currentImage;
    Button profileButton;


    String currentLocationSetting; //yes or no
    List<Address> currentLocation = new ArrayList<Address>();
    FusedLocationProviderClient fusedLocationProviderClient;

    FireStoreClass f;



    @Override
    public void onDataPass(Bitmap bitmap, String setting) {
        currentImage = bitmap;
        currentLocationSetting = setting;
    }
    private void getCurrentLocation() {

        if(currentLocationSetting.equals("yes")) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null){
                                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                    try {
                                        currentLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

            }

        }
        /*
        else{
            currentLocation.set((Address)0, 0, 0);
        }

         */
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Globals.username == null){

        } else {
            this.f = new FireStoreClass(Globals.username);
            this.f.autoUpdate();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("valid", MODE_PRIVATE);
        String remember = preferences.getString("remember", "");

        if (remember.equals("trueS")){
            SharedPreferences preferences1 = getSharedPreferences("name", MODE_PRIVATE);
            Globals.username = preferences1.getString("username", "");
        } else {
            Intent newIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(newIntent);
        }

        profileButton = findViewById(R.id.profile_btn);
        cameraButton = findViewById(R.id.open_camera_button);
        cameraButton.setOnClickListener(v->
        {
            scanCode();
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProfile();
            }
        });

        Button btn1 = findViewById(R.id.location_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, Globals.username, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    viewProfile shows all scanned qr codes of the user in a new Activity
     */
    private void viewProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
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



            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            try {
                ScannedCode code = null;
                code = new ScannedCode(result);

                PlayerCode pCode = new PlayerCode(code.getHashAsString(), code.getName(),
                                    code.getScore(), code.getPicture());
                builder.setMessage(pCode.getPicture());
                //builder.setMessage(pCode.getName());
                String test = "a";
                getCurrentLocation();
                for(Address address : currentLocation) {
                    test.join(address.toString());

                }
                Log.d("please+++++++++++++++++++++++++++++++++", test);
                builder.setMessage(test);
                builder.setNegativeButton("Don't Collect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                builder.setPositiveButton("Collect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Add the player code to the database in here
                        dialog.dismiss();
                    }
                }).show();
                // e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }


        }
    });
}