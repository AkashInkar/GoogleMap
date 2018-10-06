package com.example.felixits.googlemap_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int RESQUEST_PERMISSION_LOCATION =1;

    // this variable get for the location in mobile
    private FusedLocationProviderClient mfusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/** this code is used to get the permission/ check the permission allow or not*/
        if (ContextCompat .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)

        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.
                    ACCESS_FINE_LOCATION},RESQUEST_PERMISSION_LOCATION);
        }
        else
        {
            getMyLocation();
            Toast.makeText(this,"Permission is allowed",Toast.LENGTH_SHORT).show();
        }



        // Add a marker in Sydney and move the camera
        LatLng vimannagar = new LatLng(18.566526, 73.912239);
        LatLng kalynaiNagar = new LatLng(18.5463, 73.9033);
        LatLng baner = new LatLng(18.5590, 73.7868);

        mMap.addMarker(new MarkerOptions().position( vimannagar).title("Marker in VimanNagar"));
        mMap.addMarker(new MarkerOptions().position( kalynaiNagar).title("Marker in KalyaniNagar"));
        mMap.addMarker(new MarkerOptions().position( baner).title("Marker in Baner"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( vimannagar,12.0f));
        CircleOptions circleOptions =  new CircleOptions().center(vimannagar).radius(1000).strokeColor(Color.BLACK)
                .strokeWidth(3).fillColor(Color.BLUE);

        mMap.addCircle(circleOptions);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(vimannagar);
        polylineOptions.add(kalynaiNagar);
        polylineOptions.add(baner);
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        mMap.addPolyline(polylineOptions);


        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(vimannagar);
        polygonOptions.add(kalynaiNagar);
        polygonOptions.add(baner);
        polygonOptions.strokeWidth(15);
        polygonOptions.fillColor(Color.BLUE);
        mMap.addPolygon(polygonOptions);

    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        mfusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    LatLng mylocation = new LatLng(location.getLatitude(),location.getLongitude());

                    MarkerOptions markerOptions =new MarkerOptions().position(mylocation).title("thus is my location");
                    mMap.addMarker(markerOptions);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== RESQUEST_PERMISSION_LOCATION){
            if (grantResults.length> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getMyLocation();
            }
            else {
                Toast.makeText(this,"this is permission is mandatory",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RESQUEST_PERMISSION_LOCATION);
            }
        }
    }
}
