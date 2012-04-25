package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.InfoAlgemeenFragment;
import com.ut.bataapp.fragments.InfoCalamiteitenFragment;
import com.ut.bataapp.fragments.InfoColofonFragment;
import com.ut.bataapp.fragments.InfoOverzichtskaartenFragment;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;
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
			Utils.goHome(this);
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
