package com.example.mike.hilosdeandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bloquear orientacion.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

    }

    public void b_post(View view) {
        Intent intent = new Intent(MainActivity.this, Cronometro_post.class);
        startActivity(intent);
    }

    public void b_handler(View view) {
        Intent intent = new Intent(MainActivity.this, Cronometro_Handler.class);
        startActivity(intent);
    }

    public void b_async(View view) {
        Intent intent = new Intent(MainActivity.this, Cronometro_AsyncTask.class);
        startActivity(intent);
    }
}
