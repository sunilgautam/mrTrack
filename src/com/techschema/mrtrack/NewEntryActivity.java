package com.techschema.mrtrack;

import org.apache.commons.validator.routines.RegexValidator;
import com.app.async.PostNewEntry;
import com.app.pojo.NewEntry;
import com.app.utility.AppUtility;
import com.app.utility.LocationHelper;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class NewEntryActivity extends Activity
{
    private final static String LOGTAG = NewEntryActivity.class.getName();
    private PostNewEntry postNewEntryAsyncTask;
    private ProgressDialog dialog;
    private AppUtility utility = new AppUtility();
    public NewEntry objNewEntry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_new_entry);
	
	postNewEntryAsyncTask = new PostNewEntry(this);
    }
    
    public void btnSaveClick(View view)
    {
	Resources resources = getResources();
	
	EditText eName = (EditText)findViewById(R.id.txtNewEntryName);
	EditText eMobile = (EditText)findViewById(R.id.txtNewEntryMobile);
	EditText eComment = (EditText)findViewById(R.id.txtNewEntryComment);
	
	RegexValidator mobile_validator = new RegexValidator("^\\d{10}$", false);

	if (eName.getText().toString().trim().equals(""))
	{
	    eName.setError(resources.getString(R.string.val_msg_name));
	}
	else if (eMobile.getText().toString().trim().equals(""))
	{
	    eMobile.setError(resources.getString(R.string.val_msg_mobile));
	}
	else if (!mobile_validator.isValid(eMobile.getText().toString()))
	{
	    eMobile.setError(resources.getString(R.string.val_msg_valid_mobile));
	}
	else
	{
	    showProgressDialog();
	    this.objNewEntry = new NewEntry();
	    this.objNewEntry.setName(eName.getText().toString());
	    this.objNewEntry.setMobileNo(eMobile.getText().toString());
	    this.objNewEntry.setComments(eComment.getText().toString());
	    this.objNewEntry.setImeiNo(utility.getDeviceIMEINo(this));
	    
	    LocationHelper locHelper = new LocationHelper();
	    Location currentLocation = locHelper.getCurrentLocation(this);
	    if (currentLocation != null)
	    {
		this.objNewEntry.setLat(String.valueOf(currentLocation.getLatitude()));
		this.objNewEntry.setLon(String.valueOf(currentLocation.getLongitude()));
	    }
	    postNewEntryAsyncTask.execute();	    
	}
    }

    public void btnCancelClick(View view)
    {
	finish();
    }
    
    public void showProgressDialog()
    {
	Resources resources = getResources();
	// Hide keyboard before
	InputMethodManager inputManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);

	//check if no view has focus:
	View v = this.getCurrentFocus();
	if(v != null)
	{
	    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	dialog = new ProgressDialog(this);
        dialog.setMessage(resources.getString(R.string.progress_new_entry));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }
    
    public void doneProgressing()
    {
	hideProgressDialog();
	Log.d(LOGTAG, "ENTRY SAVED");
	Log.d(LOGTAG, this.objNewEntry.toString());
	Toast.makeText(getBaseContext(), R.string.msg_save_success_new_entry, Toast.LENGTH_LONG).show();
	finish();
    }
    
    public void hideProgressDialog()
    {
	dialog.cancel();
    }

}
