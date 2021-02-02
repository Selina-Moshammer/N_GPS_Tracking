package com.example.n_gps_tracking;

import com.example.n_gps_tracking.Model.GPS;

public class GPSTbl {
    public final static String TABLE_NAME = "GPS";

    public final static String GPSId ="GPSId";
    public final static String Longitude = "Longitude";
    public final static String Latitude = "Latitude" ;
    public final static String Date = "Date";

    public final static String SQL_DROP = "DROP TABLE IF EXISTS "+
            TABLE_NAME;
    public final static String SQL_CREATE = "CREATE TABLE " + TABLE_NAME+
            "("+
            GPSId+" INTEGER PRIMARY KEY,"+
            Longitude+" DOUBLE NOT NULL,"+
            Latitude+" DOUBLE NOT NULL,"+
            Date+" String NOT NULL"+
            ")";

    public final static String STMT_DELETE = "DELETE FROM "+TABLE_NAME;
    public final static String STMT_INSERT =
            "INSERT INTO "+TABLE_NAME+
                    "(" + GPSId+","+
                    Longitude +","+ Latitude+","+Date+")"+
                    "VALUES (?,?,?,?)";

    public final static String SELECT_ALL = "SELECT * FROM "+TABLE_NAME;


}
