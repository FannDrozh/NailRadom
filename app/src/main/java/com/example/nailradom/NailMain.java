package com.example.nailradom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;

public class NailMain extends AppCompatActivity {
    private static final int DELAY = 3000;
    int defTimeOut = 0;
    public ImageView image = findViewById(R.id.imageNail);
    public boolean p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nail_main);

    }
    public void Clickfiltr()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent( NailMain.this, FinalNail.class);
                startActivity(intent);
            }
        }, DELAY);
    }
}