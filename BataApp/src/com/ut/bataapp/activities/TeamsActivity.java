package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.R;
import com.ut.bataapp.MainActivity;
import com.ut.bataapp.MainActivity.OverridePendingTransition;
import com.ut.bataapp.adapters.TeamAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Team;

public class TeamsActivity extends SherlockListActivity  {
	
	 private final int MENU_SEARCH = Menu.FIRST;
	 private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	 private final int MENU_SORT_START = Menu.FIRST + 2;
	 private ArrayList<Team> teams = null;
	 private EditText filterText = null;
	 private TeamAdapter adapter = null;
	 
	 public void setTeams(ArrayList<Team> arrayList){
		 this.teams=arrayList;
	 }
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   setTitle("Teams");
	   super.onCreate(savedInstanceState);
	   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   this.getListView().setFastScrollEnabled(true);
	   new getTeams().execute();  
   }
   
   @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
	   Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
       intent.putExtra("index", position);
       startActivity(intent);
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {	
 	   menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
 	   	.setIcon(R.drawable.ic_action_search)
 	   	//.setActionView(R.layout.search_box)
 	   	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
 	   
 	  SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
      subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
      subMenu1.add(0,MENU_SORT_START,Menu.NONE, "Startnummer");

     subMenu1.getItem()
      .setIcon(R.drawable.ic_action_sort)
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 	   return true;
    }
   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }
	            break;
			case MENU_SEARCH:
				item.setActionView(R.layout.search_box);
				filterText = (EditText) item.getActionView().findViewById(R.id.search_box);
				filterText.addTextChangedListener(filterTextWatcher);
				filterText.requestFocus();
				InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm != null) {
				    imm.showSoftInput(filterText, 0); 
				}
				break;
			case MENU_SORT_NAAM:
				Collections.sort(teams,new Comparator<Team>() {
				    public int compare(Team arg0, Team arg1) {
				    	return arg1.getNaam().compareTo(arg0.getNaam());
				    }
				});

				/*adapter.sort(new Comparator<Team>() {
				    public int compare(Team arg0, Team arg1) {
				    	return arg0.getNaam().compareTo(arg1.getNaam());
				    }
				});*/
				adapter = new TeamAdapter(TeamsActivity.this, teams);
			    setListAdapter(adapter);
			    adapter.notifyDataSetChanged();
				break;
			case MENU_SORT_START:
				/*adapter.sort(new Comparator<Team>() {
				    public int compare(Team arg0, Team arg1) {
				    	return (arg0.getStartnummer()>arg1.getStartnummer() ? -1 : (arg0.getStartnummer()==arg1.getStartnummer() ? 0 : 1));
				    }
				});*/
				Collections.sort(teams,new Comparator<Team>() {
				    public int compare(Team arg0, Team arg1) {
				    	return (arg0.getStartnummer()>arg1.getStartnummer() ? -1 : (arg0.getStartnummer()==arg1.getStartnummer() ? 0 : 1));
				    }
				});
				adapter = new TeamAdapter(TeamsActivity.this, teams);
			    setListAdapter(adapter);
			    adapter.notifyDataSetChanged();
			    
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class getTeams extends AsyncTask<Void, Void, Void> {  
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {  
			progressDialog = ProgressDialog.show(TeamsActivity.this,  
			  "Bezig met laden", "Teams worden opgehaald...", true);  
		}
		
		@Override  
		protected Void doInBackground(Void... arg0) {  
			teams = api.getTeams();
			return null;       
		}
		
		@Override  
		protected void onPostExecute(Void result) {
			adapter = new TeamAdapter(TeamsActivity.this, teams);
			setListAdapter(adapter);
			progressDialog.dismiss();
		}
	}
	
	private TextWatcher filterTextWatcher = new TextWatcher() {

	    public void afterTextChanged(Editable s) {
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before,
	            int count) {
	        adapter.getFilter().filter(s);
	    }

	};
}
