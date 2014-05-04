package com.app.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.app.pojo.MyPosition;
import android.os.AsyncTask;
import android.util.Log;

public class PostLocationUpdate extends AsyncTask<String, Integer, Boolean>
{
    private final static String LOGTAG = PostLocationUpdate.class.getName();
    private final static String ENTRY_POST_URL = "http://india-ghar.com/submit-loc-entry.php";
    private MyPosition objMyPosition;
    
    public PostLocationUpdate(MyPosition objMyPosition)
    {
	Log.d(LOGTAG, "Initialized Async task object to post location updates");
	this.objMyPosition = objMyPosition;
    }

    @Override
    protected void onPreExecute()
    {
	Log.d(LOGTAG, "PreExecute Async task object");
    }
    
    @Override
    protected void onPostExecute(Boolean result)
    {
	Log.d(LOGTAG, "onPostExecute Async task object");
    }
    
    @Override
    protected void onProgressUpdate(Integer... values)
    {
	Log.d(LOGTAG, "onProgressUpdate Async task object");
    }
    
    @Override
    protected void onCancelled()
    {
	Log.d(LOGTAG, "onCancelled Async task object");
    }
    
    @Override
    protected Boolean doInBackground(String... arg0)
    {
	Log.d(LOGTAG, "Performing Async task");
	callWebService();
	publishProgress(100);
	Log.d(LOGTAG, "Async task Done");
	Log.d(LOGTAG, "Location Posted : \n" + objMyPosition.toString());
	return Boolean.valueOf(true);
    }
    
    private String callWebService()
    {
	String response = null;
	HttpClient client = null;
	try
	{
	    client = new DefaultHttpClient();
	    HttpPost request = new HttpPost(ENTRY_POST_URL);

	    MyPosition objData = this.objMyPosition;

	    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	    pairs.add(new BasicNameValuePair("imei_no", objData.getImeiNo()));
	    pairs.add(new BasicNameValuePair("lat", objData.getLat()));
	    pairs.add(new BasicNameValuePair("lon", objData.getLon()));
	    pairs.add(new BasicNameValuePair("_", String.valueOf(Calendar.getInstance().getTimeInMillis())));

	    request.setEntity(new UrlEncodedFormEntity(pairs));

	    Log.d(LOGTAG, "Sending data to web service");
	    HttpResponse httpResponse = client.execute(request);
	    int responseCode = httpResponse.getStatusLine().getStatusCode();
	    Log.i(LOGTAG, "response code: " + responseCode);
	}
	catch (ClientProtocolException e)
	{
	    Log.e(LOGTAG, "protocol error, check connection");
	    client.getConnectionManager().shutdown();
	}
	catch (IOException e)
	{
	    Log.e(LOGTAG, "io error, have u added the permision: android.permission.INTERNET ");
	    client.getConnectionManager().shutdown();
	}
	Log.i(LOGTAG, "response: " + response);
	return response;
    }

}
