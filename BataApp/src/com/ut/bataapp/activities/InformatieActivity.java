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

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.Utils;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.fragments.InfoAlgemeenFragment;
import com.ut.bataapp.fragments.InfoCalamiteitenFragment;
import com.ut.bataapp.fragments.InfoColofonFragment;
import com.ut.bataapp.fragments.InfoOverzichtskaartenFragment;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class InformatieActivity extends SherlockFragmentActivity {
   
	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.dashboard_informatie);
        setContentView(R.layout.simple_tabs);
		
        mAdapter = new InformatieFragmentAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(getApplicationContext());
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    class InformatieFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public InformatieFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new InfoAlgemeenFragment());
			titels.add("Algemeen");
			fragments.add(new InfoCalamiteitenFragment());
			titels.add("Calamiteiten");
			fragments.add(new InfoOverzichtskaartenFragment());
			titels.add("Overzichtkaart");
			fragments.add(new InfoColofonFragment());
			titels.add("Colofon");
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
}
