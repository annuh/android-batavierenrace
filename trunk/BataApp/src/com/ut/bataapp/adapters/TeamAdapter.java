package com.ut.bataapp.adapters;

import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Team;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * Adapter voor teams. Per team wordt het startnummer en teamnaam weergegeven.
 * Teams zijn zoekbaar, op teamnaam en teamstartnummer 
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class TeamAdapter extends ArrayAdapter<Team> {

	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle teams, wordt gebruikt om filteredItems te herstellen */
	private ArrayList<Team> values;
	/** Lijst met gefilterde teams die getoond worden */
	private ArrayList<Team> filteredItems;
	/** Filter dat gebruikt wordt om te zoeken op teamnaam en teamstartnummer */
	private TeamFilter filter;

	/**
	 * Constructor van TeamAdapter.
	 * Alle teams worden gekopieerd naar values en filteredItems
	 * @param context Context waarin deze Adapter wordt aangeroepen
	 * @param values Lijst met alle teams
	 */
	public TeamAdapter(Context context, ArrayList<Team> values) {
		super(context, R.layout.row_team, values);
		this.context = context;
		this.values = new ArrayList<Team>();
		this.values.addAll(values);
		filteredItems = new ArrayList<Team>();
		filteredItems.addAll(values);
		getFilter();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_team, parent, false);
		rowView.setId(filteredItems.get(position).getID());

		TextView text_start = (TextView) rowView.findViewById(R.id.team_start);
		text_start.setText(Integer.toString(filteredItems.get(position).getStartnummer()));

		TextView text_naam = (TextView) rowView.findViewById(R.id.team_naam);
		text_naam.setText(filteredItems.get(position).getNaam());


		return rowView;
	}



	@Override
	public Filter getFilter() {
		if (filter == null){
			filter = new TeamFilter();
		}
		return filter;
	}

	/**
	 * Inner class Filter. Deze filter maakt het mogelijk om te zoeken op teamnaam en startnummer, teams die hier niet
	 * aan voldoen worden gefilterd.
	 * @author Anne vd Venis
	 * @version 1.0
	 */
	private class TeamFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0) {
				ArrayList<Team> filteredItems = new ArrayList<Team>();
				for(int i = 0, l = values.size(); i < l; i++) {
					Team m = values.get(i);
					// Zoeken op teamnaam of startnummer
					if(m.getNaam().toLowerCase().contains(constraint) || String.valueOf(m.getStartnummer()).contains(constraint))
						filteredItems.add(m);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}
			else {
				synchronized(this) {
					result.values = values;
					result.count = values.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filteredItems = (ArrayList<Team>)results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0; i <filteredItems.size(); i++){
				add(filteredItems.get(i));
			}
			notifyDataSetInvalidated();

		}
	}


}
