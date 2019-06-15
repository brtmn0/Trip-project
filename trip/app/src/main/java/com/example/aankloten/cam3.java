package com.example.aankloten;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cam3 extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView;
    String pathtofile;
    EditText titel;
    EditText bes;
    String name;
    String titelString;
    String besString;
    Button fotoButton;
    TextView geluktMessage;
    int picTaken = 1;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        imageView = findViewById(R.id.imageViewcam);
        fotoButton = findViewById(R.id.fotoButtoncam);
        geluktMessage = findViewById(R.id.textView9cam);
        if (Build.VERSION.SDK_INT >= 23)    {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},2 );
        }
    }

    public void foto(View view) throws IOException {
        dispatchpicture();
    }

    private void dispatchpicture() throws IOException {
        if (picTaken == 1) {
            picTaken = 2;
            fotoButton.setText("voeg info toe!");
            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            titel = (EditText) findViewById(R.id.Titelcam);
            bes = (EditText) findViewById(R.id.beschrijvingFieldcam);
            titel.setVisibility(View.VISIBLE);
            bes.setVisibility(View.VISIBLE);
            if (takePic.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createFile();
                if (photoFile != null) {
                    pathtofile = photoFile.getAbsolutePath();
                    Uri photouri = FileProvider.getUriForFile(cam3.this, "com.example.aankloten.fileprovider", photoFile);
                    takePic.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                    startActivityForResult(takePic, 1);
                    return;
                }
            }
        }
        if(picTaken == 2){
            GPSTracker g = new GPSTracker(getApplicationContext());
            Location l = g.getLocation();
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            latitude = Double.toString(lat);
            longitude = Double.toString(lon);
            SharedPreferences sp4=this.getSharedPreferences("name", MODE_PRIVATE);
            String name4=sp4.getString("name", null);

            SharedPreferences sp=this.getSharedPreferences("Save", MODE_PRIVATE);
            titelString = this.titel.getText().toString();
            besString = this.bes.getText().toString();
            SharedPreferences.Editor Ed = sp.edit();
            Ed.putString(name4+"titel",titelString);
            Ed.putString(name4+"bes",besString);
            Ed.putString(name4+"lat",latitude);
            Ed.putString(name4+"lon",longitude);
            Ed.apply();
            geluktMessage.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "LAT: "+latitude+" \n LON: "+longitude,Toast.LENGTH_LONG).show();
        }
    }

    private File createFile() throws IOException {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        SharedPreferences sp3=this.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor Ed3 = sp3.edit();
        Ed3.putString("name",name);
        Ed3.apply();

        File storageDir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + "/trip");
        storageDir.mkdir();
        File image = null;
        image = File.createTempFile(name,".jpg",storageDir);
        return image;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cam3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
                startActivity(new Intent(cam3.this, Main2Activity.class));
        } else if (id == R.id.makememory) {

        } else if (id == R.id.memories) {
            startActivity(new Intent(cam3.this, gallery2.class));
        } else if (id == R.id.maps) {
            startActivity(new Intent(cam3.this, MapsActivity.class));

        } else if (id == R.id.logout) {
            startActivity(new Intent(cam3.this,MainActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data)   {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1)   {
                Bitmap b = BitmapFactory.decodeFile(pathtofile);
                imageView.setImageBitmap(b);
            }
        }
    }

}
