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
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Looptijd;
import com.ut.bataapp.objects.Response;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Klasse voor het representeren van een een EtappeLooptijdenFragment.
 * Alle looptijden van een etappe worden in een lijst gezet en zijn zoekbaar op teamnaam en teamstartnummer.
 * De looptijden zijn tevens te sorteren op naam en stand.
 * Er kan ook een popup worden weergegeven met een overzicht van alle foutcodes en bijbehorende afkortingen.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class EtappeLooptijdenFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Response<ArrayList<ArrayList<Looptijd>>>> {

	/** Alle looptijden van deze etappe, worden gebruikt als kopie om looptijden_copy te herstellen */
	private ArrayList<ArrayList<Looptijd>> looptijden = new ArrayList<ArrayList<Looptijd>>();
	/** Alle looptijden van deze etappe die worden weergegeven */
	private ArrayList<ArrayList<Looptijd>> looptijden_copy = new ArrayList<ArrayList<Looptijd>>();
	/** Adapter voor de looptijden van het universiteitsklassement */
	private EtappeLooptijdAdapter adapter_uni = null;
	/** Adapter voor de looptijden van het algemeen klassement */
	private EtappeLooptijdAdapter adapter_alg = null;
	/** Gecombineerde adapter om zowel de looptijden van het universiteitsklassement en algemeen klassement weer te geven */
	private SeparatedListAdapter adapter = null;
	/** ID van de actionbar-zoekknop */
	private final int MENU_SEARCH = Menu.FIRST + 10;
	/** ID van de sorteer-op-teamnaam zoekknop */
	private final int MENU_SORT_NAAM = Menu.FIRST + 11;
	/** ID van de sorteer-op-stand zoekknop */
	private final int MENU_SORT_STAND = Menu.FIRST + 12;
	/** ID van de foutcodes menu knop */
	private final int MENU_FOUTCODES = Menu.FIRST + 2;
	/** Tekst waarop de looptijden gefilterd worden */
	private String filterText = "";
	/** Sorteervolgorde van teamnaam, D=decrease, A=ascend */
	private char sortTeam = 'D';
	/** Sorteervolgorde van stand van teams, D=decrease, A=ascend */
	private char sortStand = 'D';
	/** Variabele om bij te houden of dit fragment voor het eerst wordt geopend. Als dit fragment voor het eerst wordt
	 * geopend, worden de teams gesorteerd op stand in deze etappe */
	private boolean firstLaunch = true;

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
				sortTeam(null);
			}
		});
		view.findViewById(R.id.etappe_looptijden_header_snelheid).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortStand(null);
			}
		});
		view.findViewById(R.id.etappe_looptijden_header_tijd).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortStand(null);
			}
		});
		
		
		return view;
	}

	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments().getBoolean("restarted", false))
			getActivity().getSupportLoaderManager().restartLoader(0, null, this);
		else
			getActivity().getSupportLoaderManager().initLoader(0, null, this);
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
		
		menu.add(0,MENU_FOUTCODES,Menu.NONE, R.string.ab_foutcodes)
		.setIcon(R.drawable.ic_action_foutcodes)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SORT_NAAM:
			sortTeam(null);
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
		case MENU_FOUTCODES:
			FragmentTransaction ft = getFragmentManager().beginTransaction();
		    DialogFragment newFragment = new FoutcodesDialogFragment();
		    newFragment.show(ft, "Foutcodes");
			break;		
		
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Deze methode zorgt ervoor dat er de looptijden kan worden gezocht op naam en startnummer
	 */
	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) { }

		public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

		public void onTextChanged(CharSequence s, int start, int before, int count) {	
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
			makeList();
		}
	};

	/**
	 * Deze methode zorgt ervoor dat het software toetsenbord wordt geopend en maakt een invoerveld actief. 
	 * @param primaryTextField Het invoerveld dat actief moet worden gemaakt
	 */
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

	/**
	 * Kopieerd de inhoud van een arraylist naar een nieuwe arraylist.
	 * @param list De lijst die gekopieerd moet worden
	 * @return Een nieuwe lijst met dezelfde elementen als list
	 */
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
	 * Loader waarin de Etappelooptijden worden opgehaald
	 * @author Anne vd Venis
	 *
	 */
	public static class EtappeLooptijdenLoader extends AsyncTaskLoader<Response<ArrayList<ArrayList<Looptijd>>>> {
		
		/** Resultaat van de api aanvraag */
		Response<ArrayList<ArrayList<Looptijd>>> response;
		/** ID van de etappe waarvan de looptijden worden opgehaald. */
		int etappeid;
		
		/**
		 * Constructor van EtappeLooptijdenLoader
		 * @param context Context waarin deze Loader wordt aangeroepen
		 * @param etappeid ID van de etappe waarvan de looptijden worden opgehaald.
		 */
		public EtappeLooptijdenLoader(Context context, int etappeid) {
			super(context);
			this.etappeid = etappeid;
		}

		@Override
		public Response<ArrayList<ArrayList<Looptijd>>> loadInBackground() {
			response = api.getUitslagenVanEtappe(etappeid);
			return response;
		}

		@Override
		public void deliverResult(Response<ArrayList<ArrayList<Looptijd>>> response) {
			if (isReset()) {
				return;
			}
			this.response = response;
			super.deliverResult(response);
		}

		@Override
		protected void onStartLoading() {
			if (response != null) {
				deliverResult(response);
			}
			if (takeContentChanged() || response == null) {
				forceLoad();
			}
		}

		@Override
		protected void onStopLoading() {
			// Attempt to cancel the current load task if possible.
			cancelLoad();
		}

		@Override
		protected void onReset() {
			super.onReset();
			// Ensure the loader is stopped
			onStopLoading();
			response = null;
		}
	}

	/**
	 * Maakt de listview
	 */
	public void makeList() {
		View view = getView();
		if(view != null){
			getListView().setVisibility(View.GONE);
			adapter_uni = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(0));
			adapter_alg = new EtappeLooptijdAdapter(getActivity().getApplicationContext(), looptijden.get(1));
			adapter = new SeparatedListAdapter(getActivity().getApplicationContext());
			adapter.addSection(getString(R.string.etappe_looptijden_header_universiteit), adapter_uni);
			adapter.addSection(getString(R.string.etappe_looptijden_header_algemeen), adapter_alg);
			setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			getListView().setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Methode die de lijst sorteerd op teamnaam.
	 * Als de lijst al van A-Z is gesorteerd, wordt de lijst nu op Z-A gesorteerd en vice versa.
	 * @param v View waarvan de sorteerfunctie wordt aangeroepen
	 */
	public void sortTeam(View v) {
		if(sortTeam == 'D') {
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
			sortTeam = 'A';
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
			sortTeam = 'D';
			sortStand = 'D';


		}
		if(getView() != null){
			resetArrows();
			int i = this.getResources().getIdentifier("sort_"+sortTeam, "string", this.getActivity().getPackageName());
			((TextView) getView().findViewById(R.id.etappe_looptijden_header_team)).setText(this.getText(R.string.etappe_looptijden_header_team) +" "+ getText(i));
		}
		makeList();
	}

	/**
	 * Methode die de lijst sorteerd op stand binnen deze etappe.
	 * Als de lijst al van LAAG-HOOG is gesorteerd, wordt de lijst nu op HOOG-LAAG gesorteerd en vice versa.
	 * @param v View waarvan de sorteerfunctie wordt aangeroepen
	 */
	public void sortStand(View v) {
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
			sortTeam = 'D';
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
			sortTeam = 'D';
		}
		if(getView() != null){
			resetArrows();
			int i = this.getResources().getIdentifier("sort_"+sortStand, "string", this.getActivity().getPackageName());
			((TextView) getView().findViewById(R.id.etappe_looptijden_header_stand)).setText(this.getText(R.string.etappe_looptijden_header_stand) +" "+ getText(i));
		}
		makeList();
	}

	/**
	 * Methode die de sorteer-pijlen verwijderd uit de ListView header.
	 */
	public void resetArrows() {
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_stand)).setText(this.getText(R.string.etappe_looptijden_header_stand));
		((TextView) getView().findViewById(R.id.etappe_looptijden_header_team)).setText(this.getText(R.string.etappe_looptijden_header_team));

	}

	@Override
	public Loader<Response<ArrayList<ArrayList<Looptijd>>>> onCreateLoader(int arg0, Bundle arg1) {
		return new EtappeLooptijdenLoader(getActivity().getApplicationContext(), ((EtappeActivity) this.getActivity()).getEtappe().getId() );
	}

	@Override
	public void onLoadFinished(Loader<Response<ArrayList<ArrayList<Looptijd>>>> loader, Response<ArrayList<ArrayList<Looptijd>>> response) {
		if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
			looptijden = response.getResponse();
			looptijden_copy = copyArray(looptijden);
			if(firstLaunch) {
				sortStand(null);
				firstLaunch=false;
			}
			if(getView() != null)
				getListView().setEmptyView(getView().findViewById(R.id.listview_leeg));
		}
	}

	@Override
	public void onLoaderReset(Loader<Response<ArrayList<ArrayList<Looptijd>>>> arg0) {                
		adapter = null;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().getSupportLoaderManager().destroyLoader(0);
	}
}
