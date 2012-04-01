package com.ut.bataapp.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ut.bataapp.R;
import com.ut.bataapp.weer.WeerException;
import com.ut.bataapp.weer.WeerInfo;
import com.ut.bataapp.weer.WeerProvider;

public class WeerAdviesFragment extends SherlockFragment {
	private OnRequestWeerProvider mRequestWeerProvider;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mRequestWeerProvider = (OnRequestWeerProvider) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnRequestWeerProvider");
        }
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.weer_advies, container, false);
    }
      
    public interface OnRequestWeerProvider {
    	public WeerProvider onRequestWeerProvider();
    }
    
    @Override
	public void onResume() {
		super.onResume();
		TextView huidigHeader = (TextView) getActivity().findViewById(R.id.weer_huidig),
				 huidigTemp = (TextView) getActivity().findViewById(R.id.weer_huidig_temp),
				 verwachtMaxTemp = (TextView) getActivity().findViewById(R.id.weer_max_verwacht_temp),
				 advies = (TextView) getActivity().findViewById(R.id.weer_advies);
		ImageView huidigIcoon = (ImageView) getActivity().findViewById(R.id.weer_huidig_icoon),
				  verwachtIcoon = (ImageView) getActivity().findViewById(R.id.weer_verwacht_icoon);
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		int loc;
		String locDesc;
		if (hour >= 21 || hour < 8) {
			loc = WeerProvider.NIJMEGEN;
			locDesc = "Nijmegen";
		} else if (hour < 14) {
			loc = WeerProvider.GROENLO;
			locDesc = "Groenlo";
        } else {
			loc = WeerProvider.ENSCHEDE;
			locDesc = "Enschede";
		}
		
		WeerProvider provider = mRequestWeerProvider.onRequestWeerProvider();
		
		huidigHeader.setText(huidigHeader.getText() + locDesc);
		try {
			WeerInfo huidig = provider.getHuidig(loc);
			huidigTemp.setText(huidig.getTemp() + "°C");
			huidigIcoon.setImageBitmap(new getBitmap().execute(huidig.getURL()).get());
		} catch (WeerException e) {
			huidigTemp.setText("niet bekend");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, 1);
		try {
			WeerInfo max = provider.getVerwachting(now.getTime());
			verwachtMaxTemp.setText(max.getTemp() + "°C");
			verwachtIcoon.setImageBitmap(new getBitmap().execute(max.getURL()).get());
			float maxTemp = Float.parseFloat(max.getTemp());
			if (maxTemp < 15)
				advies.setText(R.string.adviesmin15);
			else if (maxTemp < 20)
				advies.setText(R.string.advies1520);
			else if (maxTemp < 25)
				advies.setText(R.string.advies2025);
			else
				advies.setText(R.string.advies25plus);
		} catch (WeerException e) {
			verwachtMaxTemp.setText("niet bekend");
			advies.setText("niet bekend");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
    
    private class getBitmap extends AsyncTask<String, Void, Bitmap> {
    	protected Bitmap doInBackground(String... arg) {
    		try {
				return BitmapFactory.decodeStream((InputStream)new URL(arg[0]).getContent());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
    	}
    }
}