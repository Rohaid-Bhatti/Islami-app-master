package com.example.islamiapp.TabsFragment.qabla;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

public class MyLocationProvider {

    LocationManager locationManager;
    Location location;
    boolean canGetLocation;
    Context context;
    long MINIMUM_TIME_BETWEEN_UPDATES=5*1000;
    long MINIMUM_DISTANCE_BETWEEN_UPDATES=5;
    

    public MyLocationProvider(Context context) {
        this.context = context;
        location=null;
        locationManager =(LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean isGpsEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }



    //this warning about you use permission and you must use runtime permission
    //but you use this function in activity use runtime permission  already , we use this SuppressWarnings to handle this warning
    @SuppressWarnings("MissingPermission")
    public Location getUserLocation(LocationListener locationListener){
        String provider=null;

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            provider= LocationManager.GPS_PROVIDER;
        else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            provider= LocationManager.NETWORK_PROVIDER;
        if(provider==null){
            canGetLocation=false;
            location=null;
            return null;
        }

        if (locationListener!=null) {
            locationManager.requestLocationUpdates(provider,
                    MINIMUM_TIME_BETWEEN_UPDATES,MINIMUM_DISTANCE_BETWEEN_UPDATES,locationListener);
        }
        location= locationManager.getLastKnownLocation(provider);

        if(location==null)
            location=getBestLastKnownLocation();

        return location;
    }

    @SuppressWarnings("MissingPermission")
    private Location getBestLastKnownLocation() {
        List<String> providers =locationManager.getProviders(true);
        Location bestLocation=null;
        for(String provider : providers){
            Location l=locationManager.getLastKnownLocation(provider);
            if(bestLocation==null&&l !=null){
                bestLocation=l;
            }else if(bestLocation!=null&& l!=null){
                if(l.getAccuracy()<bestLocation.getAccuracy())
                    bestLocation=l;
            }
        }
        return bestLocation;

    }





}
