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

public class LooptijdAdapter extends ArrayAdapter<Looptijd> {
	
	private final Context context;
	private final ArrayList<Looptijd> values;

	public LooptijdAdapter(Context context, ArrayList<Looptijd> values) {
		super(context, R.layout.row_looptijd, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_looptijd, parent, false);
		rowView.setId(values.get(position).getEtappe());
		
		TextView etappe = (TextView) rowView.findViewById(R.id.etappe);
		etappe.setText(String.valueOf(values.get(position).getEtappe()));
		
		TextView tijd = (TextView) rowView.findViewById(R.id.tijd);
		tijd.setText(values.get(position).getTijd());
		
		TextView snelheid = (TextView) rowView.findViewById(R.id.snelheid);
		snelheid.setText(values.get(position).getSnelheid());

		return rowView;
	}
	
}
