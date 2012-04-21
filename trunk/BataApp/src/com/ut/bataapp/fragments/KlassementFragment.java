package com.ut.bataapp.fragments;

import java.util.Collections;
import android.support.v4.app.LoaderManager;
import java.util.Comparator;
import android.support.v4.content.Loader;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

public class KlassementFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Response<Klassement>> {

	private final int MENU_SEARCH = Menu.FIRST + 10;
	private final int MENU_SORT_NAAM = Menu.FIRST + 11;
	private final int MENU_SORT_STAND = Menu.FIRST + 12;
	private String filterText = "";
	private static String naam;
	private Klassement klassement;
	private KlassementAdapter adapter = null;
	private char sortNaam = 'D';
	private char sortStand = 'D';
	private boolean inViewpager = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		naam = this.getArguments().getString("index");
		inViewpager = this.getArguments().getBoolean("inViewpager", false);
		if(inViewpager)
			this.setListAdapter(null);
		this.getActivity().getSupportLoaderManager().initLoader(0, null, this);
		//new getKlassement(getActivity().getApplicationContext()).execute();
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
		Context context;

		public getKlassement(Context context) {
			this.context = context;
		}
		protected void onPreExecute() {
			if(!inViewpager){
				progressDialog = ProgressDialogFragment.newInstance(getString(R.string.laden_titel), getString(R.string.klassement_laden));
				progressDialog.show(getActivity().getSupportFragmentManager(), "");
				//progressDialog = ProgressDialog.show(getActivity().getApplicationContext(),  
				//		getString(R.string.laden_titel), getString(R.string.klassement_laden), true);*/
				progressDialog.setCancelable(true);
				/*(progressDialog.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						cancel(true);
						Utils.goHome(getActivity().getApplicationContext());
					}
				});*/

			}
		}

		@Override  
		protected Void doInBackground(Void... arg0) {
			if(!isCancelled())
				response = api.getKlassementByNaam(naam);
			return null;
		}

		@Override  
		protected void onPostExecute(Void result) {

			if(Utils.checkResponse(context, response)) {
				klassement = response.getResponse();
				Log.d("Klassement", ""+klassement.getUitslag().size());
				sortStand(null);
				getListView().setSelection(getArguments().getInt("init") -1);
				if(!inViewpager && progressDialog != null)
					progressDialog.dismiss();
				getListView().setEmptyView(getView().findViewById(R.id.listview_leeg));
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

	@Override
	public void onDestroy(){
		Log.d("KlassementFragment", "DESTROY");
		super.onDestroy();
	}

	public static class AppListLoader extends AsyncTaskLoader<Response<Klassement>> {
		Response<Klassement> response;
		public AppListLoader(Context context) {
			super(context);
		}

		@Override public Response<Klassement> loadInBackground() {
			response = api.getKlassementByNaam(naam);
			return response;
		}

		@Override public void deliverResult(Response<Klassement> response) {
			if (isReset()) {
				return;
			}

			this.response = response;
			super.deliverResult(response);
		}

		@Override protected void onStartLoading() {
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

	//private ProgressDialog progressDialog;
	private ProgressDialogFragment progressDialog;
	@Override
	public Loader<Response<Klassement>> onCreateLoader(int arg0, Bundle arg1) {
		/*progressDialog = ProgressDialog.show(TeamActivity.this,  
				"Bezig met laden", "Team wordt opgehaald...", true);
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});*/
		
		if(!inViewpager){
			progressDialog = ProgressDialogFragment.newInstance(getString(R.string.laden_titel), getString(R.string.klassement_laden));
			progressDialog.show(getActivity().getSupportFragmentManager(), "");
			//progressDialog = ProgressDialog.show(getActivity().getApplicationContext(),  
			//		getString(R.string.laden_titel), getString(R.string.klassement_laden), true);*/
			progressDialog.setCancelable(true);
			/*(progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					Utils.goHome(getActivity().getApplicationContext());
				}
			});*/

		}
		return new AppListLoader(getActivity().getApplicationContext());
	}

	@Override
	public void onLoadFinished(Loader<Response<Klassement>> loader, Response<Klassement> response) {
		//Log.d("Loader", "Klaar");
		if(Utils.checkResponse(getActivity().getApplicationContext(), response)) {
			klassement = response.getResponse();
			Log.d("Klassement", ""+klassement.getUitslag().size());
			sortStand(null);
			getListView().setSelection(getArguments().getInt("init") -1);
			if(!inViewpager && progressDialog != null)
				handler.sendEmptyMessage(1);
			getListView().setEmptyView(getView().findViewById(R.id.listview_leeg));klassement = response.getResponse();
			
		}
	}
	
	
	private Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	        if(msg.what == 1) {
	            hideDialog();
	        }
	    }
	};
	
	public void hideDialog(){
		progressDialog.dismiss();
	}

	@Override
	public void onLoaderReset(Loader<Response<Klassement>> arg0) {                
		//Utils.goHome(this);
	}

}
