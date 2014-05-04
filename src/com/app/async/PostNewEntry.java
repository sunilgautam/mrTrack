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
import com.app.pojo.NewEntry;
import com.techschema.mrtrack.NewEntryActivity;
import android.os.AsyncTask;
import android.util.Log;

public class PostNewEntry extends AsyncTask<String, Integer, Boolean>
{
    private final static String LOGTAG = PostNewEntry.class.getName();
    private final static String ENTRY_POST_URL = "http://india-ghar.com/submit-new-entry.php";
    private NewEntryActivity uiThread;

    public PostNewEntry(NewEntryActivity m)
    {
	Log.d(LOGTAG, "Initialized Async task object to post data");
	this.uiThread = m;
    }

    // runs on UI thread
    @Override
    protected void onPreExecute()
    {
	Log.d(LOGTAG, "PreExecute Async task object");
    }

    // runs on a separate single worker thread
    @Override
    protected Boolean doInBackground(String... params)
    {
	Log.d(LOGTAG, "Performing Async task");
	callWebService();
	publishProgress(100);
	Log.d(LOGTAG, "Async task Done");
	return Boolean.valueOf(true);
    }

    // runs on UI thread
    @Override
    protected void onProgressUpdate(Integer... values)
    {
	Log.d(LOGTAG, "onProgressUpdate on UI thread");
    }

    // runs on UI thread
    @Override
    protected void onPostExecute(Boolean result)
    {
	Log.d(LOGTAG, "onPostExecute on UI thread");
	uiThread.doneProgressing();
    }

    // runs on UI thread, if cancelled instead of onPostExecute() , this will be
    // called from doInBackground()
    @Override
    protected void onCancelled()
    {
	Log.d(LOGTAG, "onCancelled on UI thread");
	uiThread.hideProgressDialog();
    }

    private String callWebService()
    {
	String response = null;
	HttpClient client = null;
	try
	{
	    client = new DefaultHttpClient();
	    HttpPost request = new HttpPost(ENTRY_POST_URL);

	    NewEntry objData = this.uiThread.objNewEntry;

	    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	    pairs.add(new BasicNameValuePair("name", objData.getName()));
	    pairs.add(new BasicNameValuePair("mobile_no", objData.getMobileNo()));
	    pairs.add(new BasicNameValuePair("comments", objData.getComments()));
	    pairs.add(new BasicNameValuePair("imei_no", objData.getImeiNo()));
	    pairs.add(new BasicNameValuePair("lat", objData.getLat()));
	    pairs.add(new BasicNameValuePair("lon", objData.getLon()));
	    pairs.add(new BasicNameValuePair("_", String.valueOf(Calendar.getInstance().getTimeInMillis())));

	    request.setEntity(new UrlEncodedFormEntity(pairs));

	    Log.d(LOGTAG, "Sending data to web service");
	    HttpResponse httpResponse = client.execute(request);
	    int responseCode = httpResponse.getStatusLine().getStatusCode();
	    Log.i(LOGTAG, "response code: " + responseCode);
	    // String message = httpResponse.getStatusLine().getReasonPhrase();
	    // HttpEntity entity = httpResponse.getEntity();
	    // if (entity != null)
	    // {
	    // InputStream instream = entity.getContent();
	    // response = convertStreamToString(instream);
	    // instream.close();
	    // }
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