package com.example.aankloten;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.FrameLayout;

public class FullImageActivity extends AppCompatActivity {
    ImageView fullImage;
    ImageButton arrowUp;
    FrameLayout infoFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        fullImage = (ImageView)findViewById(R.id.full_image);


        String data = getIntent().getExtras().getString("img");

        fullImage.setImageURI(Uri.parse(data));
    }
    public void openLayout(View v){
        arrowUp = (ImageButton)findViewById(R.id.openButton);
        infoFrame = (FrameLayout)findViewById(R.id.infoFrame);
        arrowUp.setVisibility(View.INVISIBLE);
        infoFrame.setVisibility(View.VISIBLE);
    }
}
