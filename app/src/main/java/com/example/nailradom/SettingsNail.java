package com.example.nailradom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SettingsNail extends AppCompatActivity
{
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_nail);
    }
    public void Final ()
    {
        Intent intent = new Intent(SettingsNail.this, FinalNail.class);
        startActivity(intent);
    }

    public void RandomNailClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(SettingsNail.this);
        builder.setTitle("Дизайн ногтей")
                .setMessage("Вы готовы увидеть ваш будющий дизайн ногтей?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if (Country.getText().length()==0|| Population.getText().length()==0  )
                        //{
                        //    Toast.makeText(SettingsNail.this, "Не заполнены обязательные поля", Toast.LENGTH_SHORT).show();
                        //    return;
                        //}
                        try {
                            String query="";
                            int id;
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {

                                query = "SELECT FIRST 1 ID_Nail FROM Design_Nail ORDER BY rand()";
                                id = Integer.getInteger(query);
                                if(id > 0 || id < 3)
                                {
                                    Statement statement = connection.createStatement();
                                    Toast.makeText(SettingsNail.this,"Нокоточки подобраны", Toast.LENGTH_LONG).show();
                                    ResultSet result = statement.executeQuery(query);
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(SettingsNail.this,"Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        Final();
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