package com.techschema.mrtrack;

import com.app.services.LocationTrackerService;
import com.app.widget.MessageDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;

public class DashboardActivity extends Activity
{
    private final static String LOGTAG = DashboardActivity.class.getName();
    MessageDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_dashboard);
	
	// CHECK IS TRACKER SERVICE IS RUNNING
	if (!isTrackerServiceRunning())
	{
	    Log.i(LOGTAG, "LOCATION TRACKER SERVICE NOT RUNNING");
	    Log.i(LOGTAG, "STARTING LOCATION TRACKER SERVICE ...");
	    Intent service = new Intent(this, LocationTrackerService.class);
	    startService(service);
	}
	else
	{
	    Log.i(LOGTAG, "LOCATION TRACKER SERVICE RUNNING");
	}
    }
    
    private boolean isTrackerServiceRunning()
    {
	try
	{
	    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
	    {
		if (LocationTrackerService.class.getName().equals(service.service.getClassName()))
		{
		    return true;
		}
	    }
	}
	catch (Exception ex)
	{
	    ex.printStackTrace();
	}
	return false;
    }
    
    public void btnNewEntryClick(View view)
    {
	try
	{
	    Intent intent = new Intent(DashboardActivity.this, NewEntryActivity.class);
	    //Intent intent = new Intent(DashboardActivity.this, ShowLocationActivity.class);
	    startActivity(intent);
	}
	catch (Exception ex)
	{
	    dialog = new MessageDialog(getString(R.string.msg_err_open_act), getString(R.string.msg_dialog_title_error), MessageDialog.MESSAGE_ERROR, DashboardActivity.this);
	}
    }

}
