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
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String fPath;
    private ClusterManager<MyItem> mClusterManager;
    ArrayList<File> list;
    String latitude;
    String longitude;
    String title;
    String bes;

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
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        final SharedPreferences sp = this.getSharedPreferences("Foto",MODE_PRIVATE);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add a marker in Sydney and move the camera
        LatLng Beurs = new LatLng(51.918811, 4.480692);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Beurs, 10), 100, null);

        File storageDir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + "/trip");
        list = imageReader(storageDir);

        int listsize = list.size();

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                String  namePath = item.getSnippet();
                Intent intent = new Intent(MapsActivity.this,FullImageActivity.class);
                intent.putExtra("img",namePath);
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putString("path",namePath);
                Ed.apply();
                startActivity(intent);
                return false;
            }
        });

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
            bes = sp2.getString(fPath + "bes", null);
            String dats = u.toString();
            String datum_s = dats.substring(dats.lastIndexOf("/"));
            datum_s = datum_s.substring(1,9);
            String d_y = datum_s.substring(0,4);
            String d_m = datum_s.substring(4, 6);
            String d_d = datum_s.substring(6, 8);
            String datum = "("+d_d+"-"+d_m+"-"+d_y+")";




            if (latitude != null | longitude != null) {
                double lat = Double.parseDouble(latitude);
                double lot = Double.parseDouble(longitude);
                MyItem marker = new MyItem(lat, lot, title, u+"");
                mClusterManager.addItem(marker);
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

class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        mTitle = "";
        mSnippet = "";
    }

    public MyItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
