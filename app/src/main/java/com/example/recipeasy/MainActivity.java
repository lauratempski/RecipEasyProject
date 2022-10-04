package com.example.recipeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    VideoView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();

    }

    public void initUI(){
        setContentView(R.layout.activity_main);

        nav = (BottomNavigationView) findViewById(R.id.navigation);
        background = (VideoView) findViewById(R.id.backgroundVideo);

        String path = "android.resource://com.example.recipeasy/"+R.raw.foood;
        Uri u = Uri.parse(path);
        background.setVideoURI(u);
        background.start();

        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeIcon:
                        startActivity(new Intent(MainActivity.this,Home.class));
                        break;

                    case R.id.favIcon:
                        startActivity(new Intent(MainActivity.this, Favourites.class));
                        break;


                    case R.id.addRecipeIcon:
                        startActivity(new Intent(MainActivity.this, AddRecipe.class));
                        break;


                    case R.id.listIcon:
                        startActivity(new Intent(MainActivity.this,ShoppigList.class));
                        break;

                    case R.id.scannerIcon:
                        startActivity(new Intent(MainActivity.this,BarCodeScanner.class));
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume(){
        background.resume();
        super.onResume();
    }

    @Override
    protected void onPause(){
        background.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        background.stopPlayback();
        super.onDestroy();
    }
}