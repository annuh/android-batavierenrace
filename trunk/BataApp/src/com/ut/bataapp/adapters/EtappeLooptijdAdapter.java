package com.ut.bataapp.adapters;

import java.util.ArrayList;

import com.actionbarsherlock.view.Menu;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.Looptijd;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EtappeLooptijdAdapter extends ArrayAdapter<Looptijd> {

	private final Context context;
	private final ArrayList<Looptijd> values;

	public EtappeLooptijdAdapter(Context context, ArrayList<Looptijd> values) {
		super(context, R.layout.row_looptijd, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_etappe_looptijd, parent, false);
		rowView.setId(values.get(position).getTeamStartnummer());

		TextView plaats = (TextView) rowView.findViewById(R.id.etappe_looptijd_plaats);
		plaats.setText(String.valueOf(values.get(position).getEtappeStand()));

		TextView team = (TextView) rowView.findViewById(R.id.etappe_looptijd_team);
		team.setText(values.get(position).getTeamNaam());

		TextView tijd = (TextView) rowView.findViewById(R.id.etappe_looptijd_tijd);
		tijd.setText(values.get(position).getTijd());

		TextView code = (TextView) rowView.findViewById(R.id.etappe_looptijd_code);
		code.setText(values.get(position).getFoutcode());

		return rowView;
	}

}
