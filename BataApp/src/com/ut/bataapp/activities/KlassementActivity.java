package com.ut.bataapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.R;
import com.ut.bataapp.Utils;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.KlassementItem;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;

public class KlassementActivity extends SherlockListActivity  {

	private final int MENU_SEARCH = Menu.FIRST;
	private final int MENU_SORT_NAAM = Menu.FIRST + 1;
	private final int MENU_SORT_STAND = Menu.FIRST + 2;
	private EditText filterText = null;
	private String naam;
	private Klassement klassement;
	private KlassementAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle("Klassement");
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.getListView().setFastScrollEnabled(true);
		naam = this.getIntent().getStringExtra("index");
		this.setContentView(R.layout.listview_klassement);
		new getKlassement().execute();  	   
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
		menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
		.setIcon(R.drawable.ic_action_search)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
		subMenu1.add(0,MENU_SORT_STAND,Menu.NONE, "Plaats");

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(getApplicationContext());
			break;
		case MENU_SEARCH:
			item.setActionView(R.layout.search_box);
			filterText = (EditText) item.getActionView().findViewById(R.id.search_box);
			filterText.addTextChangedListener(filterTextWatcher);
			setKeyboardFocus(filterText);
			break;
		case MENU_SORT_NAAM:
			sortList(klassement.getUitslag(), "naam");
			break;
		case MENU_SORT_STAND:
			sortList(klassement.getUitslag(), "stand");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void setKeyboardFocus(final EditText primaryTextField) {
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
			}
		}, 100);
	}

	public void sortList(ArrayList<KlassementItem> list, final String sort) {
		Collections.sort(list,new Comparator<KlassementItem>() {
			public int compare(KlassementItem arg0, KlassementItem arg1) {
				if(sort.equals("naam"))
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				else if(sort.equals("stand"))
					return (arg0.getPlaats()<arg1.getPlaats() ? -1 : (arg0.getPlaats()==arg1.getPlaats() ? 0 : 1));
				else
					return 0;
			}
		});
		adapter = new KlassementAdapter(KlassementActivity.this, list);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	OnCancelListener cancelListener=new OnCancelListener(){
		@Override
		public void onCancel(DialogInterface arg0){
			finish();
		}
	};

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


	private class getKlassement extends AsyncTask<Void, Void, Void> {
		Response<Klassement> response;
		private ProgressDialog progressDialog;  
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(KlassementActivity.this,  
					"Bezig met laden", "Klassement wordt opgehaald...", true);
			progressDialog.setCancelable(true);
		    progressDialog.setOnCancelListener(new OnCancelListener() {
		        public void onCancel(DialogInterface dialog) {
		        	cancel(true);
		        	Utils.goHome(KlassementActivity.this);
		        }
		    });
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			while(!isCancelled())
				response = api.getKlassementByNaam(naam);
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(KlassementActivity.this, response)) {
				klassement = response.getResponse();
				adapter = new KlassementAdapter(KlassementActivity.this, klassement.getUitslag());
				setListAdapter(adapter);
				progressDialog.dismiss();
			}


		}
	}
}
