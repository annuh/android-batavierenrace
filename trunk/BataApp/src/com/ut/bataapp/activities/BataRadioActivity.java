package com.ut.bataapp.activities;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.ut.bataapp.services.BataRadioService;

public class BataRadioActivity extends SherlockActivity {
	public static final String LAST_QUAL_SELECTION = "LQS";
	
	private Spinner mSpinner;
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (BataRadioService.class.getCanonicalName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private void startMP() {
		Intent intent = new Intent(this, BataRadioService.class);
    	intent.putExtra(BataRadioService.MP_URL, getResources().getStringArray(R.array.url_livestream)[mSpinner.getSelectedItemPosition()]);
    	startService(intent);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	getSupportActionBar().setTitle(R.string.dashboard_bataradio);
        setContentView(R.layout.bataradio);
        mSpinner = (Spinner) findViewById(R.id.spinner_quality);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bataradio_kwaliteit_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(getPreferences(MODE_PRIVATE).getInt(LAST_QUAL_SELECTION, getResources().getInteger(R.integer.spinner_quality_default)));
        mSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		ToggleButton button = (ToggleButton) findViewById(R.id.toggle_radio);
        button.setChecked(isMyServiceRunning());
	}
	
	public void onToggleClicked(View v) {
	    if (((ToggleButton) v).isChecked())
	    	startMP();
	    else
	        stopService(new Intent(this, BataRadioService.class));
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	if (pos != getPreferences(MODE_PRIVATE).getInt(LAST_QUAL_SELECTION, getResources().getInteger(R.integer.spinner_quality_default))) {
	    		SharedPreferences settings = getPreferences(MODE_PRIVATE);
	    		SharedPreferences.Editor editor = settings.edit();
	    		editor.putInt(LAST_QUAL_SELECTION, pos);
	    		editor.commit();
		    	if (isMyServiceRunning()) {
		    		stopService(new Intent(BataRadioActivity.this, BataRadioService.class));
		    		startMP();
		    	}
	    	}
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
}