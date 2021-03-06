package com.example.foursquare;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
 
public class GpsInfo extends Service implements LocationListener {
  
    private final Context mContext;
  
    // Current GPS status
    boolean isGPSEnabled = false;
  
    // Current network status 
    boolean isNetworkEnabled = false;
  
    // GPS value
    boolean isGetLocation = false;
  
    Location location; 
    double lat; // latitude 
    double lon; // longitude
  
    // minimum gps update distance = 10m 
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 
  
    // minimum gps update time = 1 minute 
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; 
  
    protected LocationManager locationManager;
  
    public GpsInfo(Context context) {
        this.mContext = context;
        getLocation();
    }
  
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
  
            // Bring gps information 
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
  
            // Get current network value  
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  
            if (!isGPSEnabled && !isNetworkEnabled) {
                // if GPS and network is not available 
            } else {
                this.isGetLocation = true;
                // Bring current location from network
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            // Store latitude and longitude
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                 
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
      
    /**
     * End Gps  
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsInfo.this);
        }       
    }
      
    /**
     * Get latitude 
     * */
    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }
      
    /**
     * Get longitude 
     * */
    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }
      
    /**
     * Check whether gps is on or not
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }
      

  
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
         
    }
 
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
         
    }
 
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
         
    }
 
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
         
    }
}
