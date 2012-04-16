package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.fragments.LayoutFragment;
import com.ut.bataapp.fragments.SponsorInformatieFragment;
import com.ut.bataapp.objects.Sponsor;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class SponsorInformatieActivity extends SherlockFragmentActivity {
	
	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
    	super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.simple_tabs);
		
        mAdapter = new SponsorInformatieAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		
		
		String page = "";
		page = getIntent().getStringExtra("page");
		int pageid = 0;
		pageid = Integer.parseInt(page) -1;
		
		mPager.setCurrentItem(pageid);
		mIndicator.setCurrentItem(pageid);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				/*
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }*/
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
