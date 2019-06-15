package com.example.aankloten;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.EditText;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import java.io.ByteArrayOutputStream;
public class FullImageActivity extends AppCompatActivity {
    ImageView i1;
    Uri uri;
    ImageButton arrowUp;
    Button shareButton;
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

        i1 = (ImageView) findViewById(R.id.full_image);
        shareButton = (Button) findViewById(R.id.button3);

        String data = getIntent().getExtras().getString("img");
        geluktMessage2 = findViewById(R.id.textView12);
        Intent i = getIntent();
        i1.setImageURI(Uri.parse(data));
        uri = uri.parse(data);
        i1.setImageURI(uri);

    }
    public void Button(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.putExtra("return.data",true);
        intent.putExtra(Intent.EXTRA_TEXT,"Image");
        Intent chooser = Intent.createChooser(intent ,"Share via");
        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(chooser);

        }
        }
    public void openLayout(View v){
        arrowUp = (ImageButton)findViewById(R.id.openButton);
        infoFrame = (FrameLayout)findViewById(R.id.infoFrame);
        titleField = (EditText)findViewById(R.id.titelField2);
        besField = (EditText)findViewById(R.id.descField2);

        arrowUp.setVisibility(View.INVISIBLE);
        infoFrame.setVisibility(View.VISIBLE);
        shareButton.setVisibility(View.GONE);

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
