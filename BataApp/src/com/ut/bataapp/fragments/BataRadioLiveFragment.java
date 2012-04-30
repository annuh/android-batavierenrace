package com.ut.bataapp.fragments;

import com.ut.bataapp.R;
import com.ut.bataapp.services.BataRadioService;

import android.app.*;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.*;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Klasse voor het representeren van een een BataRadio-livefragment. Retourneert bijbehorende layout/view.
 * Kan BataRadio-service opstarten en weer afsluiten.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class BataRadioLiveFragment extends SherlockFragment implements OnClickListener {	
	// -- INNER CLASSES --
	
	/* Receiver voor broadcast van stoppen BataRadioService. Wordt gebruikt om ToggleButton up-to-date te houden.
	 * Onderdeel van ontwerpproject BataApp.
	 * @author Danny Bergsma
	 * @version 0.1
	 */
	private class BataRadioStopped extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (!mForceStop)
				mToggleButton.setChecked(false);
			mForceStop = false;
		}
	}
	
	/* Listener voor keuzemenu (spinner).
	 * Onderdeel van ontwerpproject BataApp.
	 * @author Danny Bergsma
	 * @version 0.1
	 */
	private class MyOnItemSelectedListener implements OnItemSelectedListener {
	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	// optimalisaties:
	    	Activity activity = mActivity;
	    	Resources res = mRes;
	    	
	    	if (pos != activity.getPreferences(Activity.MODE_PRIVATE).getInt(res.getString(R.string.pref_bataradio_quality), 
	    			res.getInteger(R.integer.pref_bataradio_quality_default))) { // onItemSelected() niet resultaat van setSelected() in onCreateView()?
	    		// sla keuze op:
	    		SharedPreferences settings = activity.getPreferences(Activity.MODE_PRIVATE);
	    		SharedPreferences.Editor editor = settings.edit();
	    		editor.putInt(res.getString(R.string.pref_bataradio_quality), pos);
	    		editor.commit();
	    		// verander URL door oude service te stoppen en nieuwe (met nieuwe URL) te starten:
		    	if (isMyServiceRunning()) {
		    		mForceStop = true;
		    		activity.stopService(new Intent(activity, BataRadioService.class));
		    		startMP();
		    	}
	    	}
	    }

	    public void onNothingSelected(AdapterView parent) {
	    	// doe niets
	    }
	}
	
	// -- INSTANTIEVARIABELEN --
	
	/* keuzemenu voor kwaliteit */
	private Spinner mSpinner;
	/* aan/uit-knopje */
	private ToggleButton mToggleButton;
	/* parentactivity behorend bij dit fragment */
	private Activity mActivity;
	/* pointer naar app-resources */
	private Resources mRes;
	/* receiver voor broadcast van stoppen BataRadioService */
	private BroadcastReceiver mReceiveBataRadioStoppedBroadcast;
	/* geeft aan of stoppen service door activity zelf is gedaan in het kader van switchen naar andere stream */
	private boolean mForceStop;
	
	// -- HULPMETHODEN --
	
	/* Geeft terug of BataRadio-service aan het draaien is.
	 * @return of BataRadio-service aan het draaien is 
	 */
	private boolean isMyServiceRunning() {
	    boolean result = false;
		ActivityManager manager = (ActivityManager) mActivity.getSystemService(Activity.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
	        if (BataRadioService.class.getCanonicalName().equals(service.service.getClassName()))
	        	result = true;
	    return result;
	}
	
	/* Start BataRadio-service. Geeft aan service URL van stream door. */
	private void startMP() {
		Activity activity = mActivity;
		Intent intent = new Intent(activity, BataRadioService.class);
    	intent.putExtra(BataRadioService.MP_URL, mRes.getStringArray(R.array.url_livestream)[mSpinner.getSelectedItemPosition()]);
    	activity.startService(intent);
	}
		
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRes = getResources();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.bataradio_live, null);
				
		Activity activity = mActivity; // optimalisatie
		// opbouwen keuzemenu:
        Spinner spinner = (mSpinner = (Spinner) view.findViewById(R.id.spinner_quality));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.pref_bataradio_quality_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(activity.getPreferences(Activity.MODE_PRIVATE).getInt(mRes.getString(R.string.pref_bataradio_quality), mRes.getInteger(R.integer.pref_bataradio_quality_default)));
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        mToggleButton = (ToggleButton) view.findViewById(R.id.toggle_radio);
        mToggleButton.setOnClickListener(this);
        
        mReceiveBataRadioStoppedBroadcast = new BataRadioStopped();
        
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mToggleButton.setChecked(isMyServiceRunning());
		mActivity.registerReceiver(mReceiveBataRadioStoppedBroadcast, new IntentFilter(BataRadioService.BATARADIO_SERVICE_STOPPED));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mActivity.unregisterReceiver(mReceiveBataRadioStoppedBroadcast);
	}
		
	// -- CALLBACKMETHODEN --
	
	public void onClick(View v) { // toggleButton
	    if (((ToggleButton) v).isChecked())
	    	startMP();
	    else
	        mActivity.stopService(new Intent(mActivity, BataRadioService.class));
	}
}
