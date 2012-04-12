package com.ut.bataapp.adapters;

import java.util.ArrayList;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.Klassement.KlassementInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KlassementAdapter extends ArrayAdapter<KlassementInfo> {
	
	private final Context context;
	private final ArrayList<KlassementInfo> values;

	public KlassementAdapter(Context context, ArrayList<KlassementInfo> values) {
		super(context, R.layout.row_looptijd, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_looptijd, parent, false);
		rowView.setId(values.get(position).getTeamStartNummer());
		
		TextView plaats = (TextView) rowView.findViewById(R.id.klassement_plaats);
		plaats.setText(String.valueOf(values.get(position).getPlaats()));
		
		TextView team = (TextView) rowView.findViewById(R.id.klassement_team);
		team.setText(values.get(position).getTeamNaam());
		
		TextView tijd = (TextView) rowView.findViewById(R.id.klassement_tijd);
		tijd.setText(values.get(position).getTijd());

		return rowView;
	}
	
}
