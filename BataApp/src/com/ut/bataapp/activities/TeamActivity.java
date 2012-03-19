package com.ut.bataapp.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.R;
import com.ut.bataapp.api.api;
import com.ut.bataapp.fragments.ContactFragment;
import com.ut.bataapp.fragments.TeamInformatieFragment;
import com.ut.bataapp.objects.Team;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitleProvider;
import com.viewpagerindicator.PageIndicator;

public class TeamActivity extends SherlockFragmentActivity {
	
	ViewPager mPager;
	PageIndicator mIndicator;
	FragmentPagerAdapter mAdapter;
	private final int MENU_FOLLOW = Menu.FIRST;
	private Team team = null;
	private int team_id;
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public Team getTeam(){
		return team;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        team_id = this.getIntent().getIntExtra("index", 0) + 1;
        new getTeam().execute();
        
        
    }
	   
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {	
	 	   menu.add(0,MENU_FOLLOW,Menu.NONE, R.string.ab_volgen)
	 	   	.setIcon(R.drawable.ic_action_star)
	 	   	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	 	   
	 	   return true;
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, TeamsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }
	            break;
			case MENU_FOLLOW:
				Toast toast = Toast.makeText(this, "U volgt dit team nu.", Toast.LENGTH_SHORT);
				toast.show();
				SharedPreferences keyValues = this.getSharedPreferences("teams_follow", Context.MODE_PRIVATE);
				SharedPreferences.Editor keyValuesEditor = keyValues.edit();
				keyValuesEditor.putInt(team.getNaam(), team.getStartnummer());	
				keyValuesEditor.commit();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	class TeamFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
		
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ArrayList<String> titels = new ArrayList<String>();
		
		public TeamFragmentAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new TeamInformatieFragment());
			titels.add("Informatie");
			fragments.add(new ContactFragment());
			titels.add("Routetijden");
			fragments.add(new ContactFragment());
			titels.add("Klassement");
			fragments.add(new ContactFragment());
			titels.add("Informatie");
			fragments.add(new ContactFragment());
			titels.add("Informatie");
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
	
	private class getTeam extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamActivity.this,  
			  "Bezig met laden", "Team wordt opgehaald...", true);  
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			
			team = api.getTeamByID(team_id);
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			setContentView(R.layout.simple_tabs);
			
	        mAdapter = new TeamFragmentAdapter(getSupportFragmentManager());
			
			mPager = (ViewPager)findViewById(R.id.pager);
			mPager.setAdapter(mAdapter);
			
			mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
			mIndicator.setViewPager(mPager);
		
			progressDialog.dismiss();
		}
	}

}
