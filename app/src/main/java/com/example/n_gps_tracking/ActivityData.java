package com.example.n_gps_tracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.n_gps_tracking.Model.GPS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityData extends AppCompatActivity {
    private SQLiteDatabase db;
    private ArrayList<GPS> gpsList;
    private ArrayAdapter<GPS> adapterG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        gpsList = new ArrayList<>();
        ListView lv = findViewById(R.id.listViewGPS);
        BindAdapterToList(lv);
        ReadFromDataBase();

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switchActivity();
            }
        });




    }

    private void switchActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void BindAdapterToList(ListView lv){
        adapterG = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,gpsList);
        lv.setAdapter(adapterG);
    }

    private void ReadFromDataBase(){
        Cursor rows = db.rawQuery( GPSTbl.SELECT_ALL, null);
        while (rows.moveToNext()) {

           
            double longitude = rows.getDouble(1);
            double latitude = rows.getDouble(2);
            String dateString = rows.getString(3);

            SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = null;
            try {
                date = formater.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

                GPS gps = new GPS(longitude, latitude, date);
                gpsList.add(gps);
                adapterG.notifyDataSetChanged();

        }
    }
}