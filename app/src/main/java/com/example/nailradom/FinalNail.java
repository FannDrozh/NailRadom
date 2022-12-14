package com.example.nailradom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FinalNail extends AppCompatActivity {


    ImageView Photo;
    TextView NameNail,PriceNail;
    Mask mask;
    Button button;
    Connection connection;
    View v;
    String image = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_nail);

        mask=getIntent().getParcelableExtra("NailRandom");
        NameNail =findViewById(R.id.NameNail);
        PriceNail = findViewById(R.id.PriceNail);
        Photo = findViewById(R.id.Photo);

        //v = findViewById(com.google.android.material.R.id.ghost_view);

    }
    private void selectImage() {
        // clear previous data
        NameNail.setText("");
        Photo.setImageBitmap(null);
        // Initialize intent
        Intent intent=new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("image/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // check condition
        if (requestCode==100 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            // when permission
            // is granted
            // call method
            selectImage();
        }
        else
        {
            // when permission is denied
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
    public ImageView MyDisign (String image){

                // decode base64 string
                byte[] bytes= Base64.getDecoder().decode(image);
                // Initialize bitmap
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
                Photo.setImageBitmap(bitmap);
        return Photo;
    }

    public void ClickReset(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(FinalNail.this);
        builder.setTitle("??????????????????")
                .setMessage("???? ?????????????? ?????? ????????????, ?????????? ?????? ?????????????????? ???????????? ????????????")
                .setCancelable(false)
                .setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String query = "SELECT TOP 1 Name_Design, Price, Image FROM Design_Nail ORDER BY NEWID()";
                            String name_design = "";
                            String price = "";
                            String img = "";
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery(query);
                                while (resultSet.next()){
                                    name_design = resultSet.getString("Name_Design");
                                    price = resultSet.getString("Price");
                                    img = resultSet.getString("Image");
                                }

                                if(name_design == null)
                                {
                                    Toast.makeText(FinalNail.this, "???????????? name ???? ??????????????", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    if(price == null)
                                    {
                                        Toast.makeText(FinalNail.this, "???????????? price ???? ??????????????", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        NameNail.setText(name_design);
                                        PriceNail.setText(price);
                                        image = img;
                                        MyDisign(image);
                                        Toast.makeText(FinalNail.this, "???????????? ????????????????", Toast.LENGTH_SHORT).show();
                                    }
                                }



                            }
                        }
                        catch (Exception ex)
                        {

                        }
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog=builder.create();
        dialog.show();
    }


}