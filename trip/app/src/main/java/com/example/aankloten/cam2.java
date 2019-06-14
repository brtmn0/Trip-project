package com.example.aankloten;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.widget.Button;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cam2 extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam2);
        imageView = findViewById(R.id.imageView);
        fotoButton = findViewById(R.id.fotoButton);
        geluktMessage = findViewById(R.id.textView9);
        if (Build.VERSION.SDK_INT >= 23)    {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},2 );
        }


    }

    public void foto(View view) throws IOException {
        dispatchpicture();
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

    private void dispatchpicture() throws IOException {
        if(picTaken == 1){
            picTaken = 2;
            fotoButton.setText("voeg info toe!");
            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            titel = (EditText)findViewById(R.id.Titel);
            bes  = (EditText)findViewById(R.id.beschrijvingField);
            titel.setVisibility(View.VISIBLE);
            bes.setVisibility(View.VISIBLE);
            if (takePic.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createFile();
                if (photoFile != null) {
                    pathtofile = photoFile.getAbsolutePath();
                    Uri photouri = FileProvider.getUriForFile(cam2.this,"com.example.aankloten.fileprovider", photoFile);
                    takePic.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                    startActivityForResult(takePic,1);
                    return;
                }
            }
        }
        if(picTaken == 2){
            GPSTracker g = new GPSTracker(getApplicationContext());
            Location l = g.getLocation();
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            String latitude = Double.toString(lat);
            String longitude = Double.toString(lon);
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
}

