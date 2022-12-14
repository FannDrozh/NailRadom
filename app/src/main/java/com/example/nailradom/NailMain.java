package com.example.nailradom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

public class NailMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nail_main);
    }
    public void Clickfiltr(View v)
    {
        Intent intent = new Intent( NailMain.this, FinalNail.class);
        startActivity(intent);
    }
}