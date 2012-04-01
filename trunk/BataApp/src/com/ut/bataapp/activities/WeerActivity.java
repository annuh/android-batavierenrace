package com.ut.bataapp.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ut.bataapp.fragments.WeerAdviesFragment;
import com.ut.bataapp.fragments.WeerBuienradarFragment;
import com.ut.bataapp.fragments.WeerVerwachtingFragment;
import com.ut.bataapp.weer.WeerBuienradarGoogle;
import com.ut.bataapp.weer.WeerException;
import com.ut.bataapp.weer.WeerProvider;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

/**
 * Activity voor het weergedeelte. Bevat drie tabbladen (fragments): advies, verwachting en buienradar. 
 * Bij starten activity worden de laatste XML-weergegevens (Google en Buienradar) opgehaald.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class WeerActivity extends SherlockFragmentActivity implements WeerAdviesFragment.OnRequestWeerProvider {
	private WeerProvider mWeerProvider;
    
    /**
     * Callback-methode creëren activity. Layout wordt opgebouwd.
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.dashboard_weer);
        setContentView(R.layout.simple_tabs);
        
        // opbouwen tabbladen:
        FragmentPagerAdapter mAdapter = new WeerFragmentAdapter(getSupportFragmentManager());
		ViewPager mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		PageIndicator mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
    }
    
    /**
     * Callback-methode resumen activity. XML-weergegevens (Google en Buienradar) worden opgehaald.
     */
    @Override
	protected void onResume() {
    	super.onResume();
    	try {
			Document[] documents = new getXML().execute(getResources().getString(R.string.url_xml_buienradar), getResources().getString(R.string.url_xml_google)).get();
			mWeerProvider = new WeerBuienradarGoogle(documents[0], documents[1], this);
    	} catch (InterruptedException e) {
			setResult(RESULT_CANCELED);
			finish();
		} catch (ExecutionException e) {
			setResult(RESULT_CANCELED);
			finish();
		} catch (CancellationException e) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}
    
    public WeerProvider onRequestWeerProvider() {
    	return mWeerProvider;
    }
    
    /* Adapterklasse voor fragments -> pager.
     * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1
     */
    private class WeerFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		/* Lijst van alle tabs (fragments) in de pager
		 * @invariant fragments != null
		 * @invariant voor alle 0 <= i < fragments.size(): fragments.get(i) != null
		 */
    	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    	/* Lijst van alle titels in de pager
		 * @invariant titels != null
		 * @invariant voor alle 0 <= i < titels.size(): titels.get(i) != null
		 */
		private ArrayList<String> titels = new ArrayList<String>();
		
		/**
		 * Constructor voor deze adapter. Bouwt de adapter op met alle tabs (fragments) en titels.
		 * @param fm manager voor de fragments
		 * @require fm != null
		 */
		public WeerFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new WeerAdviesFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_advies));
			fragments.add(new WeerVerwachtingFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_verwachting));
			fragments.add(new WeerBuienradarFragment());
			titels.add(getResources().getString(R.string.tabtitel_weer_buienradar));
		}
		
		/**
		 * Geeft het Fragment op positie position terug.
		 * @param position positie van fragment
		 * @return Fragment op positie position
		 * @require 0 <= position < getCount()
		 * @ensure result != null
		 */
		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		/**
		 * Geeft het aantal fragments terug.
		 * @return aantal fragments
		 * @ensure result >= 0
		 */
		@Override
		public int getCount() {
			return fragments.size();
		}

		/**
		 * Geeft de titel van het fragment (de tab) op positie position.
		 * @param position positie van fragment (tab)
		 * @return de titel van het fragment (de tab) op positie position
		 * @require 0 <= position < getCount()
		 * @ensure result != null
		 */
		@Override
		public String getTitle(int position) {
			return titels.get(position);
		}
	}
    
    /* Klasse voor het binnenhalen van XML-documenten.
     */
    private class getXML extends AsyncTask<String, Void, Document[]> {  
		private ProgressDialog progressDialog;  
		
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(WeerActivity.this, getResources().getString(R.string.bezig_met_laden), 
			  getResources().getString(R.string.ophalen_weergegevens), true);  
		}
				
		@Override
		protected Document[] doInBackground(String... arg) {  
			Document[] result = new Document[arg.length];
			
			try {
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				int i=0;
				while (i<arg.length && !isCancelled()) {
					try {
						result[i] = documentBuilder.parse(arg[i]);
					} catch (IOException e) {
						Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.error_cant_download_xml), e), Toast.LENGTH_LONG).show();
						cancel(true);
					} catch (SAXException e) {
						Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.error_cant_parse_xml), e), Toast.LENGTH_LONG).show();
						cancel(true);
					}
					i++;
				}			
			} catch (ParserConfigurationException e) {
				Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.error_cant_start_xml_parser), e), Toast.LENGTH_LONG).show();
				cancel(true);
			}
			
			return result;
		}
		
		@Override  
		protected void onPostExecute(Document[] result) {
			progressDialog.dismiss();
		}
		
		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
		}
	}
}