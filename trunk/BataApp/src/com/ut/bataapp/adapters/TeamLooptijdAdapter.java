package com.ut.bataapp.adapters;

import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Looptijd;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter voor teamlooptijden. Per looptijd is etappenummer, tijd, snelheid, etappe klassement, cum klassement en een evt. foutcode te zien.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class TeamLooptijdAdapter extends ArrayAdapter<Looptijd> {

	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle looptijden die getoond worden */
	private final ArrayList<Looptijd> values;

	/**
	 * Constructor van TeamLooptijdAdapter
	 * @param context De context waarin deze Adapter wordt aangeroepen
	 * @param values De looptijden die getoond moeten worden
	 */
	public TeamLooptijdAdapter(Context context, ArrayList<Looptijd> values) {
		super(context, R.layout.row_team_looptijd, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_team_looptijd, parent, false);
		rowView.setId(values.get(position).getEtappe());

		String formatkmu = context.getResources().getString(R.string.kmu);	

		TextView etappe = (TextView) rowView.findViewById(R.id.etappe);
		etappe.setText(String.valueOf(values.get(position).getEtappe()));

		TextView tijd = (TextView) rowView.findViewById(R.id.tijd);
		tijd.setText(values.get(position).getTijd());

		if(values.get(position).getSnelheid() != null && values.get(position).getSnelheid().length() > 0) {
			TextView snelheid = (TextView) rowView.findViewById(R.id.snelheid);
			snelheid.setText(String.format(formatkmu,values.get(position).getSnelheid()));
		}

		TextView etappe_klassement = (TextView) rowView.findViewById(R.id.etappe_klassement);
		etappe_klassement.setText(String.valueOf(values.get(position).getEtappeStand()));

		TextView cum_klassement = (TextView) rowView.findViewById(R.id.cum_klassement);
		cum_klassement.setText(String.valueOf(values.get(position).getCumulatieveStand()));

		TextView code = (TextView) rowView.findViewById(R.id.etappe_foutcode);
		code.setText(values.get(position).getFoutcode());

		return rowView;
	}

}
