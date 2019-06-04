package com.example.aankloten;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cam2 extends AppCompatActivity {

    ImageView imageView;
    String pathtofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam2);
        imageView = findViewById(R.id.imageView);
        if (Build.VERSION.SDK_INT >= 23)    {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2 );
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
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createFile();
            if (photoFile != null) {
                pathtofile = photoFile.getAbsolutePath();
                Uri photouri = FileProvider.getUriForFile(cam2.this,"com.example.aankloten.fileprovider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                startActivityForResult(takePic,1);
            }
        }
    }

    private File createFile() throws IOException {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        image = File.createTempFile(name,".jpg",storageDir);
        return image;
    }
}

