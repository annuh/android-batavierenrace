package com.ut.bataapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.ut.bataapp.R;
import com.ut.bataapp.SeparatedListAdapter;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.EtappeActivity;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.adapters.EtappeLooptijdAdapter;
import com.ut.bataapp.adapters.KlassementAdapter;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.KlassementItem;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
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

public class EtappeLooptijdenFragment extends SherlockListFragment {

	private ArrayList<ArrayList<Looptijd>> looptijden = new ArrayList<ArrayList<Looptijd>>();
	private ArrayList<ArrayList<Looptijd>> looptijden_copy = new ArrayList<ArrayList<Looptijd>>();
	private EtappeLooptijdAdapter adapter_uni = null;
	private EtappeLooptijdAdapter adapter_alg = null;
	private SeparatedListAdapter adapter = null;
	private final int MENU_SEARCH = Menu.FIRST + 10;
	private final int MENU_SORT_NAAM = Menu.FIRST + 11;
	private final int MENU_SORT_STAND = Menu.FIRST + 12;
	TextView loading;
	private String filterText = "";
	private char sortNaam = 'D';
	private char sortStand = 'D';

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		View view = inflater.inflate(R.layout.listview_etappe_looptijden, null);
		view.findViewById(R.id.etappe_looptijden_header_stand).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortStand(null);
			}
		});
		view.findViewById(R.id.etappe_looptijden_header_team).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortNaam(null);
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onResume(){
		super.onResume();
		if(adapter_uni == null) {
			new getEtappeLooptijden().execute();
		} else {
			makeList();
		}
	}


	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		menu.add(0,MENU_SEARCH,Menu.NONE, R.string.ab_zoeken)
		.setIcon(R.drawable.ic_action_search)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, "Naam");
		subMenu1.add(0,MENU_SORT_STAND,Menu.NONE, "Uitslag");

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SORT_NAAM:
			sortNaam(null);
			break;
		case MENU_SEARCH:
			item.setActionView(R.layout.search_box);
			EditText filterEdit = (EditText) item.getActionView().findViewById(R.id.search_box);
			filterEdit.setText(filterText);
			filterEdit.addTextChangedListener(filterTextWatcher);
			setKeyboardFocus(filterEdit);
			break;
		case MENU_SORT_STAND:
			sortStand(null);		
		}
		return super.onOptionsItemSelected(item);
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {	
	
			filterText = s.toString();
			ArrayList<ArrayList<Looptijd>> result = new ArrayList<ArrayList<Looptijd>>();
			for(ArrayList<Looptijd> o : looptijden_copy) {
				ArrayList<Looptijd> result1 = new ArrayList<Looptijd>();
				for(Looptijd l: o) {
					if(l.getTeamNaam().toLowerCase().contains(filterText.toLowerCase()) || String.valueOf(l.getTeamStartnummer()).contains(filterText.toLowerCase()) )
							result1.add(l);
				}
				result.add(result1);
			}
			
			looptijden = result;
			//adapter_alg.getFilter().filter(filterText);
			//adapter_uni.getFilter().filter(filterText);	
			
			makeList();
			

		}
	};

	public static void setKeyboardFocus(final EditText primaryTextField) {
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
				primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
			}
		}, 100);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), TeamActivity.class);
		intent.putExtra("index", v.getId());
		startActivity(intent);
	}

	private class getEtappeLooptijden extends AsyncTask<Void, Void, Void> {
		Response<ArrayList<ArrayList<Looptijd>>> response;

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getUitslagenVanEtappe(((EtappeActivity) getActivity()).getEtappe().getId());
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {
			if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
				looptijden = response.getResponse();
				//looptijden_copy.addAll(looptijden);
				looptijden_copy = copyArray(looptijden);
				//Collections.copy(looptijden_copy, looptijden);
				getListView().setEmptyView(getView().findViewById(R.id.listview_leeg));
				if(getView() != null) // Als tijdens laden ander tab wordt geopend
					sortStand(null);
			}
		}
	}

	public ArrayList<ArrayList<Looptijd>> copyArray(ArrayList<ArrayList<Looptijd>> list) {
		ArrayList<ArrayList<Looptijd>> result = new ArrayList<ArrayList<Looptijd>>();
		for(ArrayList<Looptijd> o : list) {
			ArrayList<Looptijd> result1 = new ArrayList<Looptijd>();
			for(Looptijd l: o) {
				result1.add(l);
			}
			result.add(result1);
		}
		return result;
	}
	
	
	/**
	 * Maakt de listview
	 */
	public void makeList() {

		View view = getView();
		if(view != null){
			getListView().setVisibility(View.GONE);
			
			//looptijden.clear();
			
			
			//looptijden = copyArray(looptijden_copy);
			
			//looptijden = new ArrayList<ArrayList<Looptijd>>(looptijden_copy);
			//Log.d("Looptijden alg",""+looptijden.get(1).size());
			adapter_uni = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(0));
			adapter_alg = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(1));

					
			
			adapter = new SeparatedListAdapter(getActivity().getApplicationContext());
			adapter.addSection("Universiteitsklassement", adapter_uni);
			adapter.addSection("Algemeen klassement", adapter_alg);

			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			getListView().setVisibility(View.VISIBLE);
		}
	}

	public void sortNaam(View v) {
		resetArrows();
		if(sortNaam == 'D') {
			Collections.sort(looptijden.get(0),new Comparator<Looptijd>() {
				public int compare(Looptijd arg0, Looptijd arg1) {
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				}
			});
			Collections.sort(looptijden.get(1),new Comparator<Looptijd>() {
				public int compare(Looptijd arg0, Looptijd arg1) {
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				}
			});
			sortNaam = 'A';
			sortStand = 'D';
		} else {
			Collections.sort(looptijden.get(0),new Comparator<Looptijd>() {
				public int compare(Looptijd arg1, Looptijd arg0) {
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				}
			});
			Collections.sort(looptijden.get(1),new Comparator<Looptijd>() {
				public int compare(Looptijd arg1, Looptijd arg0) {
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				}
			});
			sortNaam = 'D';
			sortStand = 'D';
		}

		int i = this.getResources().getIdentifier("sort_"+sortNaam, "string", getActivity().getPackageName());
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_team)).setText(this.getText(R.string.etappe_looptijden_header_team) +" "+ getText(i));
		makeList();
	}

	public void sortStand(View v) {
		resetArrows();
		if(sortStand == 'D') {
			Collections.sort(looptijden.get(0),new Comparator<Looptijd>() {
				public int compare(Looptijd arg0, Looptijd arg1) {
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				}
			});
			Collections.sort(looptijden.get(1),new Comparator<Looptijd>() {
				public int compare(Looptijd arg0, Looptijd arg1) {
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				}
			});
			sortStand = 'A';
			sortNaam = 'D';
		} else {
			Collections.sort(looptijden.get(0),new Comparator<Looptijd>() {
				public int compare(Looptijd arg1, Looptijd arg0) {
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				}
			});
			Collections.sort(looptijden.get(1),new Comparator<Looptijd>() {
				public int compare(Looptijd arg1, Looptijd arg0) {
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				}
			});
			sortStand = 'D';
			sortNaam = 'D';
		}

		int i = this.getResources().getIdentifier("sort_"+sortStand, "string", getActivity().getPackageName());
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_stand)).setText(this.getText(R.string.etappe_looptijden_header_stand) +" "+ getText(i));
		makeList();
	}


	public void sortList(ArrayList<Looptijd> list, final String sort) {
		Collections.sort(list,new Comparator<Looptijd>() {
			public int compare(Looptijd arg0, Looptijd arg1) {
				if(sort.equals("naam"))
					return arg0.getTeamNaam().compareTo(arg1.getTeamNaam());
				else if(sort.equals("stand"))
					return (arg0.getEtappeStand()<arg1.getEtappeStand() ? -1 : (arg0.getEtappeStand()==arg1.getEtappeStand() ? 0 : 1));
				else
					return 0;
			}
		});
	}

	public void resetArrows() {
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_stand)).setText(this.getText(R.string.etappe_looptijden_header_stand));
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_team)).setText(this.getText(R.string.etappe_looptijden_header_team));
	}

}
