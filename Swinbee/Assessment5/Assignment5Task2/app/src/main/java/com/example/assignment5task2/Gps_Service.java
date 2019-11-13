package com.example.assignment5task2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class Gps_Service extends Service {

    public static final String NOTIFICATION="NOTIFICATION FROM SERVICE";
    public static final String REPLY_TYPE = "TYPE";
    public static final String COORDINATES = "MSG";

    private int millisecond;
    private LocationListener locationListener;
    private LocationManager locationManager;

    public Gps_Service(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        millisecond = (int)intent.getExtras().get("Minutes");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent(NOTIFICATION);
                intent.putExtra(REPLY_TYPE, "GAE");
                intent.putExtra(COORDINATES, "Longitude: " + location.getLongitude() + " Latitude: " + location.getLatitude());
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // Suppressed
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, millisecond, 0, locationListener);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }
}
