package com.ut.bataapp.adapters;

import java.util.ArrayList;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.KlassementItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * Adapter voor Klassement. Per klassementitem wordt de plaats binnen het klassement, teamnaam en de cumalitieve tijd vermeld.
 * Klassementitems zijn zoekbaar, op teamnaam en teamstartnummer 
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class KlassementAdapter extends ArrayAdapter<KlassementItem> {

	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle klassementitems, wordt gebruikt om filteredItems te herstellen */
	private ArrayList<KlassementItem> values;
	/** Lijst met gefilterde klassementitems die getoond worden */
	private ArrayList<KlassementItem> filteredItems;
	/** Filter dat gebruikt wordt om te zoeken op teamnaam en teamstartnummer */
	private KlassementFilter filter;

	/**
	 * Constructor van KlassementAdapter.
	 * Alle klassementitems worden gekopieerd naar values en filteredItems
	 * @param context Context waarin deze Adapter wordt aangeroepen
	 * @param values Lijst met alle klassementitems
	 */
	public KlassementAdapter(Context context, ArrayList<KlassementItem> values) {
		super(context, R.layout.row_team_looptijd, values);
		this.context = context;
		this.values = new ArrayList<KlassementItem>();
		this.values.addAll(values);
		filteredItems = new ArrayList<KlassementItem>();
		filteredItems.addAll(values);
		getFilter();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_klassement, parent, false);
		rowView.setId(filteredItems.get(position).getTeamStartNummer());

		TextView plaats = (TextView) rowView.findViewById(R.id.klassement_plaats);
		plaats.setText(String.valueOf(filteredItems.get(position).getPlaats()));

		TextView team = (TextView) rowView.findViewById(R.id.klassement_team);
		team.setText(filteredItems.get(position).getTeamNaam());

		TextView tijd = (TextView) rowView.findViewById(R.id.klassement_tijd);
		tijd.setText(filteredItems.get(position).getTijd());

		return rowView;
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null){
			filter = new KlassementFilter();
		}
		return filter;
	}

	/**
	 * Inner class Filter. Deze filter maakt het mogelijk om te zoeken op teamnaam en startnummer, klassementitems die hier niet
	 * aan voldoen worden gefilterd.
	 * @author Anne vd Venis
	 * @version 1.0
	 */
	private class KlassementFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0) {
				ArrayList<KlassementItem> filteredItems = new ArrayList<KlassementItem>();
				for(int i = 0, l = values.size(); i < l; i++) {
					KlassementItem m = values.get(i);
					// Zoek op teamnaam of teamstartnummer
					if(m.getTeamNaam().toLowerCase().contains(constraint) || String.valueOf(m.getTeamStartNummer()).contains(constraint))
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
			filteredItems = (ArrayList<KlassementItem>)results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0; i <filteredItems.size(); i++){
				add(filteredItems.get(i));
			}
			notifyDataSetInvalidated();
		}
	}

}