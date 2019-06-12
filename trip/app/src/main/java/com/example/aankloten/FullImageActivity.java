package com.example.aankloten;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
public class FullImageActivity extends AppCompatActivity {
    ImageView i1;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        i1 = (ImageView) findViewById(R.id.full_image);

        String data = getIntent().getExtras().getString("img");
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
}
