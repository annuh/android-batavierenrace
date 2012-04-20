package com.ut.bataapp.adapters;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Etappe;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EtappeAdapter extends ArrayAdapter<Etappe> {
	
	private final Context context;
	private final ArrayList<Etappe> values;

	public EtappeAdapter(Context context, ArrayList<Etappe> values) {
		super(context, R.layout.row_etappe, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_etappe, parent, false);
		rowView.setId(values.get(position).getId());
		
		String formatetappe = context.getResources().getString(R.string.etappes_etappe);
		String formatkm = context.getResources().getString(R.string.km);
		
		TextView textView = (TextView) rowView.findViewById(R.id.etappe);
		textView.setText(String.format(formatetappe,String.valueOf(values.get(position).getId())));

		TextView naam = (TextView) rowView.findViewById(R.id.naam);
		naam.setText(values.get(position).getVan());
		
		TextView afstand = (TextView) rowView.findViewById(R.id.afstand);
		BigDecimal afst = new BigDecimal((values.get(position).getAfstand()));
		afst = afst.divide(new BigDecimal(1000), 1, BigDecimal.ROUND_FLOOR);
		afstand.setText(String.format(formatkm, afst));
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.geslacht);		
		if(values.get(position).getGeslacht() == 'H') {
			
		} else {
			imageView.setImageResource(R.drawable.female);
		}

		return rowView;
	}
	
}
