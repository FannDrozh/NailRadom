package com.example.nailradom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NailMain extends AppCompatActivity {
    private static final int DELAY = 3000;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nail_main);
        image = findViewById(R.id.imageNail);
    }
    public void Clickfiltr(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(NailMain.this);
        builder.setTitle("Изменение")
                .setMessage("Вы хотите подобрать себе дизайн ноктей?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            image.setImageResource(R.drawable.nail2);
                            Toast.makeText(NailMain.this, "Перенапрявляю в рандомайзер", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent( NailMain.this, FinalNail.class);
                                    startActivity(intent);
                                }
                            }, DELAY);
                        }
                        catch (Exception ex){

                        }
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
