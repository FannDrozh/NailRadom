package com.example.nailradom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.jar.Attributes;

public class FinalNail extends AppCompatActivity {

    ImageView Photo;
    TextView NameNail,PriceNail;
    Mask mask;
    Button RandBut1;
    Connection connection;
    View v;
    String image = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_nail);

        mask=getIntent().getParcelableExtra("NailRandom");
        v = findViewById(com.google.android.material.R.id.ghost_view);

    }

    /*private Bitmap getImgBitmap(String encodedImg) {
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
    }*/
    public void run() {
        Intent intent = new Intent( FinalNail.this, FinalNail.class);
        startActivity(intent);
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
                            String query = "SELECT TOP 1 Name_Design, Price FROM Design_Nail ORDER BY NEWID()";
                            String name_design = "";
                            int price = 0;
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery(query);
                                while (resultSet.next()){
                                    name_design = resultSet.getString("Name_Design");
                                    price = resultSet.getInt("Price");
                                    //image = resultSet.getString("Image");
                                }

                                if(name_design == null)
                                {
                                    Toast.makeText(FinalNail.this, "Данные не найдены", Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    NameNail.setText(name_design);
                                    PriceNail.setText(price);
                                    Toast.makeText(FinalNail.this, "Данные изменены", Toast.LENGTH_SHORT).show();
                                }



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