package com.example.n_gps_tracking.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GPS {
   private int id;
   private double longitude;
   private double latitude;
   private Date date;

    public GPS(double longitude, double latitude, Date date) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateString = formater.format(date);
        return "longitude=" + longitude +
                ", latitude=" + latitude +",\n"+
                "date=" + dateString ;
    }
}
