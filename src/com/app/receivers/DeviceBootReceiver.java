package com.app.receivers;

import com.app.services.LocationTrackerService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceBootReceiver extends BroadcastReceiver
{
    private final static String LOGTAG = DeviceBootReceiver.class.getName();
    public DeviceBootReceiver()
    {
	
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
	Log.i(LOGTAG, "----------|| BOOT_COMPLETE ||----------");
	Log.i(LOGTAG, "STARTING LOCATION TRACKER SERVICE ...");
	
	Intent service = new Intent(context, LocationTrackerService.class);
	context.startService(service);
    }
}
