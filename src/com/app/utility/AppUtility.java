package com.app.utility;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AppUtility
{
    private final static String LOGTAG = AppUtility.class.getName();
    
    public String getDeviceIMEINo(Context mContext)
    {
	String imei_no = null;
	try
	{
	    TelephonyManager telephonyManager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
	    imei_no = telephonyManager.getDeviceId();
	}
	catch(Exception ex)
	{
	    Log.e(LOGTAG, "Error occurred while getting device IMEI number.");
	    imei_no = "";
	}
	return imei_no;
    }
}
