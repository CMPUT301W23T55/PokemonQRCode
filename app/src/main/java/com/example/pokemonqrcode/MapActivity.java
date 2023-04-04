package com.example.pokemonqrcode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MapActivity is an activity which displays a map, containing markers for code locations
 * This activity implements OpenStreetMaps instead of Google Maps
 * Much of the code in this class was adapted from the OpenStreetMaps tutorial, link is
 * https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
 */
public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private final int REQUEST_PERMS = 1;
    Location startLocation;
    Context context;


    private String username;
    private double lat;
    private double lon;

    ArrayList<Users> user_list;

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    /**
     * onCreate function
     * Most of the code here is from the tutorial on open maps, link is
     * https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);


        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK); // Set the tile source to Mapnik
        mapView.setBuiltInZoomControls(true); // Enable zoom controls
        mapView.setMultiTouchControls(true); // Enable multi-touch controls


        MapController mapController = (MapController) mapView.getController();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        mapController.setZoom(14); // Set the zoom level
        GeoPoint startPoint = new GeoPoint(startLocation); // Create a GeoPoint object with the initial center latitude and longitude
        mapController.setCenter(startPoint); // Set the initial center of the map

        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        mapView.getOverlays().add(myLocationOverlay);
        mapView.invalidate();



        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(51.5074, -113.1278)); // Set the position of the marker
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker); // Add the marker to the map






    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            startLocation = new Location("");
            startLocation.setLatitude(latitude);
            startLocation.setLongitude(longitude);
        }
    };

/**
 * Function to handle results of permission request
 * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
 * @param requestCode code of the permissions request
 * @param permissions permissions being requested
 */

 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 ArrayList<String> perms = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));
 if (perms.size() > 0) {
 ActivityCompat.requestPermissions(this, perms.toArray(new String[0]), REQUEST_PERMS);
 }
 }

 /**
  * Function to request permissions necessary for the map to work
  * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
  * @param permissions a list of the permissions needed to make the map work
  */

private void requestPermissions(String[] permissions) {
ArrayList<String> perms = new ArrayList<>();
for (String perm : permissions) {
if (ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED) {
perms.add(perm);
}
}
if (perms.size() > 0) {
ActivityCompat.requestPermissions(this,perms.toArray(new String[0]),REQUEST_PERMS);
}
}
}


/*

        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();

        // get the arguments passed by the intent
        this.username = getIntent().getStringExtra("key");
        Log.d("Username",this.username);

        try {
            this.lat = (double) extras.get("lat");
            this.lon = (double) extras.get("lon");
        } catch (ClassCastException e) {
            this.lat = extras.getInt("lat",0);
            this.lon = extras.getInt("lon",0);
        }


        // create the header
        CustomHeader head = findViewById(R.id.header_map_activity);
        head.initializeHead("Map", "Back");
        // set listener for back button in the header
        // TODO: button needs to be clicked multiple times to work, need to fix that
        head.back_button.setOnClickListener(view -> {finish();});

        // get the map view
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        // these are for permissions needed to make the map work properly
        List<String> permsList = Collections.singletonList(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String[] permsArray = permsList.toArray(new String[0]);



        // this is just a test point, preferably we would get the geolocation of the user
        // or ask the user to enter their location
        GeoPoint start = new GeoPoint(this.lat,this.lon);

        // creating an overlay for the map
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getBaseContext()), map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);

        requestPermissions(permsArray);

        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        mapController.setCenter(start);

        // get codes from db
        FireStoreClass f = new FireStoreClass(username);

        f.getSearchList(() -> {
            ArrayList<Users> user_list = f.getUsersArrayList();

            for (Users user : user_list) {
                FireStoreClass fireStoreClass = new FireStoreClass(user.getUsername());
                fireStoreClass.getCodesList(new FireStoreLIstResults() {
                    @Override
                    public void onResultGetList(ArrayList<PlayerCode> playerCodeList) {
                        for (PlayerCode code : playerCodeList) {
                            GeoPoint point;

                            if (code.getLocation() != null) {
                                Location location1 = new Location("");
                                location1.setLongitude(0.0d);
                                location1.setLongitude(0.0d);

                                if (code.getLocation() != location1) {

                                    Log.d("Code location", code.getLocation().toString());
                                    point = new GeoPoint(code.getLocation().getLatitude(),
                                            code.getLocation().getLongitude());
                                    Marker newMarker = new Marker(map);
                                    newMarker.setPosition(point);
                                    newMarker.setTitle(code.getName() + "\nScore: " + code.getScore()
                                            + "\nHash: " + code.getHashCode());
                                    newMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                    map.getOverlays().add(newMarker);
                                }

                            } else {
                                Log.d("Code location", "None");
                            }
                        }
                    }
                });
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * Function to handle results of permission request
     * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param requestCode code of the permissions request
     * @param permissions permissions being requested

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    ArrayList<String> perms = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));
    if (perms.size() > 0) {
        ActivityCompat.requestPermissions(this, perms.toArray(new String[0]), REQUEST_PERMS);
    }
}

    /**
     * Function to request permissions necessary for the map to work
     * Code is adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param permissions a list of the permissions needed to make the map work

    private void requestPermissions(String[] permissions) {
        ArrayList<String> perms = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,perm) != PackageManager.PERMISSION_GRANTED) {
                perms.add(perm);
            }
        }
        if (perms.size() > 0) {
            ActivityCompat.requestPermissions(this,perms.toArray(new String[0]),REQUEST_PERMS);
        }
    }



    /**
     * Function that takes the map and a point, and creates a map marker for the point
     * Ideally this would get geolocation data from a database, to show the user nearby codes
     * and show the user where their codes are from
     * Again, adapted from https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Java)
     * @param points list of items to be added to an overlay, then displayed as map markers

    private void makeMapMarker(ArrayList<OverlayItem> points) {
        // TODO: deal with deprecated classes from Open Maps
        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(points,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        byte[] mock = "Test string".getBytes();
                        ScannedCode mockCode = new ScannedCode(mock);
                        switchActivityOnHold(mockCode,MainActivity.class);
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        // this code is just to test the implementation
                        //String qrname = item.getTitle();
                        //ScannedCode pressedQRCode = new ScannedCode(qrname);
                        //switchActivityOnHold(pressedQRCode,QRLeaderboardActivity.class);
                        return false;
                    }
                }, getBaseContext());

        Log.d("Overlay item", overlay.toString());
        overlay.setFocusItemsOnTap(true);
        map.getOverlays().add(overlay);

    }

    /**
     * Function to take users to the qr code leaderboard from map markers
     * @param qrCode QRCode that the user clicked on, gets passed to the next activity, where the
     *               user can see the QRCode and a leaderboard
     * @param activityClass the activity which will be started

    private void switchActivityOnHold(ScannedCode qrCode, Class activityClass) {
        // before opening page, ask the user if they want to go to page
        // ignore warning about using Class in the function arguments
        new AlertDialog.Builder(MapActivity.this)
                .setTitle("Test")
                .setMessage("Would you like to go to the leaderboard for this code?")
                .setPositiveButton("Go to leaderboard", (dialogInterface, i) -> {
                    Intent intent = new Intent(getBaseContext(), activityClass);
                    intent.putExtra("prevActivity","map");
                    intent.putExtra("Code", (CharSequence) qrCode);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
 */
