package com.app.services;

import com.app.async.PostLocationUpdate;
import com.app.pojo.MyPosition;
import com.app.utility.AppUtility;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationTrackerService extends Service implements LocationListener
{
    public final static String LOGTAG = LocationTrackerService.class.getName();
    private LocationManager locationManager;
    private String provider;
    private Context mContext;
    private AppUtility utility = new AppUtility();
    
    Location location;
    double latitude;
    double longitude;
    
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    
    // THE MINIMUM DISTANCE TO CHANGE UPDATES IN METERS
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    // THE MINIMUM TIME BETWEEN UPDATES IN MILLISECONDS
    private static final long MIN_TIME_BW_UPDATES = 400;

    // THE RANGE FOR NOTIFICATIONS IN METERS
    private static final long NOTIFICATION_RANGE = 300;

    // MAXIMUM THRESHOLD TIME BETWEEN UPDATES
    public static final long LOCATION_UPDATE_MAX_DELTA_THRESHOLD = 1000 * 60 * 5;
    
    public LocationTrackerService()
    {
	super();
    }
    
    public Location registerLocationListner()
    {
	try
	{
	    Location tempLocation = null;
	    locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

	    // getting GPS status
	    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    // getting network status
	    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	    if (!isGPSEnabled && !isNetworkEnabled)
	    {
		Log.i(LOGTAG, "No network provider is enabled");
	    }
	    else
	    {
		this.canGetLocation = true;
		if (isNetworkEnabled)
		{
		    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		    Log.d(LOGTAG, "Network Enabled");
		    if (locationManager != null)
		    {
			tempLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		    }
		}
		
		if (isGPSEnabled)
		{
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		    Log.d(LOGTAG, "GPS Enabled");
		    if (locationManager != null)
		    {
			tempLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    }
		}
	    }
	    onLocationChanged(tempLocation);
	}
	catch (Exception e)
	{
	    Log.e(LOGTAG, e.getStackTrace().toString());
	    return null;
	}
	if (location != null)
	{
	    Log.d(LOGTAG, "INIT Lat => " + location.getLatitude() + "Long => " + location.getLongitude());
	}
	
	return location;
    }
    
    /******** LOCATIONLISTENER METHODS ********/
    
    @Override
    public void onLocationChanged(Location newLocation)
    {
	Log.d(LOGTAG, "Old location => " + location);
	Log.d(LOGTAG, "New location => " + newLocation);
	// CASES WHERE WE ONLY HAVE ONE OR THE OTHER.
	if (newLocation != null && location == null)
	{
	    Log.d(LOGTAG, "Last location null");
	    this.location = newLocation;
	    return;
	}
	else if (newLocation == null)
	{
	    Log.d(LOGTAG, "updated location is null");
	    return;
	}
	
        doLocationChangedAction(newLocation);
    }
    
    synchronized public void doLocationChangedAction(Location location)
    {
	if (location != null)
	{
	    this.location = location;
	    
	    MyPosition objMyPosition = new MyPosition();
	    objMyPosition.setLat(String.valueOf(location.getLatitude()));
	    objMyPosition.setLon(String.valueOf(location.getLongitude()));
	    objMyPosition.setImeiNo(utility.getDeviceIMEINo(mContext));
	    
	    PostLocationUpdate postLocationUpdateAsyncTask = new PostLocationUpdate(objMyPosition);
	    postLocationUpdateAsyncTask.execute();
	}
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
    
    /*********************************/
    
    /******** SERVICE METHODS ********/
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        this.mContext = getBaseContext();
	registerLocationListner();
	
        return Service.START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent arg0)
    {
	return null;
    }
    
    /*********************************/

}
