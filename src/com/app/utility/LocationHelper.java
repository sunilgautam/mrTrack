package com.app.utility;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener
{
    private LocationManager locationManager;
    private String provider;
    
    public Location getCurrentLocation(android.app.Activity activity)
    {
        // Get the location manager
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        
        // Define the criteria how to select the location provider
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        //System.out.println("LOCATION ================>");
        //System.out.println(location);
        return location;
    }
    
    @Override
    public void onLocationChanged(Location arg0)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onProviderDisabled(String arg0)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onProviderEnabled(String arg0)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2)
    {
	// TODO Auto-generated method stub
	
    }

}
