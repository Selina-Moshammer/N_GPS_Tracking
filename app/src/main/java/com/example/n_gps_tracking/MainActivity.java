package com.example.n_gps_tracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n_gps_tracking.Model.GPS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private static final int RQ_ACCESS_FINE_LOCATION = 666;
    private boolean isGpsAllowed = false;
    private LocationListener locationListener;
    private  SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerSystemService();
        checkPermissionGPS();

        //DB
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Button btnAnzeigen = findViewById(R.id.btnAnzeigen);
        btnAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });

    }

    private void switchActivity(){
        Intent intent = new Intent(this, ActivityData.class);
        startActivity(intent);
    }

    private void registerSystemService(){
        locationManager = getSystemService(LocationManager.class);
    }

    private void checkPermissionGPS(){
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if(ActivityCompat.checkSelfPermission(this, permission)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{permission}, RQ_ACCESS_FINE_LOCATION);
            isGpsAllowed = false;
        }else{
            gpsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   if (requestCode != RQ_ACCESS_FINE_LOCATION) return;
   if (grantResults.length >0 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
       Toast.makeText(getApplicationContext(), "Persmission ACCESS_FINE_LOCATION denied!"
       , Toast.LENGTH_LONG).show();

   }else
   {
       gpsGranted();
   }
    }

    private void gpsGranted(){
        Toast.makeText(getApplicationContext(), "GPS granted", Toast.LENGTH_LONG).show();
        isGpsAllowed = true;

        locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(@NonNull Location location) {
                SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date date = new Date();
                String dateString = formater.format(date);


                double lat = location ==null ? -1 : location.getLatitude();
                double lon = location ==null ? -1 : location.getLongitude();
                //Anzeigen
                displayLocation(lat, lon,dateString);

                //in DB speichern
                //search for ID
                Cursor rows = db.rawQuery( GPSTbl.SELECT_ALL, null);
                int id = 0;
                while (rows.moveToNext()) {
                    id = rows.getInt(0);
                }
                id = id+1;

                db.execSQL(GPSTbl.STMT_INSERT,
                        new String[]{
                                id+"",lon+"",lat+"",
                                dateString
                        });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isGpsAllowed){
            locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
                    3000,
                    5,
            locationListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isGpsAllowed)locationManager.removeUpdates(locationListener);
    }

    private void displayLocation (double lat, double lon, String date){

        TextView tvLongitude = findViewById(R.id.tVLongitude);
        TextView tvLatitude = findViewById(R.id.tVLatitude);
        TextView tvDate = findViewById(R.id.tVMyDate);

        tvLongitude.setText(String.format("%.4f", lon));
        tvLatitude.setText(String.format("%.4f", lat));
        tvDate.setText(date);


    }
}