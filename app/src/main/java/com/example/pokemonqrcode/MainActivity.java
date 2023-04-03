package com.example.pokemonqrcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;


import java.security.NoSuchAlgorithmException;

import java.security.NoSuchAlgorithmException;



/**
 * the MainActivity of the app, contains buttons to open the camera to scan a code,
 * view the users profile, open the map, and view other players' stats
 */
public class MainActivity extends AppCompatActivity implements CodeFoundFragment.CodeFoundDialogListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private final String SAVE_LOCATION = "Yes";
    private final String IGNORE_LOCATION = "No";

    FloatingActionButton cameraButton;

    private int rank;

    private boolean isResumed;



    Bitmap currentImage;

    String currentLocationSetting; //yes or no
    Location currentLocation;
    ScanIntentResult currentScan;

    Button profileButton, leaderboardBtn, logOutBtn,findUserBtn,mapButton;

    private LocationManager locationManager;
    private LocationListener locationListener;

    FireStoreClass f;

    @Override
    public void onDataPass(Bitmap bitmap, String setting) {
        currentImage = bitmap;
        currentLocationSetting = setting;
    }
    @Override
    public void onDataPass(Bitmap bitmap) {
        currentImage = bitmap;
    }
    @Override
    public void onDataPass(String setting) {
        currentLocationSetting = setting;
    }
    @Override
    public void onDataPass(ScanIntentResult result) {
        showScannedCode(result);
    }
    @Override
    public void onResume() {
        super.onResume();
        drawTitle();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!(Globals.username == null)) {
            this.f = new FireStoreClass(Globals.username);
            this.f.refreshCodes(new FireStoreResults() {
                @Override
                public void onResultGet() {

                }
            });
        }
    }

    /**
     * First, checks to see if location permissions have been enabled by the user, if they aren't
     * asks the user to enable permissions
     * Then, updates the current location of the user which is to be stored in the database for this
     * specific code
     *
     * This method is only called when the user enables geolocation on their most recent code scan
     */
    private void updateLocation() {
        // Check if we have permission to access the user's location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if we don't have it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        // Get the last known location from the LocationManager
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            this.currentLocation = lastLocation;
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preferences = getSharedPreferences("valid", MODE_PRIVATE);
        String remember = preferences.getString("remember", "");

        if (remember.equals("true")) {
            SharedPreferences preferences1 = getSharedPreferences("name", MODE_PRIVATE);
            Globals.username = preferences1.getString("username", "");
            Toast.makeText(this, "Successfully logged in as " + Globals.username, Toast.LENGTH_SHORT).show();
        } else {
            Intent newIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(newIntent);
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        logOutBtn = findViewById(R.id.logoutBtn);
        findUserBtn = findViewById(R.id.find_users);
        leaderboardBtn = findViewById(R.id.leaderboards);
        profileButton = findViewById(R.id.profile_btn);
        mapButton = findViewById(R.id.location_btn);
        cameraButton = findViewById(R.id.open_camera_button);
        cameraButton.setOnClickListener(v->
        {
            scanCode();
        });


        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLeaderboard();
            }
        });

        findUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_user();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProfile();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences("name", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username","");
                editor.apply();

                SharedPreferences preferences = getSharedPreferences("valid", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putString("remember","false");
                editor1.apply();

                Intent newIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(newIntent);

                TextView imageview = findViewById(R.id.item_image);
                TextView imageText = findViewById(R.id.item_image_text);
                TextView imageScore = findViewById(R.id.item_image_score);
                TextView imageRank = findViewById(R.id.item_image_rank);
                imageview.setText("");
                imageText.setText("");
                imageScore.setText("");
                imageRank.setText("");
            }
        });


        // listener for the location / map button
        mapButton.setOnClickListener(view -> {
            Intent newIntent = new Intent(MainActivity.this, MapActivity.class);
            newIntent.putExtra("key",Globals.username);
            updateLocation();
            if (currentLocation != null) {
                newIntent.putExtra("lat",this.currentLocation.getLatitude());
                newIntent.putExtra("lon",this.currentLocation.getLongitude());
            } else {
                newIntent.putExtra("lat",0);
                newIntent.putExtra("lon",0);
            }
            startActivity(newIntent);
        });

//        // listener for the location / map button
//        mapButton.setOnClickListener(view -> {
//            Intent newIntent = new Intent(MainActivity.this, MapActivity.class);
//            newIntent.putExtra("key",Globals.username);
//            startActivity(newIntent);
//        });





    }

    private void find_user() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        intent.putExtra("key",Globals.username);
        startActivity(intent);
    }

    /**
     * viewProfile shows all scanned qr codes of the user in a new Activity
     */
    private void viewProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("key",Globals.username);
        intent.putExtra("access", true);
        startActivity(intent);
    }

    /**
     * nagivates to the leaderboard activity
     */
    private void viewLeaderboard() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("key",Globals.username);
        startActivity(intent);
    }

    /*
    https://www.cambotutorial.com/article/10-minutes-build-bar-code-and-qr-code-scanner-in-android-app
    Retrieved February 22, 2023
    Licensed under MIT License
    https://choosealicense.com/licenses/mit/
     */

    /**
     * creates the activity that opens the phone's camera to scan a code
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
            currentScan = result;
            //i dont think we need this class, do it with normal alert dialog
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.CAMERA
                }, 100);
            }


            CodeFoundFragment codeFoundFragment = new CodeFoundFragment();
            codeFoundFragment.show(getSupportFragmentManager(), "Code Found");



        }
    });

    /**
     * This method generates the AlertDialog that shows the name, score, and picture for
     * the code that has been scanned
     * @param result
     * The result/contents of the barcode scan
     */
    public void showScannedCode(ScanIntentResult result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.code_captured, null);
        builder.setView(v);


        //builder.setTitle("Result");
        try {
            ScannedCode code = new ScannedCode(result);
            PlayerCode pCode = new PlayerCode(code.getHashAsString(), code.getName(),
                    code.getScore(), code.getPicture());
            pCode.setPhoto(currentImage);

            if(currentLocationSetting.equals(SAVE_LOCATION)) {
                updateLocation();
                pCode.setLocation(currentLocation);
            }

            TextView codeImage = (TextView) v.findViewById(R.id.code_image);
            TextView codeName = (TextView) v.findViewById(R.id.code_name);
            TextView codeScore = (TextView) v.findViewById(R.id.code_score);
            codeImage.setText(pCode.getPicture());
            codeName.setText(pCode.getName());
            codeScore.setText(Integer.toString(pCode.getScore()));
            Log.d("Location", String.valueOf(pCode.getLocation().getLatitude()));
            builder.setNegativeButton("Don't Collect", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("Collect", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pCode.setImgExists(pCode.getPhoto() != null);
                    // Add the player code to the database in here
                    f.addAQRCode(pCode);
                    dialog.dismiss();
                    onResume();
                }
            });
            builder.show();
            // e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ScanIntentResult getCurrentScan() {
        return currentScan;
    }

    private void drawTitle(){

        TextView imageview = findViewById(R.id.item_image);
        TextView imageText = findViewById(R.id.item_image_text);
        TextView imageScore = findViewById(R.id.item_image_score);
        TextView imageRank = findViewById(R.id.item_image_rank);
        this.f.getHighest(new FireStoreResults() {
            @Override
            public void onResultGet() {
                PlayerCode p = f.getHighestCode();
                if(!(p==null)){
                    f.determineRank(p.getScore(), new FireStoreResults() {
                        @Override
                        public void onResultGet() {
                            rank = f.getRank();
                            imageview.setText(p.getPicture());
                            imageText.setText(p.getName());
                            imageScore.setText(Integer.toString(p.getScore()));
                            imageRank.setText("Rank: "+ rank);


                            }
                        });
                    } else {
                        imageview.setText("");
                        imageText.setText("");
                        imageScore.setText("");
                        imageRank.setText("");
                }
            }
        });
    }

}
