package com.ut.bataapp.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.SponsorInformatieFragment;
import com.ut.bataapp.objects.Sponsor;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;

public class SponsorInformatieActivity extends SherlockFragmentActivity {
	
	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
    	super.onCreate(savedInstanceState);
    	getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.simple_tabs);
		
        mAdapter = new SponsorInformatieAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		
		
		int pageid = getIntent().getIntExtra("page", 0);
		
		mPager.setCurrentItem(pageid);
		mIndicator.setCurrentItem(pageid);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Utils.goHome(this);
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    class SponsorInformatieAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public SponsorInformatieAdapter(FragmentManager fm) {
			super(fm);			
						
			for(int i = 0; i< 11; i++){
				SponsorInformatieFragment spFrag = new SponsorInformatieFragment();
				Bundle b = new Bundle();
				b.putInt("page", i);
				spFrag.setArguments(b);
				fragments.add(spFrag);
				titels.add((Sponsor.getSponsors().get(i)).getNaam());
			}
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
