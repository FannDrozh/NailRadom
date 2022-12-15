package com.example.nailradom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.jar.Attributes;

public class FinalNail extends AppCompatActivity {

    private Context nContext;
    ImageView Photo;
    List<Mask> data = new ArrayList<Mask>();
    ListView listView;
    Adapter pAdapter;
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

        v = findViewById(com.google.android.material.R.id.ghost_view);

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
    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void SelectList(String Choice)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {

                String query = Choice;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("ID_Nail"),
                                    resultSet.getString("Name_Design"),
                                    resultSet.getString("Price"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
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
                                    image = resultSet.("Image");
                                }

                                if(name_design == null)
                                {
                                    Toast.makeText(FinalNail.this, "Данные name не найдены", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    if(price == null)
                                    {
                                        Toast.makeText(FinalNail.this, "Данные price не найдены", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        NameNail.setText(name_design);
                                        PriceNail.setText(price);
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // decode base64 string
                                                byte[] bytes= Base64.getDecoder().decode(image);
                                                // Initialize bitmap
                                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                // set bitmap on imageView
                                                Photo.setImageBitmap(bitmap);
                                            }
                                        });
                                        Toast.makeText(FinalNail.this, "Данные изменены", Toast.LENGTH_SHORT).show();
                                    }
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