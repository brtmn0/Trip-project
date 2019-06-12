package com.example.aankloten;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.FrameLayout;
import android.widget.EditText;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity {
    ImageView fullImage;
    ImageButton arrowUp;
    FrameLayout infoFrame;
    EditText titleField;
    EditText besField;
    String nameString;
    String titelEdited;
    String besEdited;
    TextView geluktMessage2;
    public String fPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        fullImage = (ImageView)findViewById(R.id.full_image);
        String data = getIntent().getExtras().getString("img");
        geluktMessage2 = findViewById(R.id.textView12);
        fullImage.setImageURI(Uri.parse(data));
    }
    public void openLayout(View v){
        arrowUp = (ImageButton)findViewById(R.id.openButton);
        infoFrame = (FrameLayout)findViewById(R.id.infoFrame);
        titleField = (EditText)findViewById(R.id.titelField2);
        besField = (EditText)findViewById(R.id.descField2);

        arrowUp.setVisibility(View.INVISIBLE);
        infoFrame.setVisibility(View.VISIBLE);

        SharedPreferences sp=this.getSharedPreferences("Foto", MODE_PRIVATE);
        String namePath=sp.getString("path", null);

        Uri u = Uri.parse(namePath);
        File f = new File("" + u);
        fPath = f.getName();
        fPath = fPath.substring(0,15);

        SharedPreferences sp2=this.getSharedPreferences("Save", MODE_PRIVATE);
        String titelString2=sp2.getString(fPath+"titel", null);
        String besString2=sp2.getString(fPath+"bes", null);
        titleField.setText(titelString2);
        besField.setText(besString2);
    }

    public void editOnClick(View v) {
        titelEdited = this.titleField.getText().toString();
        besEdited = this.besField.getText().toString();

        SharedPreferences sp5=this.getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor Ed5 = sp5.edit();
        Ed5.putString(fPath+"titel",titelEdited);
        Ed5.putString(fPath+"bes",besEdited);
        Ed5.apply();
        geluktMessage2.setVisibility(View.VISIBLE);

    }

}
