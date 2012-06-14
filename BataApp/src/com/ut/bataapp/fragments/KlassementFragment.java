package com.ut.bataapp.fragments;

import java.util.Collections;
import java.util.Comparator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
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

/**
 * Klasse voor het representeren van een een KlassementFragment.
 * Alle klassementitems worden weergegeven op een lijst en gesorteerd op stand.
 * De klassementitems zijn tevens te sorteren op naam en stand en zijn te updaten vanuit dit fragment.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class KlassementFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Response<Klassement>> {

	/** ID van de menuknop om te zoeken op teamnaam of teamstartnummer */
	private final int MENU_SEARCH = Menu.FIRST + 10;
	/** ID van de menuknop om te sorteren op teamnaam */
	private final int MENU_SORT_NAAM = Menu.FIRST + 11;
	/** ID van de menuknop om te sorteren op stand */
	private final int MENU_SORT_STAND = Menu.FIRST + 12;
	/** ID van de menuknop om de gegevens te updaten */
	private final int MENU_UPDATE = Menu.FIRST + 13;
	/** String waarop de klassementitems gefilterd moeten worden */
	private String filterText = "";
	/** Naam van dit klassement */
	private static String naam;
	/** Het klassement waarvan de klassementitems worden weergegeven */
	private Klassement klassement;
	/** Adapter waarin de gegevens worden opgeslagen */
	private KlassementAdapter adapter = null;
	/** Sorteervolgorde van naam */
	private char sortNaam = 'D';
	/** Sorteervolgorde van stand */
	private char sortStand = 'D';
	/** Boolean om bij te houden of dit fragment worden geladen in een ViewPager */
	private boolean inViewpager = false;
	/** Boolean om bij te houden of dit fragment voor de eerste keer wordt geladen, nodig om alleen eerste keer te sorteren */
	private boolean firstLaunch = true;
	/** ProgressDialog dat wordt getoond als de gegevens worden opgehaald */
	private ProgressDialogFragment progressDialog;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		naam = this.getArguments().getString("index");
		inViewpager = this.getArguments().getBoolean("inViewpager", false);
		setHasOptionsMenu(true);
		if(!inViewpager)
			this.getListView().getEmptyView().setVisibility(View.GONE);
		if (getArguments().getBoolean("restarted", false))
			getActivity().getSupportLoaderManager().restartLoader(0, null, this);
		else
			getActivity().getSupportLoaderManager().initLoader(0, null, this);

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

		view.findViewById(R.id.listview_hint).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				closeHint(view);
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
		menu.add(0,MENU_SEARCH,0, R.string.ab_zoeken)
		.setIcon(R.drawable.ic_action_search)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu(R.string.ab_sorteren);
		subMenu1.add(0,MENU_SORT_NAAM,Menu.NONE, R.string.sort_naam);
		subMenu1.add(0,MENU_SORT_STAND,Menu.NONE, R.string.sort_stand);

		subMenu1.getItem()
		.setIcon(R.drawable.ic_action_sort)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		if(!inViewpager){
			menu.add(0,MENU_UPDATE,Menu.NONE, R.string.ab_update)
			.setIcon(R.drawable.ic_action_refresh)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
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
			return true;
		case MENU_SORT_NAAM:
			sortNaam(null);
			return true;
		case MENU_SORT_STAND:
			sortStand(null);
			return true;
		case MENU_UPDATE:
			getActivity().getSupportLoaderManager().restartLoader(0, null, this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

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

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) { }

		public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			filterText= s.toString();
			adapter.getFilter().filter(s);
		}
	};

	/**
	 * Zet alle items uit klassement in een ListView
	 */
	public void makeList() {
		adapter = new KlassementAdapter(getActivity().getApplicationContext(), klassement.getUitslag());
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Methode om de klassementitems te sorteren op teamnaam
	 * @param v - View welke deze actie aanroept
	 */
	public void sortNaam(View v) {
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
		if(getView() != null) {
			resetArrows();
			int i = this.getResources().getIdentifier("sort_"+sortNaam, "string", this.getActivity().getPackageName());
			((TextView) getView().findViewById(R.id.klassement_header_team)).setText(this.getText(R.string.klassement_header_team) +" "+ getText(i));
			makeList();
		}
	}

	/**
	 * Methode om de klassementitems te sorteren op teamstartnummer
	 * @param v - View welke deze actie aanroept
	 */
	public void sortStand(View v) {
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
		if(getView() != null) {
			resetArrows();
			int i = this.getResources().getIdentifier("sort_"+sortStand, "string", this.getActivity().getPackageName());
			((TextView) getView().findViewById(R.id.klassement_header_stand)).setText(this.getText(R.string.klassement_header_stand) +" "+ getText(i));
			makeList();
		}

	}

	/**
	 * Haalt de pijlen weg in de headers van de ListView.
	 */
	public void resetArrows() {
		((TextView) getView().findViewById(R.id.klassement_header_stand)).setText(this.getText(R.string.klassement_header_stand));
		((TextView) getView().findViewById(R.id.klassement_header_team)).setText(this.getText(R.string.klassement_header_team));
	}

	/**
	 * Loader waarin het klassement wordt opgehaald.
	 * @author Anne
	 *
	 */
	public static class KlassementLoader extends AsyncTaskLoader<Response<Klassement>> {
		Response<Klassement> response;
		public KlassementLoader(Context context) {
			super(context);
		}

		@Override
		public Response<Klassement> loadInBackground() {
			response = api.getKlassementByNaam(naam);
			return response;
		}

		@Override
		public void deliverResult(Response<Klassement> response) {
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

	@Override
	public Loader<Response<Klassement>> onCreateLoader(int arg0, Bundle arg1) {
		if(!inViewpager){
			progressDialog = ProgressDialogFragment.newInstance(getString(R.string.laden_titel), getString(R.string.klassement_laden));
			progressDialog.show(getActivity().getSupportFragmentManager(), "tag_laden");
			progressDialog.setCancelable(true);
		}
		return new KlassementLoader(getActivity().getApplicationContext());
	}

	@Override
	public void onLoadFinished(Loader<Response<Klassement>> loader, Response<Klassement> response) {
		if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
			klassement = response.getResponse();
			if(firstLaunch) {
				sortStand(null);
				firstLaunch=false;
			}
			if(getView() != null) {
				getListView().setSelection(getArguments().getInt("init") -1);
				if(!inViewpager && progressDialog != null)
					handler.sendEmptyMessage(1);
				((TextView) getView().findViewById(R.id.listview_hint)).setVisibility(View.VISIBLE);
				((TextView) getView().findViewById(R.id.listview_hint)).setText(String.format(getString(R.string.klassement_hint), String.valueOf(klassement.getTotEtappe())));
				getListView().getEmptyView().setVisibility(View.GONE);
				getListView().setEmptyView(getView().findViewById(R.id.listview_leeg));
			}
		}
	}

	/**
	 * 
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1) {
				hideDialog();
			}
		}
	};

	/**
	 * Verwijderd het dialog waarin een spinner wordt weergegeven.
	 */
	public void hideDialog(){
		progressDialog.dismiss();
	}

	@Override
	public void onLoaderReset(Loader<Response<Klassement>> arg0) {                
		adapter = null;
	}

	/**
	 * Verwijderd de hint de bovenin de layout wordt getoond.
	 * @param view View die deze methode aanroept
	 */
	public void closeHint(final View view) {
		Animation animationSlideOutRight = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),
				android.R.anim.slide_out_right);
		animationSlideOutRight.setDuration(200);
		animationSlideOutRight.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation anim) { };
			public void onAnimationRepeat(Animation anim) { };
			public void onAnimationEnd(Animation anim) {
				view.setVisibility(View.GONE);
			};
		});
		view.setAnimation(animationSlideOutRight);
		view.startAnimation(animationSlideOutRight);

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().getSupportLoaderManager().destroyLoader(0);
	}
}
