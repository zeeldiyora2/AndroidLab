package com.example.dz.androidlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In OnCreate()");

        Button startbtn = (Button) findViewById(R.id.helloBtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(intent,50);
            }
        });

        Button chatBtn = (Button) findViewById(R.id.startchatbtn);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StartActivity.this,ChatWindow.class);
                startActivityForResult(intent2,50);
            }
        });

        Button weatherBtn = (Button) findViewById(R.id.weatherBtn);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(StartActivity.this,WeatherForecast.class);
                startActivityForResult(intent3,50);
            }
        });

        Button toolbarBtn = (Button) findViewById(R.id.testToolbarBtn);
        toolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(StartActivity.this,TestToolbar.class);
                startActivityForResult(intent4,50);
            }
        });

    }

    protected void onActivityResult(int requestCode, int responseCode){
        if(requestCode == 50){
            Log.i(ACTIVITY_NAME, "Return to StartActivity.onActivityResult");
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In OnResume()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In OnStart()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In OnPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In OnStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In OnDestroy()");
    }
}