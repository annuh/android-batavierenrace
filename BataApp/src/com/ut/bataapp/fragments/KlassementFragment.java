package com.ut.bataapp.fragments;

import java.util.Collections;
import java.util.Comparator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Klassement;
import com.ut.bataapp.objects.KlassementItem;
import com.ut.bataapp.objects.Response;

public class KlassementFragment extends SherlockListFragment {

	private final int MENU_SEARCH = Menu.FIRST + 10;
	private final int MENU_SORT_NAAM = Menu.FIRST + 11;
	private final int MENU_SORT_STAND = Menu.FIRST + 12;
	private String filterText = "";
	private String naam;
	private Klassement klassement;
	private KlassementAdapter adapter = null;
	private char sortNaam = 'D';
	private char sortStand = 'D';

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		naam = this.getArguments().getString("index");
		new getKlassement().execute();  	
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.listview_klassement, null);
		view.findViewById(R.id.klassement_header_stand).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortStand(null);
			}
		});
		view.findViewById(R.id.klassement_header_team).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortNaam(null);
			}
		});
		
		view.findViewById(R.id.klassement_header_tijd).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortStand(null);
			}
		});
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
		.setIcon(R.drawable.ic_action_search)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, R.string.sort_naam);
		subMenu1.add(0,MENU_SORT_STAND,Menu.NONE, R.string.sort_stand);

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SEARCH:
			item.setActionView(R.layout.search_box);
			EditText filterEdit = (EditText) item.getActionView().findViewById(R.id.search_box);
			filterEdit.setText(filterText);
			filterEdit.addTextChangedListener(filterTextWatcher);
			setKeyboardFocus(filterEdit);
			break;
		case MENU_SORT_NAAM:
			sortNaam(null);
			break;
		case MENU_SORT_STAND:
			sortStand(null);
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

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			filterText= s.toString();
			adapter.getFilter().filter(s);
		}

	};


	private class getKlassement extends AsyncTask<Void, Void, Void> {
		Response<Klassement> response;
		private ProgressDialogFragment progressDialog;  
		protected void onPreExecute() {
			//setListShown(false); 
			progressDialog = ProgressDialogFragment.newInstance(getString(R.string.laden_titel), getString(R.string.klassement_laden));
			progressDialog.show(getActivity().getSupportFragmentManager(), "");
			/*progressDialog = ProgressDialog.show(getActivity().getApplicationContext(),  
					getString(R.string.laden_titel), getString(R.string.klassement_laden), true);*/
			/*progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(getActivity().getApplicationContext());
				}
			});*/
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getKlassementByNaam(naam);
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
				klassement = response.getResponse();
				Log.d("Klassement", ""+klassement.getUitslag().size());
				sortStand(null);
				getListView().setSelection(getArguments().getInt("init") -1);
				if(progressDialog != null)
					progressDialog.dismiss();
			}
		}
	}

	public void sortNaam(View v) {
		resetArrows();
		if(sortNaam == 'D') {
			Collections.sort( klassement.getUitslag(),new Comparator<KlassementItem>() {
				public int compare(KlassementItem arg0, KlassementItem arg1) {
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				}
			});
			sortNaam = 'A';
			sortStand = 'D';
		} else {
			Collections.sort( klassement.getUitslag(),new Comparator<KlassementItem>() {
				public int compare(KlassementItem arg0, KlassementItem arg1) {
					return arg1.getTeamNaam().compareTo(arg0.getTeamNaam());
				}
			});
			sortNaam = 'D';
			sortStand = 'D';
		}

		int i = this.getResources().getIdentifier("sort_"+sortNaam, "string", getActivity().getPackageName());
		((TextView) getView().findViewById(R.id.klassement_header_team)).setText(this.getText(R.string.klassement_header_team) +" "+ getText(i));
		adapter = new KlassementAdapter(getActivity().getApplicationContext(), klassement.getUitslag());
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void sortStand(View v) {
		resetArrows();
		if(sortStand == 'D') {
			Collections.sort( klassement.getUitslag(),new Comparator<KlassementItem>() {
				public int compare(KlassementItem arg0, KlassementItem arg1) {
					return (arg0.getPlaats()<arg1.getPlaats() ? -1 : (arg0.getPlaats()==arg1.getPlaats() ? 0 : 1));
				}
			});
			sortStand = 'A';
			sortNaam = 'D';

		} else {
			Collections.sort( klassement.getUitslag(),new Comparator<KlassementItem>() {
				public int compare(KlassementItem arg1, KlassementItem arg0) {
					return (arg0.getPlaats()<arg1.getPlaats() ? -1 : (arg0.getPlaats()==arg1.getPlaats() ? 0 : 1));
				}
			});
			sortStand = 'D';
			sortNaam = 'D';
		}

		int i = this.getResources().getIdentifier("sort_"+sortStand, "string", this.getActivity().getPackageName());
		((TextView) getView().findViewById(R.id.klassement_header_stand)).setText(this.getText(R.string.klassement_header_stand) +" "+ getText(i));
		adapter = new KlassementAdapter(getActivity().getApplicationContext(), klassement.getUitslag());
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();	
	}

	public void resetArrows() {
		((TextView) getView().findViewById(R.id.klassement_header_stand)).setText(this.getText(R.string.klassement_header_stand));
		((TextView) getView().findViewById(R.id.klassement_header_team)).setText(this.getText(R.string.klassement_header_team));
	}
}
