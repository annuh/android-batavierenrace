package com.ut.bataapp.adapters;

import java.util.ArrayList;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.Looptijd;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * Adapter voor Etappelooptijden. De teamnaam, plaats, tijd, snelheid en foutcode worden getoond.
 * Looptijden zijn zoekbaar, op teamnaam en teamstartnummer 
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class EtappeLooptijdAdapter extends ArrayAdapter<Looptijd> {

	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle looptijden, wordt gebruikt om filteredItems te herstellen */
	private final ArrayList<Looptijd> values;
	/** Lijst met gefilterde looptijden die getoond worden */
	private ArrayList<Looptijd> filteredItems;
	/** Filter dat gebruikt wordt om te zoeken op teamnaam en teamstartnummer */
	private LooptijdFilter filter;

	/**
	 * Constructor van EtappeLooptijdAdapter.
	 * Alle looptijden worden gekopieerd naar values en filteredItems
	 * @param context Context waarin deze Adapter wordt aangeroepen
	 * @param values Lijst met alle looptijden
	 */
	public EtappeLooptijdAdapter(Context context, ArrayList<Looptijd> values) {
		super(context, R.layout.row_team_looptijd, values);
		this.context = context;
		this.values = new ArrayList<Looptijd>();
		this.values.addAll(values);
		filteredItems = new ArrayList<Looptijd>();
		filteredItems.addAll(values);
		getFilter();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_etappe_looptijd, parent, false);
		rowView.setId(filteredItems.get(position).getTeamStartnummer());

		TextView plaats = (TextView) rowView.findViewById(R.id.etappe_looptijd_plaats);
		plaats.setText(String.valueOf(filteredItems.get(position).getEtappeStand()));

		TextView team = (TextView) rowView.findViewById(R.id.etappe_looptijd_team);
		team.setText(filteredItems.get(position).getTeamNaam());

				
		TextView tijd = (TextView) rowView.findViewById(R.id.etappe_looptijd_tijd);
		tijd.setText(filteredItems.get(position).getTijd());
		
		String foutcodeteken = (filteredItems.get(position).getFoutcode().equals("–")) ? "  " : filteredItems.get(position).getFoutcode();
		TextView foutcode = (TextView) rowView.findViewById(R.id.etappe_looptijd_foutcode);
		foutcode.setText(foutcodeteken);
			
		if(values.get(position).getSnelheid() != null && values.get(position).getSnelheid().length() > 0) {
			TextView snelheid = (TextView) rowView.findViewById(R.id.etappe_looptijd_snelheid);
			snelheid.setText(String.format(context.getString(R.string.kmu), filteredItems.get(position).getSnelheid()));
		}

		return rowView;
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null){
			filter = new LooptijdFilter();
		}
		return filter;
	}

	/**
	 * Inner class Filter. Deze filter maakt het mogelijk om te zoeken op teamnaam en startnummer, looptijden die hier niet
	 * aan voldoen worden gefilterd.
	 * @author Anne vd Venis
	 * @version 1.0
	 */
	private class LooptijdFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0) {
				ArrayList<Looptijd> filteredItems = new ArrayList<Looptijd>();
				for(int i = 0, l = values.size(); i < l; i++) {
					Looptijd m = values.get(i);
					// Zoek op teamnaam of teamstartnummer
					if(m.getTeamNaam().toLowerCase().contains(constraint) || String.valueOf(m.getTeamStartnummer()).contains(constraint))
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
			filteredItems = (ArrayList<Looptijd>)results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0; i <filteredItems.size(); i++){
				add(filteredItems.get(i));
			}
			notifyDataSetInvalidated();

		}
	}


}
