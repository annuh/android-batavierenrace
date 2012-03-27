/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ut.bataapp.activities;

import java.io.IOException;
import java.util.ArrayList;

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
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

public class WeerActivity extends SherlockFragmentActivity {
   
	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
	private Document weerXML;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.dashboard_weer);
        setContentView(R.layout.simple_tabs);
        
        mAdapter = new WeerFragmentAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		
		new getBRXML().execute();
    }
    
    class WeerFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public WeerFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new WeerAdviesFragment());
			titels.add("Advies");
			fragments.add(new WeerVerwachtingFragment());
			titels.add("Verwachting");
			fragments.add(new WeerBuienradarFragment());
			titels.add("Buienradar");
		}
		
		
		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public String getTitle(int position) {
			return titels.get(position);
		}
	}
    
    private class getBRXML extends AsyncTask<Void, Void, String> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(WeerActivity.this,  
			  "Bezig met laden", "Weergegevens worden opgehaald...", true);  
		}
				
		@Override  
		protected String doInBackground(Void... arg0) {  
			String result = null;
			try {
				weerXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("http://xml.buienradar.nl/");
			} catch (IOException e) {
				result = "Kan XML niet downloaden: " + e;
			} catch (SAXException e) {
				result = "Kan XML niet parsen: " + e;
			} catch (ParserConfigurationException e) {
				result = "Kan XML-parser niet starten: " + e;
			}
			return result;
		}
		
		@Override  
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (result == null)
				Toast.makeText(getApplicationContext(), "XML succesvol opgehaald", Toast.LENGTH_SHORT).show();
			else {
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				setResult(RESULT_CANCELED);
				finish();
			}
		}
	}
}
