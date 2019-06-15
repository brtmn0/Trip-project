package com.example.aankloten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String fPath;
    ArrayList<File> list;
    String latitude;
    String longitude;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        final SharedPreferences sp = this.getSharedPreferences("Foto",MODE_PRIVATE);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String  namePath = marker.getSnippet();
                Intent intent = new Intent(MapsActivity.this,FullImageActivity.class);
                intent.putExtra("img",namePath);
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putString("path",namePath);
                Ed.apply();
                startActivity(intent);
                return false;
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng Beurs = new LatLng(51.918811, 4.480692);
        mMap.addMarker(new MarkerOptions().position(Beurs).title("Memory 1 - Beurs"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Beurs, 17), 100, null);

        File storageDir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + "/trip");
        list = imageReader(storageDir);

        int listsize = list.size();


        for (int i = 0; i < listsize; i++) {
            String namePath = list.get(i).getPath();
            Uri u = Uri.parse(namePath);
            File f = new File("" + u);
            fPath = f.getName();
            fPath = fPath.substring(0, 15);

            SharedPreferences sp2 = this.getSharedPreferences("Save", MODE_PRIVATE);
            latitude = sp2.getString(fPath + "lat", null);
            longitude = sp2.getString(fPath + "lon", null);
            title = sp2.getString(fPath + "titel",null);



            if (latitude != null | longitude != null) {
                double lat = Double.parseDouble(latitude);
                double lot = Double.parseDouble(longitude);
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lot)).title(title).snippet(namePath));
           }

        }


    }




    private ArrayList<File> imageReader(File externalStorageDirectory) {

        ArrayList<File> b = new ArrayList<>();
        File[] files = externalStorageDirectory.listFiles();

        for (int i = 0; i<files.length; i= i + 1){

            if (files[i].isDirectory()){

                b.addAll(imageReader(files[i]));
            }else{

                if (files[i].getName().endsWith(".jpg")){
                    b.add(files[i]);
                }
            }
        }
        return b;

    }
}
