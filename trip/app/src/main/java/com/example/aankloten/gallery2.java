package com.example.aankloten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;

public class gallery2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<File> list;
    ArrayList<Bitmap> thumb;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sp = this.getSharedPreferences("Foto", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        gridView = (GridView) findViewById(R.id.image_grid2);
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + "/trip");
        list = imageReader(storageDir);
        thumb = thumbMaker(storageDir);

        gridView.setAdapter(new gallery2.gridAdpter());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(gallery2.this, FullImageActivity.class);
                intent.putExtra("img", list.get(i).toString());

                String namePath = list.get(i).getPath();
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putString("path", namePath);
                Ed.apply();

                startActivity(intent);

            }
        });



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

    private ArrayList<Bitmap> thumbMaker(File externalStorageDirectory) {

        ArrayList<Bitmap> b = new ArrayList<>();
        File[] files = externalStorageDirectory.listFiles();


        for (int i = 0; i<files.length; i= i + 1){

            if (files[i].isDirectory()){

                b.addAll(thumbMaker(files[i]));
            }else{

                if (files[i].getName().endsWith(".jpg")){
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(files[i].getAbsolutePath()),128,128);
                    b.add(ThumbImage);
                }
            }


        }
        return b;

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
        getMenuInflater().inflate(R.menu.gallery2, menu);
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
            startActivity(new Intent(gallery2.this, Main2Activity.class));
        } else if (id == R.id.makememory) {
            startActivity(new Intent(gallery2.this, cam3.class));
        } else if (id == R.id.memories) {

        } else if (id == R.id.maps) {
            startActivity(new Intent(gallery2.this, MapsActivity.class));

        } else if (id == R.id.logout) {
            startActivity(new Intent(gallery2.this, MainActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class gridAdpter extends BaseAdapter {


        @Override
        public int getCount() {
            return thumb.size();
        }

        @Override
        public Object getItem(int i) {
            return thumb.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View View, ViewGroup ViewGroup) {

            View convertView = null;

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.row_layout, ViewGroup, false);
                ImageView myImage = convertView.findViewById(R.id.my_image);
                myImage.setImageBitmap(thumb.get(i));
            }


            return convertView;
        }

    }
}
