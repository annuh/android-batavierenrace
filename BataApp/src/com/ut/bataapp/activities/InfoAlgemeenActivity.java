package com.ut.bataapp.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.fragments.LayoutFragment;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.actionbarsherlock.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class InfoAlgemeenActivity extends SherlockFragmentActivity {

	public static final String TAB = "tabid";
	public static final int FRAGMENT_WATMOETJEWETEN = 0;
	public static final int FRAGMENT_BUSTIJDEN = 1;
	public static final int FRAGMENT_SLAPEN = 2;

	ViewPager mPager;
	PageIndicator mIndicator;
	AlgemeenFragmentAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setContentView(R.layout.simple_tabs);
		setTitle(R.string.dashboard_informatie);

		mAdapter = new AlgemeenFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mIndicator = (TabPageIndicator)findViewById(R.id.indicator);

		int pageid = (savedInstanceState != null) ? savedInstanceState.getInt("tabid") : getIntent().getIntExtra("tabid", 0);

		mPager.setCurrentItem(pageid);
		mIndicator.setViewPager(mPager);
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

	class AlgemeenFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {

		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();

		public AlgemeenFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new LayoutFragment(R.layout.info_algemeen_watmoetjeweten));
			titels.add("Belangrijk");
			fragments.add(new LayoutFragment(R.layout.info_algemeen_busenstarttijden));
			titels.add("Tijden");
			fragments.add(new LayoutFragment(R.layout.info_algemeen_slapen));
			titels.add("Slapen");
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

		public void deleteAll(FragmentManager fm) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment fragment: fragments)
				ft.remove(fragment);
			ft.commit();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("tabid", (mPager == null ? 0 : mPager.getCurrentItem()));
	}
}
