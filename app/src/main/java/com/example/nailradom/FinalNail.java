package com.example.nailradom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;
import java.util.jar.Attributes;

public class FinalNail extends AppCompatActivity {

    ImageView Photo;
    TextView NameNail,PriceNail;
    Mask mask;
    Connection connection;
    View v;
    String image = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_nail);

        mask=getIntent().getParcelableExtra("NailRandom");

        Photo = findViewById(R.id.Photo);

        NameNail = findViewById(R.id.NameNail);
        NameNail.setText(mask.getName());

        PriceNail = findViewById(R.id.PriceNail);
        PriceNail.setText(mask.getPrice());

        Photo.setImageBitmap(getImgBitmap(mask.getImage()));
        v = findViewById(com.google.android.material.R.id.ghost_view);
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(FinalNail.this.getResources(),
                R.drawable.logo);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            image=Base64.getEncoder().encodeToString(bytes);
            return image;
        }
        return "";
    }

    public void ClickReset(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(FinalNail.this);
        builder.setTitle("Изменение")
                .setMessage("Вы уверены что хотите, чтобы вам подобрали другой дизайн")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String query="";
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null)
                            {
                                query = "SELECT TOP 1 Name_Design = '" + NameNail.getText() + "', Price = '" + PriceNail.getText() + "', Image ='" + image + "' FROM Design_Nail ORDER BY NEWID()";
                                Statement statement = connection.createStatement();
                                Toast.makeText(FinalNail.this, "Данные изменены", Toast.LENGTH_SHORT).show();
                                statement.executeQuery(query);
                            }
                        }
                        catch (Exception ex)
                        {

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